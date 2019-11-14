package com.bw.movie.presenter.cinema;

import com.bw.movie.model.bean.cinema.CinemaByRegionBean;
import com.bw.movie.model.bean.cinema.RegionListBean;
import com.bw.movie.model.okhttp.OkHttpUtils;
import com.bw.movie.presenter.base.BasePresenter;
import com.bw.movie.view.contract.IContractView;


public class RegionListPresenter extends BasePresenter<IContractView.IRegionListView> {

    //查询区域列表
    public void regionList(){
        OkHttpUtils.getInstance().regionList(new OkHttpUtils.IOkCallBack<RegionListBean>() {
            @Override
            public void callSuccess(RegionListBean bean) {
                getView().regionSuccess(bean);
            }

            @Override
            public void callError(String msg) {

            }
        }, RegionListBean.class);
    }

    //根据区域查询影院
    public void cinemaByRegion(int regionId){
        OkHttpUtils.getInstance().cinemaByRegion(new OkHttpUtils.IOkCallBack<CinemaByRegionBean>() {
            @Override
            public void callSuccess(CinemaByRegionBean bean) {
                getView().cinemaByRegionSuccess(bean);
            }

            @Override
            public void callError(String msg) {

            }
        }, CinemaByRegionBean.class,regionId);
    }

}
