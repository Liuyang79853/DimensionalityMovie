package com.bw.movie.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bw.movie.R;
import com.bw.movie.presenter.home.HomeFragPresenter;
import com.bw.movie.view.activity.LoginActivity;
import com.bw.movie.view.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UserInformationActivity extends BaseActivity<HomeFragPresenter> {

    @BindView(R.id.iv_user_headpic)
    ImageView ivUserHeadpic;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_sex)
    TextView tvUserSex;
    @BindView(R.id.tv_user_email)
    TextView tvUserEmail;
    private Unbinder bind;

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String nickName = intent.getStringExtra("nickName");
        String headPic = intent.getStringExtra("headPic");
        String email = intent.getStringExtra("email");
        int sex = intent.getIntExtra("sex",0);

        tvUserEmail.setText(email);
        tvUserName.setText(nickName);
        if (nickName == null){
            startActivity(new Intent(UserInformationActivity.this, LoginActivity.class));
        }
        if (sex == 1){
            tvUserSex.setText("男");
        }else if (sex == 2){
            tvUserSex.setText("女");
        }

        //设置头像圆角
        Glide.with(this).load(headPic)
                .transform(new CircleCrop())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(ivUserHeadpic);
    }

    @Override
    protected HomeFragPresenter setPresenter() {
        return new HomeFragPresenter();
    }

    @Override
    protected void initView() {
        bind = ButterKnife.bind(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_user_information;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
