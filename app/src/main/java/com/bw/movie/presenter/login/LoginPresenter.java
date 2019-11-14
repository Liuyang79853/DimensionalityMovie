package com.bw.movie.presenter.login;

import com.bw.movie.model.bean.user.MovieLoginBean;
import com.bw.movie.model.bean.user.WXLoginBean;
import com.bw.movie.model.okhttp.OkHttpUtils;
import com.bw.movie.presenter.base.BasePresenter;
import com.bw.movie.view.contract.IContractView;


public class LoginPresenter extends BasePresenter<IContractView.ILoginView> implements IContractView.Presenter {
    @Override
    public void movieLogin(String email, String pwd) {
        OkHttpUtils.getInstance().movieLogin(new OkHttpUtils.IOkCallBack<MovieLoginBean>() {
            @Override
            public void callSuccess(MovieLoginBean bean) {
                getView().loginSuccess(bean);
            }

            @Override
            public void callError(String msg) {

            }
        }, MovieLoginBean.class,email,pwd);
    }

    @Override
    public void movieRegister(String nickName, String pwd, String email, String code) {

    }

    @Override
    public void movieWXLogin(String code) {
        OkHttpUtils.getInstance().movieWXLogin(new OkHttpUtils.IOkCallBack<WXLoginBean>() {
            @Override
            public void callSuccess(WXLoginBean bean) {
                getView().loginWXSuccess(bean);
            }

            @Override
            public void callError(String msg) {

            }
        }, WXLoginBean.class,code);
    }

    @Override
    public void sendCode(String email) {

    }
}
