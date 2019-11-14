package com.bw.movie.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bw.movie.R;
import com.bw.movie.model.bean.user.UserBean;
import com.bw.movie.view.activity.SplashActivity;
import com.bw.movie.view.activity.SystemMessageActivity;
import com.bw.movie.view.activity.mine.AttentionActivity;
import com.bw.movie.view.activity.mine.SetUpTheActivity;
import com.bw.movie.view.activity.mine.UserInformationActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MyFragment extends Fragment {
    @BindView(R.id.iv_xinxi)
    ImageView ivXinxi;
    @BindView(R.id.iv_head_portrait)
    ImageView ivHeadPortrait;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.user_detailed_information)
    RelativeLayout userDetailedInformation;
    @BindView(R.id.user_movie_tickets)
    LinearLayout userMovieTickets;
    @BindView(R.id.iv_my_attention)
    ImageView ivMyAttention;
    @BindView(R.id.iv_my_reservation)
    ImageView ivMyReservation;
    @BindView(R.id.iv_my_ticket_record)
    ImageView ivMyTicketRecord;
    @BindView(R.id.iv_my_movie_seen)
    ImageView ivMyMovieSeen;
    @BindView(R.id.iv_my_comments)
    ImageView ivMyComments;
    @BindView(R.id.iv_feedback)
    ImageView ivFeedback;
    @BindView(R.id.iv_my_set_up)
    ImageView ivMySetUp;
    Unbinder unbinder;
    private String headPic;
    private String nickName;
    private int sex;
    private String email;
    private String sessionId;
    private int userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my, null);
        unbinder = ButterKnife.bind(this, inflate);
        EventBus.getDefault().register(this);
        Intent intent = getActivity().getIntent();
        nickName = intent.getStringExtra("nickName");
        headPic = intent.getStringExtra("headPic");
        email = intent.getStringExtra("email");
        sex = intent.getIntExtra("sex", 0);
        tvUserName.setText(nickName +"");
        Glide.with(getActivity()).load(headPic)
                .transform(new CircleCrop())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(ivHeadPortrait);
        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC,sticky = true)
    public void Message(UserBean userBean){
        sessionId = userBean.getSessionId();
        userId = userBean.getUserId();
    }

    @OnClick({R.id.iv_xinxi, R.id.user_detailed_information, R.id.user_movie_tickets, R.id.iv_my_attention, R.id.iv_my_reservation, R.id.iv_my_ticket_record, R.id.iv_my_movie_seen, R.id.iv_my_comments, R.id.iv_feedback, R.id.iv_my_set_up})
    public void onClick(View view) {
        switch (view.getId()) {
            //系统消息
            case R.id.iv_xinxi:
                startActivity(new Intent(getActivity(), SystemMessageActivity.class));
                break;
                //用户的更多信息
            case R.id.user_detailed_information:
                Intent intent = new Intent(getActivity(), UserInformationActivity.class);
                intent.putExtra("nickName",nickName);
                intent.putExtra("headPic",headPic);
                intent.putExtra("sex",sex);
                intent.putExtra("email",email);
                startActivity(intent);
                break;
                //用户所持有的电影票
            case R.id.user_movie_tickets:
                break;
                //我的关注
            case R.id.iv_my_attention:
                Intent intent_attention = new Intent(getActivity(), AttentionActivity.class);
                intent_attention.putExtra("userId",userId);
                intent_attention.putExtra("sessionId",sessionId);
                startActivity(intent_attention);
                break;
                //我的预约
            case R.id.iv_my_reservation:
                break;
                //购票纪录
            case R.id.iv_my_ticket_record:
                break;
                //看过的电影
            case R.id.iv_my_movie_seen:
                break;
                //我的评论
            case R.id.iv_my_comments:
                break;
                //意见反馈
            case R.id.iv_feedback:
                break;
                //设置
            case R.id.iv_my_set_up:
                startActivity(new Intent(getActivity(), SetUpTheActivity.class));
                break;
        }
    }
}
