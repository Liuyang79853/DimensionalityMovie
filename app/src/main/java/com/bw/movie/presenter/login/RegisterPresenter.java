package com.bw.movie.presenter.login;

import com.bw.movie.model.bean.user.MovieRegisterBean;
import com.bw.movie.model.bean.user.SendCodeBean;
import com.bw.movie.model.okhttp.OkHttpUtils;
import com.bw.movie.presenter.base.BasePresenter;
import com.bw.movie.view.contract.IContractView;


public class RegisterPresenter extends BasePresenter<IContractView.IRegisterView> implements IContractView.Presenter {
    @Override
    public void movieLogin(String email, String pwd) {

    }

    @Override
    public void movieRegister(String nickName, String pwd, String email, String code) {
        OkHttpUtils.getInstance().movieRegister(new OkHttpUtils.IOkCallBack<MovieRegisterBean>() {
            @Override
            public void callSuccess(MovieRegisterBean bean) {
                getView().registerSuccess(bean);
            }

            @Override
            public void callError(String msg) {

            }
        },MovieRegisterBean.class,nickName,pwd,email,code);
    }

    @Override
    public void movieWXLogin(String code) {

    }

    @Override
    public void sendCode(String email) {
        OkHttpUtils.getInstance().sendCode(new OkHttpUtils.IOkCallBack<SendCodeBean>() {
            @Override
            public void callSuccess(SendCodeBean bean) {
                getView().sendCode(bean);
            }

            @Override
            public void callError(String msg) {

            }
        }, SendCodeBean.class,email);
    }
}
