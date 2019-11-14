package com.bw.movie.view.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.model.bean.cinema.CinemaDetailsBean;
import com.bw.movie.model.bean.user.UserBean;
import com.bw.movie.presenter.cinema.CinemaDetailsPresenter;
import com.bw.movie.view.activity.base.BaseActivity;
import com.bw.movie.view.adapter.cinema.CinemaFragmentAdapter;
import com.bw.movie.view.contract.IContractView;
import com.bw.movie.view.fragment.cinema.CinemaDetailsFragment;
import com.bw.movie.view.fragment.cinema.CinemaEvaluationFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
/*
* 影院详情页
* */
public class CinemaDetailsActivity extends BaseActivity<CinemaDetailsPresenter> implements IContractView.ICinemaDetailsView {

    @BindView(R.id.tv_cinemaName)
    TextView tvCinemaName;
    @BindView(R.id.tv_lable)
    TextView tv_lable;
    @BindView(R.id.tab_layout_cinema_details)
    TabLayout tabLayoutCinemaDetails;
    @BindView(R.id.view_pager_cinema_details)
    ViewPager viewPagerCinemaDetails;
    private String sessionId;
    private int userId;
    private Unbinder bind;
    private List<Fragment> fragments;

    @Override
    protected void initData() {
        Intent intent = getIntent();
        int cinemaId = intent.getIntExtra("cinemaId", 0);
        presenter.cinemaInfo(userId, sessionId, cinemaId);
//        fragments = new ArrayList<>();
//        fragments.add(new CinemaDetailsFragment());
//        fragments.add(new CinemaEvaluationFragment());
        CinemaFragmentAdapter cinemaFragmentAdapter = new CinemaFragmentAdapter(getSupportFragmentManager(), fragments);
        viewPagerCinemaDetails.setAdapter(cinemaFragmentAdapter);

        for (int i = 0; i < fragments.size(); i++) {
            tabLayoutCinemaDetails.addTab(tabLayoutCinemaDetails.newTab());
        }

        tabLayoutCinemaDetails.setupWithViewPager(viewPagerCinemaDetails);

        tabLayoutCinemaDetails.getTabAt(0).setText("影院详情");
        tabLayoutCinemaDetails.getTabAt(1).setText("影院评价");
    }

    @Override
    protected CinemaDetailsPresenter setPresenter() {
        return new CinemaDetailsPresenter();
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        bind = ButterKnife.bind(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_cinema_details;
    }

    @Override
    public void cinemaDetailsSuccess(CinemaDetailsBean cinemaDetailsBean) {
        Log.e("MyMessageCinema", cinemaDetailsBean.getResult().getName());
        CinemaDetailsBean.ResultBean result = cinemaDetailsBean.getResult();
        String label = cinemaDetailsBean.getResult().getLabel();
        tv_lable.setText(label);
        tvCinemaName.setText(result.getName()+"("+result.getAddress()+")");
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void Message(UserBean userBean) {
        userId = userBean.getUserId();
        sessionId = userBean.getSessionId();
    }

    @Override
    public void cinemaDetailsError(String msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        bind.unbind();
    }
}
