package com.bw.movie.view.fragment.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.model.bean.details.MovieDetailsBean;
import com.bw.movie.presenter.details.MovieDetailsPresenter;
import com.bw.movie.view.adapter.details.trailer.RecyclerTrailerMovieActorAdapter;
import com.bw.movie.view.contract.IContractView;
import com.bw.movie.view.customview.SyLinearLayoutManager;
import com.bw.movie.view.interfaces.IBaseView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * function:预告
 */
public class MovieDetailsTrailerFragment extends Fragment implements IContractView.IMovieDetailsView, IBaseView {
    @BindView(R.id.recycler_trailer_videoUrl)
    RecyclerView recyclerTrailerVideoUrl;
    Unbinder unbinder;
    private MovieDetailsPresenter movieDetailsPresenter;
    private int movieId;
    private List<MovieDetailsBean.ResultBean.ShortFilmListBean> shortFilmList;
    private RecyclerTrailerMovieActorAdapter recyclerTrailerMovieActorAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_moviedetalis_trailer, null);
        unbinder = ButterKnife.bind(this, inflate);
        Intent intent = getActivity().getIntent();
        movieId = intent.getIntExtra("movieId", 0);
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
        Log.e("MyMessage预告",movieDetailsBean.getResult().getName());
        //电影预告片
        shortFilmList = movieDetailsBean.getResult().getShortFilmList();
        recyclerTrailerMovieActorAdapter = new RecyclerTrailerMovieActorAdapter(getContext());
        recyclerTrailerMovieActorAdapter.addData(shortFilmList);
        recyclerTrailerVideoUrl.setLayoutManager(new SyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerTrailerVideoUrl.setAdapter(recyclerTrailerMovieActorAdapter);

    }

    @Override
    public void movieDetailsError(String msg) {

    }
}
