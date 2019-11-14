package com.bw.movie.view.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.model.bean.user.MovieRegisterBean;
import com.bw.movie.model.bean.user.SendCodeBean;
import com.bw.movie.model.encryption.EncryptUtil;
import com.bw.movie.presenter.login.RegisterPresenter;
import com.bw.movie.view.activity.base.BaseActivity;
import com.bw.movie.view.contract.IContractView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements IContractView.IRegisterView {

    @BindView(R.id.et_register_userName)
    EditText etRegisterUserName;
    @BindView(R.id.et_register_email)
    EditText etRegisterEmail;
    @BindView(R.id.et_register_pwd)
    EditText etRegisterPwd;
    @BindView(R.id.et_register_code)
    EditText etRegisterCode;
    @BindView(R.id.btn_register_sendCode)
    Button btnRegisterSendCode;
    @BindView(R.id.btn_accounts)
    Button btnAccounts;
    @BindView(R.id.btn_register)
    Button btnRegister;
    private Unbinder mBind;
    private String trim;

    @Override
    protected void initData() {

    }

    @Override
    protected RegisterPresenter setPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void initView() {
        mBind = ButterKnife.bind(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void registerSuccess(MovieRegisterBean movieRegisterBean) {
        if (movieRegisterBean.getStatus().equals("0000")){
            Toast.makeText(this,movieRegisterBean.getMessage(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            finish();
        }else {
            Toast.makeText(this,movieRegisterBean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void sendCode(SendCodeBean sendCodeBean) {
        if (sendCodeBean.getStatus().equals("0000")){
            Toast.makeText(this,sendCodeBean.getMessage(), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,sendCodeBean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void registerError(String msg) {

    }

    @OnClick({R.id.btn_register_sendCode, R.id.btn_accounts, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register_sendCode:
                String trim = etRegisterEmail.getText().toString().trim();
                presenter.sendCode(trim);
                break;
            case R.id.btn_accounts:
                //跳转到登录
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
                break;
            case R.id.btn_register:
                String userName = etRegisterUserName.getText().toString().trim();
                String email = etRegisterEmail.getText().toString().trim();
                String pwd = etRegisterPwd.getText().toString().trim();
                String code = etRegisterCode.getText().toString().trim();
                String encrypt = EncryptUtil.encrypt(pwd);
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd)){
                    Toast.makeText(this, "名字 邮箱 密码 输入不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                presenter.movieRegister(userName,encrypt,email,code);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }
}
