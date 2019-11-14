package com.bw.movie.presenter.base;

import com.bw.movie.view.interfaces.IBaseView;


public class BasePresenter<V extends IBaseView>{
    private V mView;

    public void attachView(V v){
        this.mView = v;
    }

    public void detachView(){
        this.mView = null;
    }

    public V getView(){
        return mView;
    }
}
