package com.bw.movie.view.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.model.bean.details.AddMovieCommentBean;
import com.bw.movie.model.bean.user.UserBean;
import com.bw.movie.presenter.details.AddMovieCommentPresenter;
import com.bw.movie.view.activity.base.BaseActivity;
import com.bw.movie.view.contract.IContractView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/*
 * 写影评
 * */
public class AddMovieCommentActivity extends BaseActivity<AddMovieCommentPresenter> implements IContractView.IAddMovieCommentView {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_add_moviecomment_score)
    EditText etAddMoviecommentScore;
    @BindView(R.id.et_add_moviecomment)
    EditText etAddMoviecomment;
    @BindView(R.id.btn_submit_comment)
    Button btnSubmitComment;
    private Unbinder bind;
    private int movieId;
    private String name;
    private int userId;
    private String sessionId;

    @Override
    protected void initData() {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        movieId = intent.getIntExtra("movieId", 0);
        ///设置电影名称
        tvName.setText(name);
    }

    @Override
    protected AddMovieCommentPresenter setPresenter() {
        return new AddMovieCommentPresenter();
    }

    @Override
    protected void initView() {
        bind = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_add_moviecomment;
    }

    @Override
    public void addMovieCommentSuccess(AddMovieCommentBean addMovieCommentBean) {
        if (addMovieCommentBean.getStatus().equals("0000")){
            Toast.makeText(this,addMovieCommentBean.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Toast.makeText(this,addMovieCommentBean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void Message(UserBean userBean){
        userId = userBean.getUserId();
        sessionId = userBean.getSessionId();
    }

    @OnClick(R.id.btn_submit_comment)
    public void onClick() {
        String addMoviecomment = etAddMoviecomment.getText().toString().trim();
        String addMoviecommentScore = etAddMoviecommentScore.getText().toString().trim();

        if (TextUtils.isEmpty(addMoviecomment) || TextUtils.isEmpty(addMoviecommentScore)){
            Toast.makeText(this, "输入内容不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        presenter.addMovieComment(userId,sessionId,movieId,addMoviecomment, Double.parseDouble(addMoviecommentScore));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        EventBus.getDefault().unregister(this);
    }
}
