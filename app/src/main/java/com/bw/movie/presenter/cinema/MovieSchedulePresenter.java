package com.bw.movie.presenter.cinema;

import com.bw.movie.model.bean.cinema.MovieScheduleBean;
import com.bw.movie.model.bean.cinema.PayBean;
import com.bw.movie.model.bean.cinema.SeatInfoBean;
import com.bw.movie.model.bean.moviebykey.MovieTicketsBean;
import com.bw.movie.model.okhttp.OkHttpUtils;
import com.bw.movie.presenter.base.BasePresenter;
import com.bw.movie.view.contract.IContractView;

/**

 * function:
 */
public class MovieSchedulePresenter extends BasePresenter<IContractView.IMovieScheduleView> {

    //根据电影ID和影院ID查询电影排期列表
    public void movieSchedule(int movieId,int cinemaId){
        OkHttpUtils.getInstance().movieSchedule(new OkHttpUtils.IOkCallBack<MovieScheduleBean>() {
            @Override
            public void callSuccess(MovieScheduleBean bean) {
                getView().movieScheduleSuccess(bean);
            }

            @Override
            public void callError(String msg) {

            }
        }, MovieScheduleBean.class,movieId,cinemaId);
    }

    //根据影厅id 查询座位信息
    public void seatInfo(int hallId){
        OkHttpUtils.getInstance().seatInfo(new OkHttpUtils.IOkCallBack<SeatInfoBean>() {
            @Override
            public void callSuccess(SeatInfoBean bean) {
                getView().seatInfoSuccess(bean);
            }

            @Override
            public void callError(String msg) {

            }
        }, SeatInfoBean.class,hallId);
    }

    //购票下单
    public void buyMovieTickets(int userId,String sessionId,int scheduleId,String seat,String sign){
        OkHttpUtils.getInstance().buyMovieTickets(new OkHttpUtils.IOkCallBack<MovieTicketsBean>() {
            @Override
            public void callSuccess(MovieTicketsBean bean) {
                getView().buyMovieTicketsSuccess(bean);
            }

            @Override
            public void callError(String msg) {

            }
        }, MovieTicketsBean.class,userId,sessionId,scheduleId,seat,sign);
    }

    //支付
    public void pay(int userId,String sessionId,int payType,String orderId){
        OkHttpUtils.getInstance().pay(new OkHttpUtils.IOkCallBack<PayBean>() {
            @Override
            public void callSuccess(PayBean bean) {
                getView().paySuccess(bean);
            }

            @Override
            public void callError(String msg) {

            }
        }, PayBean.class,userId,sessionId,payType,orderId);
    }

}
