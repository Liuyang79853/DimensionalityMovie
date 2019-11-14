package com.bw.movie.view.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bw.movie.presenter.base.BasePresenter;
import com.bw.movie.view.interfaces.IBaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * function:
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IBaseView {

    public P presenter;
    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindLayout());

        initView();
        presenter = setPresenter();
        presenter.attachView(this);
        initData();
    }

    protected abstract void initData();

    protected abstract P setPresenter();

    protected abstract void initView();

    protected abstract int bindLayout();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();

    }

}
