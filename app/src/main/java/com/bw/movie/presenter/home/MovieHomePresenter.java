package com.bw.movie.presenter.home;

import android.util.Log;
import android.widget.Toast;

import com.bw.movie.model.bean.homepage.HotMovieBean;
import com.bw.movie.model.bean.homepage.IsHitMovieBean;
import com.bw.movie.model.bean.homepage.MovieFragBannerBean;
import com.bw.movie.model.bean.homepage.ReserveBean;
import com.bw.movie.model.bean.homepage.UpComingBean;
import com.bw.movie.model.okhttp.OkHttpUtils;
import com.bw.movie.presenter.base.BasePresenter;
import com.bw.movie.view.contract.IContractView;


public class MovieHomePresenter extends BasePresenter<IContractView.IMovieView> {
    public void movieBanner(){
        OkHttpUtils.getInstance().movieBanner(new OkHttpUtils.IOkCallBack<MovieFragBannerBean>() {
            @Override
            public void callSuccess(MovieFragBannerBean bean) {
                getView().movieBannerSuccess(bean);
                Log.e("hhhhhhhh",bean.getResult().get(0).getImageUrl());
            }

            @Override
            public void callError(String msg) {

            }
        }, MovieFragBannerBean.class);
    }

    public void IsHitMovie(int page,int count){
        OkHttpUtils.getInstance().isHitMovie(new OkHttpUtils.IOkCallBack<IsHitMovieBean>() {
            @Override
            public void callSuccess(IsHitMovieBean bean) {
                getView().IsHitSuccess(bean);
            }

            @Override
            public void callError(String msg) {

            }
        }, IsHitMovieBean.class,page,count);
    }

    public void upComingMovie(int page,int count){
        OkHttpUtils.getInstance().upComingMovie(new OkHttpUtils.IOkCallBack<UpComingBean>() {
            @Override
            public void callSuccess(UpComingBean bean) {
                getView().upComingSuccess(bean);
            }

            @Override
            public void callError(String msg) {

            }
        }, UpComingBean.class,page,count);
    }

    public void hotMovie(int page,int count){
        OkHttpUtils.getInstance().hotMovie(new OkHttpUtils.IOkCallBack<HotMovieBean>() {
            @Override
            public void callSuccess(HotMovieBean bean) {
                getView().hotSuccess(bean);
            }

            @Override
            public void callError(String msg) {

            }
        }, HotMovieBean.class,page,count);
    }

    public void reserve(int userId, String sessionId, final int movieId){
        OkHttpUtils.getInstance().reserve(new OkHttpUtils.IOkCallBack<ReserveBean>() {
            @Override
            public void callSuccess(ReserveBean bean) {
                getView().reserveSuccess(bean);
               Log.e("MyMessageMoviehahha",movieId+"");
            }

            @Override
            public void callError(String msg) {

            }
        }, ReserveBean.class,userId,sessionId,movieId);
    }
}
