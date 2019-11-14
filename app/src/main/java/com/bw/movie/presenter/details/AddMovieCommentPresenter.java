package com.bw.movie.presenter.details;

import com.bw.movie.model.bean.details.AddMovieCommentBean;
import com.bw.movie.model.okhttp.OkHttpUtils;
import com.bw.movie.presenter.base.BasePresenter;
import com.bw.movie.view.contract.IContractView;


public class AddMovieCommentPresenter extends BasePresenter<IContractView.IAddMovieCommentView> {
    public void addMovieComment(int userId,String sessionId,int movieId,String commentContent,double score){
        OkHttpUtils.getInstance().addMovieComment(new OkHttpUtils.IOkCallBack<AddMovieCommentBean>() {
            @Override
            public void callSuccess(AddMovieCommentBean bean) {
                getView().addMovieCommentSuccess(bean);
            }

            @Override
            public void callError(String msg) {

            }
        },AddMovieCommentBean.class,userId,sessionId,movieId,commentContent,score);
    }
}
