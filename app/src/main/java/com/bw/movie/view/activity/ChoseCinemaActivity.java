package com.bw.movie.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.model.bean.cinema.CinemaByRegionBean;
import com.bw.movie.model.bean.cinema.RegionListBean;
import com.bw.movie.presenter.cinema.RegionListPresenter;
import com.bw.movie.view.activity.base.BaseActivity;
import com.bw.movie.view.adapter.cinema.RecyclerRecommendedCinemaAdapter;
import com.bw.movie.view.adapter.cinema.RecyclerRegionAdapter;
import com.bw.movie.view.adapter.cinema.RecyclerRegionCinemaAdapter;
import com.bw.movie.view.contract.IContractView;
import com.bw.movie.view.customview.SyLinearLayoutManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class ChoseCinemaActivity extends BaseActivity<RegionListPresenter> implements IContractView.IRegionListView {

    @BindView(R.id.jcv_cinema_movie_video)
    JCVideoPlayer jcvCinemaMovieVideo;
    @BindView(R.id.tv_cinema_movie_name)
    TextView tvCinemaMovieName;
    @BindView(R.id.tv_cinema_movie_duration)
    TextView tvCinemaMovieDuration;
    @BindView(R.id.tv_cinema_movie_score)
    TextView tvCinemaMovieScore;
    @BindView(R.id.tv_cinema_movie_director)
    TextView tvCinemaMovieDirector;
    @BindView(R.id.recycler_cinema_region_cinema)
    RecyclerView recyclerCinemaRegionCinema;
    @BindView(R.id.btn_chose_region)
    Button btnChoseRegion;
    private Unbinder bind;
    private String videoUrl;
    private String imageUrl;
    private String movieName;
    private int movieId;
    private List<RegionListBean.ResultBean> result;
    private RecyclerRegionAdapter recyclerRegionAdapter;
    private List<CinemaByRegionBean.ResultBean> cinemaByRegionBeanResult;
    private RecyclerRegionCinemaAdapter recyclerRegionCinemaAdapter;

    @Override
    protected void initData() {
        Intent intent = getIntent();
        videoUrl = intent.getStringExtra("videoUrl");
        imageUrl = intent.getStringExtra("imageUrl");
        movieName = intent.getStringExtra("movieName");
        movieId = intent.getIntExtra("movieId", 0);
        double movieScore = intent.getDoubleExtra("movieScore", 0);
        String movieDuration = intent.getStringExtra("movieDuration");
        String movieDirector = intent.getStringExtra("movieDirector");

        //设电影名字
        tvCinemaMovieName.setText(movieName);
        //电影评分
        tvCinemaMovieScore.setText(movieScore + "分");
        //电影时长
        tvCinemaMovieDuration.setText(movieDuration);
        //导演
        tvCinemaMovieDirector.setText(movieDirector);
        //设置视频
        jcvCinemaMovieVideo.setUp(videoUrl, "");
        //加载视频第一针图片
        Glide.with(this).load(imageUrl).into(jcvCinemaMovieVideo.ivThumb);
    }

    @Override
    protected RegionListPresenter setPresenter() {
        return new RegionListPresenter();
    }

    @Override
    protected void initView() {
        bind = ButterKnife.bind(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_chose_cinema;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    //区域下拉框
    @Override
    public void regionSuccess(RegionListBean regionListBean) {
        result = regionListBean.getResult();
        recyclerRegionAdapter = new RecyclerRegionAdapter(this);
        recyclerRegionAdapter.addData(result);
        recyclerCinemaRegionCinema.setLayoutManager(new SyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerCinemaRegionCinema.setAdapter(recyclerRegionAdapter);
        recyclerRegionAdapter.onItemClickListener(new RecyclerRegionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, int regionId) {
                btnChoseRegion.setText(result.get(position).getRegionName());
                presenter.cinemaByRegion(regionId);
            }
        });
    }

    //区域影院
    @Override
    public void cinemaByRegionSuccess(CinemaByRegionBean cinemaByRegionBean) {
        cinemaByRegionBeanResult = cinemaByRegionBean.getResult();
        recyclerRegionCinemaAdapter = new RecyclerRegionCinemaAdapter(this);
        recyclerRegionCinemaAdapter.addData(cinemaByRegionBeanResult);
        recyclerCinemaRegionCinema.setLayoutManager(new SyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerCinemaRegionCinema.setAdapter(recyclerRegionCinemaAdapter);
        recyclerRegionCinemaAdapter.onItemClickListener(new RecyclerRegionCinemaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, int cinemaId) {
                Intent intent = new Intent(ChoseCinemaActivity.this, BuyTicketActivity.class);
                intent.putExtra("videoUrl", videoUrl);
                intent.putExtra("imageUrl", imageUrl);
                intent.putExtra("movieName", movieName);
                intent.putExtra("movieId", movieId);
                intent.putExtra("cinemaId", cinemaId);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.btn_chose_region)
    public void onClick() {
        presenter.regionList();
    }
}
