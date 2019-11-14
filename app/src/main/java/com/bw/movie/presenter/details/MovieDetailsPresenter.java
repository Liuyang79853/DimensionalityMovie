package com.bw.movie.presenter.details;

import com.bw.movie.model.bean.details.MovieDetailsBean;
import com.bw.movie.model.okhttp.OkHttpUtils;
import com.bw.movie.presenter.base.BasePresenter;
import com.bw.movie.view.contract.IContractView;


public class MovieDetailsPresenter extends BasePresenter<IContractView.IMovieDetailsView> {
    public void movieDetails(int movieId){
        OkHttpUtils.getInstance().movieDetails(new OkHttpUtils.IOkCallBack<MovieDetailsBean>() {
            @Override
            public void callSuccess(MovieDetailsBean bean) {
                getView().movieDetailsSuccess(bean);
            }

            @Override
            public void callError(String msg) {

            }
        }, MovieDetailsBean.class,movieId);
    }
}
