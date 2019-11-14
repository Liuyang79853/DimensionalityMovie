package com.bw.movie.view.fragment.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.model.bean.details.MovieDetailsBean;
import com.bw.movie.presenter.details.MovieDetailsPresenter;
import com.bw.movie.view.adapter.details.still.RecyclerStillMovieActorAdapter;
import com.bw.movie.view.contract.IContractView;
import com.bw.movie.view.interfaces.IBaseView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;


public class MovieDetailsStillFragment extends Fragment implements IContractView.IMovieDetailsView, IBaseView {
    @BindView(R.id.recycler_still_posterList)
    RecyclerView recyclerStillPosterList;
    Unbinder unbinder;
    private MovieDetailsPresenter movieDetailsPresenter;
    private RecyclerStillMovieActorAdapter recyclerStillMovieActorAdapter;
    private List<String> listImageUrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_moviedetalis_still, null);
        unbinder = ButterKnife.bind(this, inflate);
        Intent intent = getActivity().getIntent();
        int movieId = intent.getIntExtra("movieId", 0);
        movieDetailsPresenter = new MovieDetailsPresenter();
        movieDetailsPresenter.attachView(this);
        movieDetailsPresenter.movieDetails(movieId);
        return inflate;
    }

    @Override
    public void movieDetailsSuccess(MovieDetailsBean movieDetailsBean) {
        List<String> posterList = movieDetailsBean.getResult().getPosterList();
        //电影预告片
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerStillMovieActorAdapter = new RecyclerStillMovieActorAdapter(getContext());
        recyclerStillMovieActorAdapter.addData(posterList);
        recyclerStillPosterList.setLayoutManager(gridLayoutManager);
        recyclerStillPosterList.setAdapter(recyclerStillMovieActorAdapter);

    }

    @Override
    public void movieDetailsError(String msg) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
