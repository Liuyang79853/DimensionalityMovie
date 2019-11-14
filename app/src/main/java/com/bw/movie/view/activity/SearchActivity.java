package com.bw.movie.view.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.model.bean.moviebykey.MovieByKeyWordBean;
import com.bw.movie.presenter.thekeyword.MovieByKeywordPresenter;
import com.bw.movie.view.activity.base.BaseActivity;
import com.bw.movie.view.adapter.moviebykey.RecyclerKeyWordAdapter;
import com.bw.movie.view.contract.IContractView;
import com.bw.movie.view.customview.SyLinearLayoutManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
/*
* 根据关键字查询电影信息
* */
public class SearchActivity extends BaseActivity<MovieByKeywordPresenter> implements IContractView.IMovieByKeyWordView {

    @BindView(R.id.et_movie_thekeyword)
    EditText etMovieThekeyword;
    @BindView(R.id.iv_search_movie)
    ImageView ivSearchMovie;
    @BindView(R.id.recycler_thekeyword)
    RecyclerView recyclerThekeyword;
    private Unbinder bind;
    private int page = 1;
    private int count = 5;
    private List<MovieByKeyWordBean.ResultBean> keyWordBeanResult;
    private RecyclerKeyWordAdapter recyclerKeyWordAdapter;

    @Override
    protected void initData() {
        etMovieThekeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = etMovieThekeyword.getText().toString().trim();
                presenter.movieByKeyWord(keyword,page,count);
            }
        });
    }

    @Override
    protected MovieByKeywordPresenter setPresenter() {
        return new MovieByKeywordPresenter();
    }

    @Override
    protected void initView() {
        bind = ButterKnife.bind(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void movieByKeyWordSuccess(MovieByKeyWordBean movieByKeyWordBean) {
        if (movieByKeyWordBean.getMessage().equals("未查到相关电影")){
            Toast.makeText(this,movieByKeyWordBean.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }else{
            keyWordBeanResult = movieByKeyWordBean.getResult();
            SyLinearLayoutManager syLinearLayoutManager = new SyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerKeyWordAdapter = new RecyclerKeyWordAdapter(this);
            recyclerKeyWordAdapter.addData(keyWordBeanResult);
            recyclerThekeyword.setLayoutManager(syLinearLayoutManager);
            recyclerThekeyword.setAdapter(recyclerKeyWordAdapter);
        }
    }

    @Override
    public void movieByKeyWordError(String msg) {

    }

    @OnClick(R.id.iv_search_movie)
    public void onClick() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
