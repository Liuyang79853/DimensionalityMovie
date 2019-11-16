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
import com.bw.movie.model.bean.details.MovieCommentBean;
import com.bw.movie.presenter.details.MovieCommentPresenter;
import com.bw.movie.view.adapter.details.filmcritics.RecyclerFilmCriticsAdapter;
import com.bw.movie.view.contract.IContractView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * function:影评
 */
public class MovieDetailsFilmCriticsFragment extends Fragment implements IContractView.IMovieCommentView{

    @BindView(R.id.recycler_filmcritics_moviecomment)
    RecyclerView recyclerFilmcriticsMoviecomment;
    Unbinder unbinder;
    private int page = 1;
    private int count = 5;
    private MovieCommentPresenter movieCommentPresenter;
    private List<MovieCommentBean.ResultBean> commentBeanResult;
    private RecyclerFilmCriticsAdapter recyclerFilmCriticsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_moviedetalis_filmcritics, null);
        unbinder = ButterKnife.bind(this, inflate);
        Intent intent = getActivity().getIntent();
        int movieId = intent.getIntExtra("movieId", 0);
        Log.e("movieId影评", movieId + "");
        movieCommentPresenter = new MovieCommentPresenter();
        movieCommentPresenter.attachView(this);
        movieCommentPresenter.movieComment(movieId, page, count);
        return inflate;
    }

    @Override
    public void movieCommentSuccess(MovieCommentBean movieCommentBean) {
        Log.e("MyMessage影评",movieCommentBean.getResult().get(0).getCommentUserName());
        commentBeanResult = movieCommentBean.getResult();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerFilmCriticsAdapter = new RecyclerFilmCriticsAdapter(getActivity());
        recyclerFilmCriticsAdapter.addData(commentBeanResult);
        recyclerFilmcriticsMoviecomment.setLayoutManager(linearLayoutManager);
        recyclerFilmcriticsMoviecomment.setAdapter(recyclerFilmCriticsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
