package com.bw.movie.presenter.cinema;

import com.bw.movie.model.bean.cinema.CinemaDetailsBean;
import com.bw.movie.model.okhttp.OkHttpUtils;
import com.bw.movie.presenter.base.BasePresenter;
import com.bw.movie.view.contract.IContractView;

/**

 * function:影院详情
 */
public class CinemaDetailsPresenter extends BasePresenter<IContractView.ICinemaDetailsView> {
    public void cinemaInfo(int userId, String sessionId, int cinemaId) {
        OkHttpUtils.getInstance().cinemaInfo(new OkHttpUtils.IOkCallBack<CinemaDetailsBean>() {
            @Override
            public void callSuccess(CinemaDetailsBean bean) {
                getView().cinemaDetailsSuccess(bean);
            }

            @Override
            public void callError(String msg) {

            }
        }, CinemaDetailsBean.class,userId,sessionId,cinemaId);
    }
}
