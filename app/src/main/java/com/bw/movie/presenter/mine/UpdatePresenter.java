package com.bw.movie.presenter.mine;

import com.bw.movie.model.bean.mine.UpdateBean;
import com.bw.movie.model.okhttp.OkHttpUtils;
import com.bw.movie.presenter.base.BasePresenter;
import com.bw.movie.view.contract.IContractView;

/**

 * function:
 */
public class UpdatePresenter extends BasePresenter<IContractView.IUpdateView> {

    public void upDate(int userId,String sessionId,String ak){
        OkHttpUtils.getInstance().update(new OkHttpUtils.IOkCallBack<UpdateBean>() {
            @Override
            public void callSuccess(UpdateBean bean) {
                getView().updateSuccess(bean);
            }

            @Override
            public void callError(String msg) {

            }
        }, UpdateBean.class,userId,sessionId,ak);
    }

}
