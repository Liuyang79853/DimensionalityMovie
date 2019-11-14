package com.bw.movie.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.applications.App;
import com.bw.movie.model.bean.cinema.MovieScheduleBean;
import com.bw.movie.model.bean.cinema.PayBean;
import com.bw.movie.model.bean.cinema.SeatInfoBean;
import com.bw.movie.model.bean.moviebykey.MovieTicketsBean;
import com.bw.movie.model.bean.user.UserBean;
import com.bw.movie.model.encryption.MD5Utils;
import com.bw.movie.presenter.cinema.MovieSchedulePresenter;
import com.bw.movie.view.activity.base.BaseActivity;
import com.bw.movie.view.adapter.cinema.MovieSeatAdapter;
import com.bw.movie.view.adapter.cinema.RecyclerMovieScheduleAdapter;
import com.bw.movie.view.contract.IContractView;
import com.bw.movie.view.customview.SyLinearLayoutManager;
import com.bw.movie.wxapi.WXEntryActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/*
 * 购票
 * */
public class BuyTicketActivity extends BaseActivity<MovieSchedulePresenter> implements IContractView.IMovieScheduleView {

    @BindView(R.id.tv_movie_name)
    TextView tvMovieName;

    @BindView(R.id.recycler_movie_schedule)
    RecyclerView recyclerMovieSchedule;
    @BindView(R.id.btn_buy_cinema_ticket)
    Button btnBuyCinemaTicket;
    @BindView(R.id.jcv_movie_video)
    JCVideoPlayer jcvMovieVideo;
    @BindView(R.id.room_movieSeat)
    RecyclerView roomMovieSeat;
    @BindView(R.id.btn_purchaseOrder)
    Button btnPurchaseOrder;
    @BindView(R.id.rb_wx_buy_ticket)
    RadioButton rbWxBuyTicket;
    @BindView(R.id.rb_zfb_buy_ticket)
    RadioButton rbZfbBuyTicket;
    @BindView(R.id.lin_cinema_date)
    LinearLayout linCinemaDate;
    @BindView(R.id.lin_pay_method)
    LinearLayout linPayMethod;
    private Unbinder bind;
    private List<MovieScheduleBean.ResultBean> result;
    private String videoUrl;
    private String imageUrl;
    private String movieName;
    private RecyclerMovieScheduleAdapter recyclerMovieScheduleAdapter;
    private List<SeatInfoBean.ResultBean> seatInfoBeanResult;
    private String string;
    private long sum;
    private double fare = 0.1;
    private double zf;
    private CheckBox wxzf;
    private String orderId;
    private int id;
    private int userId;
    private String sessionId;
    private int payType = 1;

    @Override
    protected void initData() {
        Intent intent = getIntent();
        int movieId = intent.getIntExtra("movieId", 0);
        int cinemaId = intent.getIntExtra("cinemaId", 0);
        videoUrl = intent.getStringExtra("videoUrl");
        imageUrl = intent.getStringExtra("imageUrl");
        movieName = intent.getStringExtra("movieName");
        jcvMovieVideo.setUp(videoUrl, "");
        Glide.with(this).load(imageUrl).into(jcvMovieVideo.ivThumb);
        tvMovieName.setText(movieName);
        presenter.movieSchedule(movieId, cinemaId);
    }

    @Override
    protected MovieSchedulePresenter setPresenter() {
        return new MovieSchedulePresenter();
    }

    @Override
    protected void initView() {
        bind = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_buy_ticket;
    }

