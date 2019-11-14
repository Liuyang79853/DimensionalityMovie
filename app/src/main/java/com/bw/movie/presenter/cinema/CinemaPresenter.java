package com.bw.movie.presenter.cinema;

import com.bw.movie.model.bean.cinema.NearCinemaBean;
import com.bw.movie.model.bean.cinema.RecommendedCinemaBean;
import com.bw.movie.model.okhttp.OkHttpUtils;
import com.bw.movie.presenter.base.BasePresenter;
import com.bw.movie.view.contract.IContractView;

/**

 * function:
 */
public class CinemaPresenter extends BasePresenter<IContractView.ICinemaView> {
    public void recommendedCinema(int page,int count){
        OkHttpUtils.getInstance().recommendedCinema(new OkHttpUtils.IOkCallBack<RecommendedCinemaBean>() {
            @Override
            public void callSuccess(RecommendedCinemaBean bean) {
                getView().recommendedCinemaSuccess(bean);
            }

            @Override
            public void callError(String msg) {

            }
        },RecommendedCinemaBean.class,page,count);
    }

    public void nearCinema(int page,int count){
        OkHttpUtils.getInstance().nearCinema(new OkHttpUtils.IOkCallBack<NearCinemaBean>() {
            @Override
            public void callSuccess(NearCinemaBean bean) {
                getView().nearCinemaSuccess(bean);
            }

            @Override
            public void callError(String msg) {

            }
        },NearCinemaBean.class,page,count);
    }
}
