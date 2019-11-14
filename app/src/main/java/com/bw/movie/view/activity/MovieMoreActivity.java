package com.bw.movie.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.presenter.thekeyword.MovieByKeywordPresenter;
import com.bw.movie.view.adapter.paging.MovieMorePageAdapter;
import com.bw.movie.view.fragment.more.MovieMoreHotFragment;
import com.bw.movie.view.fragment.more.MovieMoreIsHitFragment;
import com.bw.movie.view.fragment.more.MovieMoreUpCommingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieMoreActivity extends AppCompatActivity {

    @BindView(R.id.tab_layout_moviemore)
    TabLayout tabLayoutMoviemore;
    @BindView(R.id.view_pager_moviemore)
    ViewPager viewPagerMoviemore;
    @BindView(R.id.iv_search_movie_more)
    ImageView ivSearchMovieMore;
    private Unbinder bind;
    private List<Fragment> fragments;
    private MovieMorePageAdapter movieMorePageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_more);
        bind = ButterKnife.bind(this);

        fragments = new ArrayList<>();
        fragments.add(new MovieMoreIsHitFragment());
        fragments.add(new MovieMoreUpCommingFragment());
        fragments.add(new MovieMoreHotFragment());
        movieMorePageAdapter = new MovieMorePageAdapter(getSupportFragmentManager(), fragments);
        viewPagerMoviemore.setAdapter(movieMorePageAdapter);

        for (int i = 0; i < fragments.size(); i++) {
            tabLayoutMoviemore.addTab(tabLayoutMoviemore.newTab());
        }

        tabLayoutMoviemore.setupWithViewPager(viewPagerMoviemore);

        tabLayoutMoviemore.getTabAt(0).setText("正在热映");
        tabLayoutMoviemore.getTabAt(1).setText("即将上映");
        tabLayoutMoviemore.getTabAt(2).setText("热门电影");

        ivSearchMovieMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MovieMoreActivity.this,SearchActivity.class));
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
