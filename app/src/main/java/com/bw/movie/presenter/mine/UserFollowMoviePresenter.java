package com.bw.movie.presenter.mine;

import com.bw.movie.model.bean.mine.UserFollowMovieBean;
import com.bw.movie.model.okhttp.OkHttpUtils;
import com.bw.movie.presenter.base.BasePresenter;
import com.bw.movie.view.contract.IContractView;

/**

 * function:
 */
public class UserFollowMoviePresenter extends BasePresenter<IContractView.IUserFollowMovieView> {
    public void userFollowMovie(int userId,String sessionId,int page,int count){
        OkHttpUtils.getInstance().userFollowMovie(new OkHttpUtils.IOkCallBack<UserFollowMovieBean>() {
            @Override
            public void callSuccess(UserFollowMovieBean bean) {
                getView().userFollowMovieSuccess(bean);
            }

            @Override
            public void callError(String msg) {

            }
        },UserFollowMovieBean.class,userId,sessionId,page,count);
    }
}
