package com.bw.movie.view.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.applications.App;
import com.bw.movie.model.bean.user.MovieLoginBean;
import com.bw.movie.model.bean.user.UserBean;
import com.bw.movie.model.bean.user.WXLoginBean;
import com.bw.movie.model.encryption.EncryptUtil;
import com.bw.movie.presenter.login.LoginPresenter;
import com.bw.movie.view.activity.base.BaseActivity;
import com.bw.movie.view.contract.IContractView;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity extends BaseActivity<LoginPresenter> implements IContractView.ILoginView {

    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.btn_login_Forgot)
    Button btnLoginForgot;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.lineone)
    View lineone;
    @BindView(R.id.tv_huo)
    TextView tvHuo;
    @BindView(R.id.linetwo)
    View linetwo;
    @BindView(R.id.ib_wechatlogin)
    ImageButton ibWechatlogin;
    private Unbinder mBind;

    @Override
    protected void initData() {

    }

    @Override
    protected LoginPresenter setPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initView() {
        mBind = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void loginSuccess(MovieLoginBean movieLoginBean) {
        if (movieLoginBean.getStatus().equals("0000")) {
            Toast.makeText(this, movieLoginBean.getMessage(), Toast.LENGTH_SHORT).show();
            //EventBus存入  userId,sessionId
            EventBus.getDefault().postSticky(new UserBean(movieLoginBean.getResult().getUserId(), movieLoginBean.getResult().getSessionId()));
            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
            //intent传用户名称
            intent.putExtra("nickName", movieLoginBean.getResult().getUserInfo().getNickName());
            //intent传用户头像
            intent.putExtra("headPic", movieLoginBean.getResult().getUserInfo().getHeadPic());
            //intent传用户性别
            intent.putExtra("sex", movieLoginBean.getResult().getUserInfo().getSex());
            //intent传用户邮箱
            intent.putExtra("email", movieLoginBean.getResult().getUserInfo().getEmail());
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, movieLoginBean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //微信登录
    @Override
    public void loginWXSuccess(WXLoginBean wxLoginBean) {

        Toast.makeText(this,wxLoginBean.toString(), Toast.LENGTH_SHORT).show();
        if (wxLoginBean.getStatus().equals("0000")) {
            Toast.makeText(this, wxLoginBean.getMessage(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
            //EventBus存入  userId,sessionId
            EventBus.getDefault().postSticky(new UserBean(wxLoginBean.getResult().getUserId(), wxLoginBean.getResult().getSessionId()));
            //intent传用户名称
            intent.putExtra("nickName", wxLoginBean.getResult().getUserInfo().getNickName());
            //intent传用户头像
            intent.putExtra("headPic", wxLoginBean.getResult().getUserInfo().getHeadPic());
            //intent传用户性别
            intent.putExtra("sex", wxLoginBean.getResult().getUserInfo().getSex());
            //intent传用户邮箱
            intent.putExtra("email", wxLoginBean.getResult().getUserInfo().getEmail());
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, wxLoginBean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loginError(String msg) {

    }

    @OnClick({R.id.btn_login_Forgot, R.id.tv_register, R.id.btn_login, R.id.ib_wechatlogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_Forgot:
                break;
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.btn_login:
                String email = etLoginPhone.getText().toString().trim();
                String pwd = etLoginPwd.getText().toString().trim();
                String encrypt = EncryptUtil.encrypt(pwd);
                Log.e("MyMessage", encrypt + "");
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd)) {
                    Toast.makeText(LoginActivity.this, "输入的账号密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                presenter.movieLogin(email, encrypt);
                break;
            case R.id.ib_wechatlogin:
                if (!App.mWxApi.isWXAppInstalled()) {
                    Toast.makeText(LoginActivity.this, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
                    return;
                }
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "diandi_wx_login";
                App.mWxApi.sendReq(req);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getWXlOGINcODE(BaseResp baseResp) {
        String code = ((SendAuth.Resp) baseResp).code;
        Log.i("TAG", "getWXlOGINcODE: "+code);
        presenter.movieWXLogin(code);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        EventBus.getDefault().unregister(this);
    }
}
