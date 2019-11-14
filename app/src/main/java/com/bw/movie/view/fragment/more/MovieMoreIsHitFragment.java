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

import com.bw.movie.R;
import com.bw.movie.model.bean.homepage.HotMovieBean;
import com.bw.movie.model.bean.homepage.IsHitMovieBean;
import com.bw.movie.model.bean.homepage.MovieFragBannerBean;
import com.bw.movie.model.bean.homepage.ReserveBean;
import com.bw.movie.model.bean.homepage.UpComingBean;
import com.bw.movie.presenter.home.MovieHomePresenter;
import com.bw.movie.view.activity.MovieDetailsActivity;
import com.bw.movie.view.adapter.moviemore.RecyclerViewIsHitMoreAdapter;
import com.bw.movie.view.contract.IContractView;
import com.bw.movie.view.customview.SyLinearLayoutManager;
import com.bw.movie.view.interfaces.IBaseView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * date:2019/9/10
 * author:孙杰健(fdg)
 * function:
 */
public class MovieMoreIsHitFragment extends Fragment implements IContractView.IMovieView, IBaseView {
    @BindView(R.id.recycler_isHit_more)
    RecyclerView recyclerIsHitMore;
    Unbinder unbinder;
    private MovieHomePresenter movieFragPresenter;
    private int page = 1;
    private int count = 14;
    private List<IsHitMovieBean.ResultBean> isHitMovieResult;
    private RecyclerViewIsHitMoreAdapter recyclerViewIsHitMoreAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_ishit_moviemore, null);
        unbinder = ButterKnife.bind(this, inflate);
        movieFragPresenter = new MovieHomePresenter();
        movieFragPresenter.attachView(this);
        movieFragPresenter.IsHitMovie(page,count);
        return inflate;
    }

    @Override
    public void movieBannerSuccess(MovieFragBannerBean movieFragBannerBean) {

    }

    //正在热映数据回调成功接口
    @Override
    public void IsHitSuccess(IsHitMovieBean isHitMovieBean) {
        Log.e("MyMessage111",isHitMovieBean.getResult().get(0).getName());
        isHitMovieResult = isHitMovieBean.getResult();
        recyclerViewIsHitMoreAdapter = new RecyclerViewIsHitMoreAdapter(getActivity());
        recyclerViewIsHitMoreAdapter.addData(isHitMovieResult);
        recyclerIsHitMore.setLayoutManager(new SyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerIsHitMore.setAdapter(recyclerViewIsHitMoreAdapter);

        recyclerViewIsHitMoreAdapter.onItemClickListener(new RecyclerViewIsHitMoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
                intent.putExtra("movieId",isHitMovieResult.get(position).getMovieId());
                startActivity(intent);
            }

            @Override
            public void appointmentClick(int movieId) {

            }
        });
    }

    //即将上映数据回调成功接口
    @Override
    public void upComingSuccess(UpComingBean upComingBean) {

    }

    //热门电影数据回调成功接口
    @Override
    public void hotSuccess(HotMovieBean hotMovieBean) {

    }

    @Override
    public void reserveSuccess(ReserveBean reserveBean) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
