package com.bw.movie.view.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.bw.movie.R;
import com.bw.movie.presenter.home.HomeFragPresenter;
import com.bw.movie.view.activity.base.BaseActivity;
import com.bw.movie.view.adapter.paging.HomePageFragmentAdapter;
import com.bw.movie.view.fragment.attention.MyAttentionCinemaFragment;
import com.bw.movie.view.fragment.attention.MyAttentionMovieFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AttentionActivity extends BaseActivity<HomeFragPresenter> {

    @BindView(R.id.tab_layout_attention)
    TabLayout tabLayoutAttention;
    @BindView(R.id.view_pager_attention)
    ViewPager viewPagerAttention;
    private Unbinder bind;
    private List<Fragment> fragments;

    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        fragments.add(new MyAttentionMovieFragment());
        fragments.add(new MyAttentionCinemaFragment());

        HomePageFragmentAdapter homePageFragmentAdapter = new HomePageFragmentAdapter(getSupportFragmentManager(), fragments);
        viewPagerAttention.setAdapter(homePageFragmentAdapter);

        for (int i = 0; i < fragments.size(); i++) {
            tabLayoutAttention.addTab(tabLayoutAttention.newTab());
        }

        tabLayoutAttention.setupWithViewPager(viewPagerAttention);

        tabLayoutAttention.getTabAt(0).setText("电影");
        tabLayoutAttention.getTabAt(1).setText("影院");
    }

    @Override
    protected HomeFragPresenter setPresenter() {
        return new HomeFragPresenter();
    }

    @Override
    protected void initView() {
        bind = ButterKnife.bind(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_attention;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
