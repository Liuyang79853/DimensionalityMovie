package com.bw.movie.view.fragment.more;

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
import com.bw.movie.view.adapter.RecyclerViewUpComingAdapter;
import com.bw.movie.view.contract.IContractView;
import com.bw.movie.view.customview.SyLinearLayoutManager;
import com.bw.movie.view.interfaces.IBaseView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * date:2019/9/10
 * author:孙杰健(fdg)
 * function:
 */
public class MovieMoreUpCommingFragment extends Fragment implements IContractView.IMovieView, IBaseView {
    @BindView(R.id.recycler_upComing_more)
    RecyclerView recyclerUpComingMore;
    Unbinder unbinder;
    private MovieHomePresenter movieFragPresenter;
    private int page = 1;
    private int count = 14;
    private String sessionId;
    private int userId;
    private List<UpComingBean.ResultBean> upComingResult;
    private RecyclerViewUpComingAdapter recyclerViewUpComingAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_upcomming_moviemore, null);
        unbinder = ButterKnife.bind(this, inflate);
        EventBus.getDefault().register(this);
        movieFragPresenter = new MovieHomePresenter();
        movieFragPresenter.attachView(this);
        movieFragPresenter.upComingMovie(page,count);
        return inflate;
    }

    @Override
    public void movieBannerSuccess(MovieFragBannerBean movieFragBannerBean) {

    }

    //正在热映数据回调成功接口
    @Override
    public void IsHitSuccess(IsHitMovieBean isHitMovieBean) {

    }

    //即将上映数据回调成功接口
    @Override
    public void upComingSuccess(UpComingBean upComingBean) {
        Log.e("MyMessage111", upComingBean.getResult().get(0).getName());
        upComingResult = upComingBean.getResult();
        recyclerViewUpComingAdapter = new RecyclerViewUpComingAdapter(getActivity());
        recyclerViewUpComingAdapter.addData(upComingResult);
        recyclerUpComingMore.setLayoutManager(new SyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerUpComingMore.setAdapter(recyclerViewUpComingAdapter);


        recyclerViewUpComingAdapter.onItemClickListener(new RecyclerViewUpComingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
                intent.putExtra("movieId",upComingResult.get(position).getMovieId());
                startActivity(intent);
            }

            @Override
            public void appointmentClick(int movieId) {
                movieFragPresenter.reserve(userId,sessionId,movieId);
            }
        });
    }

    //热门电影数据回调成功接口
    @Override
    public void hotSuccess(HotMovieBean hotMovieBean) {

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

    @Subscribe(threadMode = ThreadMode.ASYNC,sticky = true)
    public void Message(UserBean userBean){
        userId = userBean.getUserId();
        sessionId = userBean.getSessionId();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
