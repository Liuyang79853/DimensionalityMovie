package com.bw.movie.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.model.bean.mine.UpdateBean;
import com.bw.movie.model.bean.user.UserBean;
import com.bw.movie.presenter.mine.UpdatePresenter;
import com.bw.movie.view.activity.base.BaseActivity;
import com.bw.movie.view.contract.IContractView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SetUpTheActivity extends BaseActivity<UpdatePresenter> implements IContractView.IUpdateView {

    @BindView(R.id.iv_update)
    ImageView ivUpdate;
    private String sessionId;
    private int userId;
    private String ak = "0110010010000";
    private Unbinder bind;

    @Override
    protected void initData() {

    }

    @Override
    protected UpdatePresenter setPresenter() {
        return new UpdatePresenter();
    }

    @Override
    protected void initView() {
        bind = ButterKnife.bind(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_set_up_the;
    }

    @Override
    public void updateSuccess(UpdateBean updateBean) {
        int flag = updateBean.getFlag();
        if (flag == 1){
            Toast.makeText(this, "已有新版本!请更新!", Toast.LENGTH_SHORT).show();
            String downloadUrl = updateBean.getDownloadUrl();
            Intent intent = new Intent(SetUpTheActivity.this, UpdateNowActivity.class);
            intent.putExtra("downloadUrl",downloadUrl);
            startActivity(intent);
        }else if (flag == 2){
            Toast.makeText(this, "已是最新版本,谢谢对此软件的支持", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    private void Message(UserBean userBean) {
        userId = userBean.getUserId();
        sessionId = userBean.getSessionId();
    }

    @OnClick(R.id.iv_update)
    public void onClick() {
        presenter.upDate(userId, sessionId, ak);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
