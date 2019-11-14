package com.bw.movie.presenter.details;

import com.bw.movie.model.bean.details.MovieCommentBean;
import com.bw.movie.model.okhttp.OkHttpUtils;
import com.bw.movie.presenter.base.BasePresenter;
import com.bw.movie.view.contract.IContractView;


public class MovieCommentPresenter extends BasePresenter<IContractView.IMovieCommentView> {
    public void movieComment(int movieId,int page,int count){
        OkHttpUtils.getInstance().movieComment(new OkHttpUtils.IOkCallBack<MovieCommentBean>() {
            @Override
            public void callSuccess(MovieCommentBean bean) {
                getView().movieCommentSuccess(bean);
            }

            @Override
            public void callError(String msg) {

            }
        },MovieCommentBean.class,movieId,page,count);
    }
}
