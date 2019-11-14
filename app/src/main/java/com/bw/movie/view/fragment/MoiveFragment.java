package com.bw.movie.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.model.bean.homepage.HotMovieBean;
import com.bw.movie.model.bean.homepage.IsHitMovieBean;
import com.bw.movie.model.bean.homepage.MovieFragBannerBean;
import com.bw.movie.model.bean.homepage.ReserveBean;
import com.bw.movie.model.bean.homepage.UpComingBean;
import com.bw.movie.model.bean.user.UserBean;
import com.bw.movie.presenter.home.MovieHomePresenter;
import com.bw.movie.view.activity.MovieDetailsActivity;
import com.bw.movie.view.activity.MovieMoreActivity;
import com.bw.movie.view.activity.SearchActivity;
import com.bw.movie.view.adapter.RecyclerViewHotAdapter;
import com.bw.movie.view.adapter.RecyclerViewIsHitAdapter;
import com.bw.movie.view.adapter.RecyclerViewUpComingAdapter;
import com.bw.movie.view.contract.IContractView;
import com.bw.movie.view.customview.SyLinearLayoutManager;
import com.bw.movie.view.interfaces.IBaseView;
import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MoiveFragment extends Fragment implements IContractView.IMovieView, IBaseView {
    //定位
    @BindView(R.id.frag_movie_ib_location)
    ImageButton fragMovieIbLocation;
    //搜索
    @BindView(R.id.frag_movie_ib_search)
    ImageButton fragMovieIbSearch;
    //banner
    @BindView(R.id.frag_movie_banner)
    XBanner fragMovieBanner;
    Unbinder unbinder;
    //正在热映
    @BindView(R.id.recycler_isHit)
    RecyclerView recyclerIsHit;
    //banner页数
    @BindView(R.id.tv_frag_movie_page)
    TextView tvFragMoviePage;
    //即将上映
    @BindView(R.id.recycler_upComing)
    RecyclerView recyclerUpComing;
    //热门电影图片
    @BindView(R.id.iv_imageUrl)
    ImageView ivImageUrl;
    //购票
    @BindView(R.id.btn_buy_ticket)
    Button btnBuyTicket;
    //热门电影
    @BindView(R.id.recycler_hot)
    RecyclerView recyclerHot;
    @BindView(R.id.tv_movie_more1)
    TextView tvMovieMore1;
    @BindView(R.id.tv_movie_more2)
    TextView tvMovieMore2;
    @BindView(R.id.tv_movie_more3)
    TextView tvMovieMore3;
    private MovieHomePresenter movieFragPresenter;
    private int page = 1;
    private int count = 14;
    //正在热映
    private List<IsHitMovieBean.ResultBean> isHitResult;
    private RecyclerViewIsHitAdapter recyclerViewIsHitAdapter;
    //即将上映
    private List<UpComingBean.ResultBean> upComingResult;
    private RecyclerViewUpComingAdapter recyclerViewUpComingAdapter;
    //热门电影
    private List<HotMovieBean.ResultBean> hotResult;
    private RecyclerViewHotAdapter recyclerViewHotAdapter;
    private int userId;
    private String sessionId;
    private int movieId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_movie, null);
        unbinder = ButterKnife.bind(this, inflate);

        EventBus.getDefault().register(this);

        movieFragPresenter = new MovieHomePresenter();
        movieFragPresenter.attachView(this);
        //首页XBanner
        movieFragPresenter.movieBanner();
        //正在热映展示列表
        movieFragPresenter.IsHitMovie(page, count);
        //即将上映展示列表
        movieFragPresenter.upComingMovie(page, count);
        //热门电影展示列表
        movieFragPresenter.hotMovie(page, count);

        return inflate;
    }

    @OnClick({R.id.frag_movie_ib_location, R.id.frag_movie_ib_search,R.id.tv_movie_more1, R.id.tv_movie_more2, R.id.tv_movie_more3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frag_movie_ib_location:
                break;
            case R.id.frag_movie_ib_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.tv_movie_more1:
                //跳转更多电影
                startActivity(new Intent(getActivity(), MovieMoreActivity.class));
                break;
            case R.id.tv_movie_more2:
                //跳转更多电影
                startActivity(new Intent(getActivity(), MovieMoreActivity.class));
                break;
            case R.id.tv_movie_more3:
                //跳转更多电影
                startActivity(new Intent(getActivity(), MovieMoreActivity.class));
                break;
        }
    }

    //首页展示Banner
    @Override
    public void movieBannerSuccess(MovieFragBannerBean movieFragBannerBean) {
        final List<MovieFragBannerBean.ResultBean> images = movieFragBannerBean.getResult();
        Log.e("hhhhhhhh", images.get(0).getImageUrl());
        fragMovieBanner.setData(images, null);
        fragMovieBanner.setPageChangeDuration(2000);
        fragMovieBanner.setPageTransformer(Transformer.Default);
        fragMovieBanner.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(getActivity()).load(images.get(position).getImageUrl()).into((ImageView) view);
                tvFragMoviePage.setText(images.get(position).getRank() + "/5");
            }
        });
        fragMovieBanner.startAutoPlay();
    }

    //正在热映
    @Override
    public void IsHitSuccess(IsHitMovieBean isHitMovieBean) {
        isHitResult = isHitMovieBean.getResult();
        recyclerViewIsHitAdapter = new RecyclerViewIsHitAdapter(getActivity());
        recyclerViewIsHitAdapter.addData(isHitResult);
        recyclerIsHit.setLayoutManager(new SyLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerIsHit.setAdapter(recyclerViewIsHitAdapter);

        recyclerViewIsHitAdapter.onItemClickListener(new RecyclerViewIsHitAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int movieId = isHitResult.get(position).getMovieId();
                Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
                intent.putExtra("movieId", movieId);
                startActivity(intent);
            }
        });

    }

    //即将上映列表展示
    @Override
    public void upComingSuccess(final UpComingBean upComingBean) {
        upComingResult = upComingBean.getResult();
        recyclerViewUpComingAdapter = new RecyclerViewUpComingAdapter(getActivity());
        recyclerViewUpComingAdapter.addData(upComingResult);
        recyclerUpComing.setLayoutManager(new SyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerUpComing.setAdapter(recyclerViewUpComingAdapter);
        recyclerViewUpComingAdapter.onItemClickListener(new RecyclerViewUpComingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                movieId = upComingBean.getResult().get(position).getMovieId();
                Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
                intent.putExtra("movieId", movieId);
                startActivity(intent);
            }

            @Override
            public void appointmentClick(final int movieId) {
                movieFragPresenter.reserve(userId,sessionId,movieId);
                Toast.makeText(getContext(),movieId+"", Toast.LENGTH_SHORT).show();
                Log.e("MyMessageMovieId",movieId+"");
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void Message(UserBean userBean){
        userId = userBean.getUserId();
        sessionId = userBean.getSessionId();
    }

    //热门电影
    @Override
    public void hotSuccess(final HotMovieBean hotMovieBean) {
        hotResult = hotMovieBean.getResult();
        recyclerViewHotAdapter = new RecyclerViewHotAdapter(getActivity());
        recyclerViewHotAdapter.addData(hotResult);
        recyclerHot.setLayoutManager(new SyLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerHot.setAdapter(recyclerViewHotAdapter);

        Glide.with(getActivity()).load(hotResult.get(1).getHorizontalImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(ivImageUrl);

        recyclerViewHotAdapter.onItemClickListener(new RecyclerViewHotAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int movieId = hotMovieBean.getResult().get(position).getMovieId();
                Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
                intent.putExtra("movieId", movieId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void reserveSuccess(ReserveBean reserveBean) {
        if (reserveBean.getStatus().equals("0000")){
            Toast.makeText(getContext(),reserveBean.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }else {
            Toast.makeText(getContext(),reserveBean.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        movieFragPresenter.detachView();
    }

    @OnClick(R.id.btn_buy_ticket)
    public void onClick() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
