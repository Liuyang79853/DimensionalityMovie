package com.bw.movie.view.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.model.bean.details.MovieDetailsBean;
import com.bw.movie.presenter.details.MovieDetailsPresenter;
import com.bw.movie.view.activity.base.BaseActivity;
import com.bw.movie.view.adapter.paging.DetailsPageFragmentAdapter;
import com.bw.movie.view.contract.IContractView;
import com.bw.movie.view.dateutils.DateUtils;
import com.bw.movie.view.fragment.details.MovieDetailsFilmCriticsFragment;
import com.bw.movie.view.fragment.details.MovieDetailsIntroduceFragment;
import com.bw.movie.view.fragment.details.MovieDetailsStillFragment;
import com.bw.movie.view.fragment.details.MovieDetailsTrailerFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MovieDetailsActivity extends BaseActivity<MovieDetailsPresenter> implements IContractView.IMovieDetailsView {

    @BindView(R.id.iv_details_imageUrl)
    ImageView ivDetailsImageUrl;
    @BindView(R.id.tv_details_score)
    TextView tvDetailsScore;
    @BindView(R.id.tv_details_commentNum)
    TextView tvDetailsCommentNum;
    @BindView(R.id.tv_details_name)
    TextView tvDetailsName;
    @BindView(R.id.tv_details_movieType)
    TextView tvDetailsMovieType;
    @BindView(R.id.tv_details_duration)
    TextView tvDetailsDuration;
    @BindView(R.id.tv_details_releaseTime)
    TextView tvDetailsReleaseTime;
    @BindView(R.id.btn_film_critics)
    Button btnFilmCritics;
    @BindView(R.id.btn_choose_ticket)
    Button btnChooseTicket;
    @BindView(R.id.tab_layout_details)
    TabLayout tabLayoutDetails;
    @BindView(R.id.view_pager_details)
    ViewPager viewPagerDetails;
    private Unbinder bind;
    private int movieId;
    private List<Fragment> detailsPageFragments;
    private DetailsPageFragmentAdapter detailsPageFragmentAdapter;
    private MovieDetailsBean.ResultBean movieDetailsResult;

    @Override
    protected void initData() {
        Intent intent = getIntent();
        movieId = intent.getIntExtra("movieId", 0);
        presenter.movieDetails(movieId);
        Toast.makeText(this, movieId + "", Toast.LENGTH_SHORT).show();

        detailsPageFragments = new ArrayList<>();
        detailsPageFragments.add(new MovieDetailsIntroduceFragment());
        detailsPageFragments.add(new MovieDetailsTrailerFragment());
        detailsPageFragments.add(new MovieDetailsStillFragment());
        detailsPageFragments.add(new MovieDetailsFilmCriticsFragment());
        detailsPageFragmentAdapter = new DetailsPageFragmentAdapter(getSupportFragmentManager(), detailsPageFragments);
        viewPagerDetails.setAdapter(detailsPageFragmentAdapter);

        for (int i = 0; i < detailsPageFragments.size(); i++) {
            tabLayoutDetails.addTab(tabLayoutDetails.newTab());
        }

        tabLayoutDetails.setupWithViewPager(viewPagerDetails);

        tabLayoutDetails.getTabAt(0).setText("介绍");
        tabLayoutDetails.getTabAt(1).setText("预告");
        tabLayoutDetails.getTabAt(2).setText("剧照");
        tabLayoutDetails.getTabAt(3).setText("影评");
    }

    @Override
    protected MovieDetailsPresenter setPresenter() {
        return new MovieDetailsPresenter();
    }

    @Override
    protected void initView() {
        bind = ButterKnife.bind(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_movie_details;
    }

    @Override
    public void movieDetailsSuccess(MovieDetailsBean movieDetailsBean) {
        movieDetailsResult = movieDetailsBean.getResult();
        tvDetailsScore.setText(movieDetailsResult.getScore() + "分");
        tvDetailsCommentNum.setText(movieDetailsResult.getCommentNum() + "万条");
        tvDetailsMovieType.setText(movieDetailsResult.getMovieType());
        tvDetailsName.setText(movieDetailsResult.getName());
        String times = DateUtils.times(movieDetailsResult.getReleaseTime());
        tvDetailsReleaseTime.setText(times + "   " + "中国大陆上映");
        tvDetailsDuration.setText(movieDetailsResult.getDuration());
        Glide.with(this).load(movieDetailsResult.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(ivDetailsImageUrl);
    }

    @Override
    public void movieDetailsError(String msg) {

    }

    @OnClick({R.id.btn_film_critics, R.id.btn_choose_ticket})
    public void onClick(View view) {
        switch (view.getId()) {
            //写影评
            case R.id.btn_film_critics:
                Intent intent = new Intent(MovieDetailsActivity.this, AddMovieCommentActivity.class);
                intent.putExtra("name",movieDetailsResult.getName());
                intent.putExtra("movieId",movieDetailsResult.getMovieId());
                startActivity(intent);
                break;
                //购票
            case R.id.btn_choose_ticket:
                Intent intent1 = new Intent(MovieDetailsActivity.this,ChoseCinemaActivity.class);
                //传视频
                intent1.putExtra("videoUrl",movieDetailsResult.getShortFilmList().get(0).getVideoUrl());
                //传照片
                intent1.putExtra("imageUrl",movieDetailsResult.getShortFilmList().get(0).getImageUrl());
                //传电影名称
                intent1.putExtra("movieName",movieDetailsResult.getName());
                //传评分
                intent1.putExtra("movieScore",movieDetailsResult.getScore());
                //传电影ID
                intent1.putExtra("movieId",movieDetailsResult.getMovieId());
                //传时长
                intent1.putExtra("movieDuration",movieDetailsResult.getDuration());
                //传导演名字
                intent1.putExtra("movieDirector",movieDetailsResult.getMovieDirector().get(0).getName());
                startActivity(intent1);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
