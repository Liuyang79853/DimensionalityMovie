package com.bw.movie.presenter.thekeyword;

import com.bw.movie.model.bean.moviebykey.MovieByKeyWordBean;
import com.bw.movie.model.okhttp.OkHttpUtils;
import com.bw.movie.presenter.base.BasePresenter;
import com.bw.movie.view.contract.IContractView;

/**
 * function:关键字查询电影
 */
public class MovieByKeywordPresenter extends BasePresenter<IContractView.IMovieByKeyWordView> {
    public void movieByKeyWord(String keyword,int page,int count){
        OkHttpUtils.getInstance().movieByKeyword(new OkHttpUtils.IOkCallBack<MovieByKeyWordBean>() {
            @Override
            public void callSuccess(MovieByKeyWordBean bean) {
                getView().movieByKeyWordSuccess(bean);
            }

            @Override
            public void callError(String msg) {

            }
        },MovieByKeyWordBean.class,keyword,page,count);
    }
}
