package com.bw.movie.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bw.movie.R;
import com.bw.movie.model.bean.mine.SysMsgBean;
import com.bw.movie.model.bean.user.UserBean;
import com.bw.movie.presenter.mine.SysMsgPresenter;
import com.bw.movie.view.activity.base.BaseActivity;
import com.bw.movie.view.adapter.mine.RecyclerSysMsgAdapter;
import com.bw.movie.view.contract.IContractView;
import com.bw.movie.view.customview.SyLinearLayoutManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
 * 系统消息
 * */
public class SystemMessageActivity extends BaseActivity<SysMsgPresenter> implements IContractView.ISysMsgView {

    @BindView(R.id.recycler_system_message)
    RecyclerView recyclerSystemMessage;
    private Unbinder bind;
    private String sessionId;
    private int userId;
    private int page = 1;
    private int count = 10;
    private RecyclerSysMsgAdapter recyclerSysMsgAdapter;
    private List<SysMsgBean.ResultBean> sysMsgBeanResult;

    @Override
    protected void initData() {
        presenter.sysMsg(userId,sessionId,page,count);
    }

    @Override
    protected SysMsgPresenter setPresenter() {
        return new SysMsgPresenter();
    }

    @Override
    protected void initView() {
        bind = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_system_message;
    }

    @Override
    public void sysMsgSuccess(SysMsgBean sysMsgBean) {
        Log.e("MyMessageSysMsg",sysMsgBean.getResult().get(0).getTitle());
        sysMsgBeanResult = sysMsgBean.getResult();
        SyLinearLayoutManager syLinearLayoutManager = new SyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerSysMsgAdapter = new RecyclerSysMsgAdapter(this);
        recyclerSysMsgAdapter.addData(sysMsgBeanResult);
        recyclerSystemMessage.setLayoutManager(syLinearLayoutManager);
        recyclerSystemMessage.setAdapter(recyclerSysMsgAdapter);

    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void Message(UserBean userBean){
        userId = userBean.getUserId();
        sessionId = userBean.getSessionId();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        EventBus.getDefault().unregister(this);
    }
}
