package com.bw.movie.presenter.cinema;

import com.bw.movie.model.bean.cinema.FindAllCinemaCommentBean;
import com.bw.movie.model.okhttp.OkHttpUtils;
import com.bw.movie.presenter.base.BasePresenter;
import com.bw.movie.view.contract.IContractView;

/**

 * function:
 */
public class AllCinemaCommentPresenter extends BasePresenter<IContractView.IAllCinemaCommentView> {

    public void allCinemaComment(int userId, String sessionId, int cinemaId, int page, int count){
        OkHttpUtils.getInstance().findAllCinemaComment(new OkHttpUtils.IOkCallBack<FindAllCinemaCommentBean>() {
            @Override
            public void callSuccess(FindAllCinemaCommentBean bean) {
                getView().allCinemaCommentSuccess(bean);
            }

            @Override
            public void callError(String msg) {

            }
        }, FindAllCinemaCommentBean.class,userId,sessionId,cinemaId,page,count);
    }

}
