package com.bw.movie.presenter.mine;

import com.bw.movie.model.bean.mine.SysMsgBean;
import com.bw.movie.model.okhttp.OkHttpUtils;
import com.bw.movie.presenter.base.BasePresenter;
import com.bw.movie.view.contract.IContractView;

/**
 * function:系统消息
 */
public class SysMsgPresenter extends BasePresenter<IContractView.ISysMsgView> {

    public void sysMsg(int userId,String sessionId,int page,int count){
        OkHttpUtils.getInstance().sysMsg(new OkHttpUtils.IOkCallBack<SysMsgBean>() {
            @Override
            public void callSuccess(SysMsgBean bean) {
                getView().sysMsgSuccess(bean);
            }

            @Override
            public void callError(String msg) {

            }
        },SysMsgBean.class,userId,sessionId,page,count);
    }

}
