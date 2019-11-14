package com.bw.movie.view.fragment.cinema;

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
import com.bw.movie.model.bean.cinema.NearCinemaBean;
import com.bw.movie.model.bean.cinema.RecommendedCinemaBean;
import com.bw.movie.presenter.cinema.CinemaPresenter;
import com.bw.movie.view.activity.CinemaDetailsActivity;
import com.bw.movie.view.adapter.cinema.RecyclerRecommendedCinemaAdapter;
import com.bw.movie.view.contract.IContractView;
import com.bw.movie.view.customview.SyLinearLayoutManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**

 * function:推荐影院
 */
public class RecommendedCinemaFragment extends Fragment implements IContractView.ICinemaView {
    @BindView(R.id.recycler_recommendedCinema)
    RecyclerView recyclerRecommendedCinema;
    Unbinder unbinder;
    private int page = 1;
    private int count = 10;
    private CinemaPresenter cinemaPresenter;
    private RecyclerRecommendedCinemaAdapter recyclerRecommendedCinemaAdapter;
    private List<RecommendedCinemaBean.ResultBean> recommendedCinemaResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_recommended_cinema, null);
        unbinder = ButterKnife.bind(this, inflate);
        cinemaPresenter = new CinemaPresenter();
        cinemaPresenter.attachView(this);
        cinemaPresenter.recommendedCinema(page,count);
        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        cinemaPresenter.detachView();
    }

    //推荐影院
    @Override
    public void recommendedCinemaSuccess(RecommendedCinemaBean recommendedCinemaBean) {
        String address = recommendedCinemaBean.getResult().get(0).getAddress();
        Log.e("MessageAddress",address);
        recommendedCinemaResult = recommendedCinemaBean.getResult();
        recyclerRecommendedCinemaAdapter = new RecyclerRecommendedCinemaAdapter(getContext());
        recyclerRecommendedCinemaAdapter.addData(recommendedCinemaResult);
        recyclerRecommendedCinema.setLayoutManager(new SyLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerRecommendedCinema.setAdapter(recyclerRecommendedCinemaAdapter);

        recyclerRecommendedCinemaAdapter.onItemClickListener(new RecyclerRecommendedCinemaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, int cinemaId) {
                Intent intent = new Intent(getActivity(), CinemaDetailsActivity.class);
                intent.putExtra("cinemaId",cinemaId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void nearCinemaSuccess(NearCinemaBean nearCinemaBean) {

    }
}
