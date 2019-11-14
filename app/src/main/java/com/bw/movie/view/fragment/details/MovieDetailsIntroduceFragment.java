package com.bw.movie.view.fragment.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.model.bean.details.MovieDetailsBean;
import com.bw.movie.presenter.details.MovieDetailsPresenter;
import com.bw.movie.view.adapter.details.introduce.RecyclerIntroduceMovieActorAdapter;
import com.bw.movie.view.adapter.details.introduce.RecyclerIntroduceMovieDirectorAdapter;
import com.bw.movie.view.contract.IContractView;
import com.bw.movie.view.customview.SyLinearLayoutManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MovieDetailsIntroduceFragment extends Fragment implements IContractView.IMovieDetailsView {
    Unbinder unbinder;
    @BindView(R.id.tv_introduce_summary)
    TextView tvIntroduceSummary;
    @BindView(R.id.recycler_introduce_movieDirector)
    RecyclerView recyclerIntroduceMovieDirector;
    @BindView(R.id.recycler_introduce_movieActor)
    RecyclerView recyclerIntroduceMovieActor;
    private MovieDetailsPresenter movieDetailsPresenter;
    private RecyclerIntroduceMovieDirectorAdapter recyclerIntroduceMovieDirectorAdapter;
    //导演Bean
    private List<MovieDetailsBean.ResultBean.MovieDirectorBean> movieDirector;
    //演员的Bean
    private List<MovieDetailsBean.ResultBean.MovieActorBean> movieActor;
    private RecyclerIntroduceMovieActorAdapter recyclerIntroduceMovieActorAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_moviedetalis_introduce, null);
        unbinder = ButterKnife.bind(this, inflate);
        Intent intent = getActivity().getIntent();
        int movieId = intent.getIntExtra("movieId", 0);
        movieDetailsPresenter = new MovieDetailsPresenter();
        movieDetailsPresenter.attachView(this);
        movieDetailsPresenter.movieDetails(movieId);
        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //电影详情
    @Override
    public void movieDetailsSuccess(MovieDetailsBean movieDetailsBean) {
        tvIntroduceSummary.setText(movieDetailsBean.getResult().getSummary());
        //导演的详情介绍
        movieDirector = movieDetailsBean.getResult().getMovieDirector();
        recyclerIntroduceMovieDirectorAdapter = new RecyclerIntroduceMovieDirectorAdapter(getContext());
        recyclerIntroduceMovieDirectorAdapter.addData(movieDirector);
        recyclerIntroduceMovieDirector.setLayoutManager(new SyLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerIntroduceMovieDirector.setAdapter(recyclerIntroduceMovieDirectorAdapter);

        //演员的详情介绍
        movieActor = movieDetailsBean.getResult().getMovieActor();
        recyclerIntroduceMovieActorAdapter = new RecyclerIntroduceMovieActorAdapter(getContext());
        recyclerIntroduceMovieActorAdapter.addData(movieActor);
        recyclerIntroduceMovieActor.setLayoutManager(new SyLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerIntroduceMovieActor.setAdapter(recyclerIntroduceMovieActorAdapter);
    }

    @Override
    public void movieDetailsError(String msg) {

    }
}
