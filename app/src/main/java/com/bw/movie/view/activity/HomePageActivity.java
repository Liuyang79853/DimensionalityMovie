package com.bw.movie.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.presenter.home.HomeFragPresenter;
import com.bw.movie.view.activity.base.BaseActivity;
import com.bw.movie.view.adapter.paging.HomePageFragmentAdapter;
import com.bw.movie.view.fragment.CinemaFragment;
import com.bw.movie.view.fragment.MoiveFragment;
import com.bw.movie.view.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomePageActivity extends BaseActivity<HomeFragPresenter> {
    @BindView(R.id.vp_main_viewPager)
    ViewPager vpMainViewPager;
    @BindView(R.id.rb_main_movie)
    RadioButton rbMainMovie;
    @BindView(R.id.rb_main_cinema)
    RadioButton rbMainCinema;
    @BindView(R.id.rb_main_my)
    RadioButton rbMainMy;
    @BindView(R.id.rg_main_radioGroup)
    RadioGroup rgMainRadioGroup;
    private Unbinder bind;
    private List<Fragment> fragments;
    private long mExitTime;
    private HomePageFragmentAdapter homePageFragmentAdapter;

    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        fragments.add(new MoiveFragment());
        fragments.add(new CinemaFragment());
        fragments.add(new MyFragment());

        homePageFragmentAdapter = new HomePageFragmentAdapter(getSupportFragmentManager(), fragments);
        vpMainViewPager.setAdapter(homePageFragmentAdapter);

        vpMainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        rgMainRadioGroup.check(R.id.rb_main_movie);
                        break;
                    case 1:
                        rgMainRadioGroup.check(R.id.rb_main_cinema);
                        break;
                    case 2:
                        rgMainRadioGroup.check(R.id.rb_main_my);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        rgMainRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_main_movie:
                        vpMainViewPager.setCurrentItem(0);
                        break;

                    case R.id.rb_main_cinema:
                        vpMainViewPager.setCurrentItem(1);
                        break;

                    case R.id.rb_main_my:
                        vpMainViewPager.setCurrentItem(2);
                        break;
                }
            }
        });
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
        return R.layout.activity_home;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出维度电影", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