    //排期
    @Override
    public void movieScheduleSuccess(MovieScheduleBean movieScheduleBean) {
        result = movieScheduleBean.getResult();
        if (result.size() > 0) {
            Log.e("MyMessageScreeningHall", result.get(0).getScreeningHall());
            SyLinearLayoutManager syLinearLayoutManager = new SyLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerMovieScheduleAdapter = new RecyclerMovieScheduleAdapter(this);
            recyclerMovieScheduleAdapter.addData(result);
            recyclerMovieSchedule.setLayoutManager(syLinearLayoutManager);
            recyclerMovieSchedule.setAdapter(recyclerMovieScheduleAdapter);
            recyclerMovieScheduleAdapter.onItemClickListener(new RecyclerMovieScheduleAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    id = result.get(position).getId();
                    Log.e("MyMessageId",id+"");
                    presenter.seatInfo(result.get(position).getHallId());
                }
            });
        } else {
            Toast.makeText(this, "该影院不提供影厅选择", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    //下单
    @Override
    public void buyMovieTicketsSuccess(MovieTicketsBean movieTicketsBean) {
        if (movieTicketsBean.getStatus().equals("0000")){
            Toast.makeText(this,movieTicketsBean.getMessage(), Toast.LENGTH_SHORT).show();
            String orderId = movieTicketsBean.getOrderId();
            Log.e("orderId",orderId);
            presenter.pay(userId,sessionId,1,orderId);
        }else {
            Toast.makeText(this,movieTicketsBean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void Message(UserBean userBean){
        userId = userBean.getUserId();
        sessionId = userBean.getSessionId();
    }

    //选座
    @Override
    public void seatInfoSuccess(SeatInfoBean seatInfoBean) {
        seatInfoBeanResult = seatInfoBean.getResult();
        LinearLayoutManager manager = new GridLayoutManager(this, 8);
        roomMovieSeat.setLayoutManager(manager);
        MovieSeatAdapter seatAdapter = new MovieSeatAdapter(this);
        seatAdapter.addData(seatInfoBeanResult);
        roomMovieSeat.setAdapter(seatAdapter);
        seatAdapter.setCallBack(new MovieSeatAdapter.CallBack() {
            @Override
            public void getBack(String s) {
                Toast.makeText(BuyTicketActivity.this, s, Toast.LENGTH_SHORT).show();
                string = s;
                Log.e("MyMessageSsssss",string);
                for (int i = 0; i < seatInfoBeanResult.size(); i++) {
                    if (seatInfoBeanResult.get(i).getStatus() == 3) {
                        sum++;
                    }
                }
                if (sum > 4) {
                    Toast.makeText(BuyTicketActivity.this, "最多" + sum + "张", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //设置价格
                    if (sum != 0) {
                        zf = sum * fare;
                        btnPurchaseOrder.setText("￥:" + sum * fare);
                        btnPurchaseOrder.setVisibility(View.VISIBLE);
                        btnBuyCinemaTicket.setVisibility(View.GONE);
                        sum = 0;
                    } else if (sum == 0) {
                        btnBuyCinemaTicket.setVisibility(View.VISIBLE);
                        btnPurchaseOrder.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void getStrng(ArrayList<String> list) {
                String str = null;
                for (int i = 0; i < list.size(); i++) {
                    String s = list.get(i);
                    str = s + ",";
                }
                Log.i("qqq", "getStrng: " + str);
                String seat = str.substring(0, str.lastIndexOf(","));
                SharedPreferences qq = getSharedPreferences("zw", Context.MODE_PRIVATE);
                qq.edit().putString("seat", seat).commit();
            }
        });
    }

    //支付
    @Override
    public void paySuccess(PayBean payBean) {
        if (payBean.getStatus().equals("0000")){
            IWXAPI wxapi = WXAPIFactory.createWXAPI(BuyTicketActivity.this, null);
            wxapi.registerApp("wxb3852e6a6b7d9516");
            PayReq payReq = new PayReq();
            payReq.appId = "wxb3852e6a6b7d9516";
            payReq.partnerId = payBean.getPartnerId();
            payReq.prepayId = payBean.getPrepayId();
            payReq.nonceStr = payBean.getNonceStr();
            payReq.timeStamp = payBean.getTimeStamp()+"";
            payReq.packageValue = payBean.getPackageValue();
            payReq.sign = payBean.getSign();
            payReq.extData = "app data"; // optional
            wxapi.sendReq(payReq);
            BuyTicketActivity.this.finish();
        }else {
            Toast.makeText(this,payBean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void movieScheduleError(String msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @OnClick({R.id.rb_wx_buy_ticket, R.id.rb_zfb_buy_ticket, R.id.btn_buy_cinema_ticket, R.id.btn_purchaseOrder})
    public void onClick(View view) {
        switch (view.getId()) {
            //微信
            case R.id.rb_wx_buy_ticket:
                rbZfbBuyTicket.setChecked(false);
                String s = userId+""+ id + "movie";
                Log.e("MessageUserId",userId+"");
                Log.e("MessageUserId",id+"");
                Log.e("MyMessageSss",s);
                String sign = MD5Utils.MD5(s);
                Log.e("MyMessageSsign",sign);
                presenter.buyMovieTickets(userId,sessionId,id,string,sign);
                break;
            //支付宝
            case R.id.rb_zfb_buy_ticket:
                rbWxBuyTicket.setChecked(false);
                break;
            case R.id.btn_buy_cinema_ticket:
                break;
            //立即支付
            case R.id.btn_purchaseOrder:
                linCinemaDate.setVisibility(View.GONE);
                linPayMethod.setVisibility(View.VISIBLE);
                break;
                default:break;
        }
    }
}
