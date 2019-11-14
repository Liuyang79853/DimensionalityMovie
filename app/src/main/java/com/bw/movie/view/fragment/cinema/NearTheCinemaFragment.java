package com.bw.movie.view.fragment.cinema;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.model.bean.cinema.NearCinemaBean;
import com.bw.movie.model.bean.cinema.RecommendedCinemaBean;
import com.bw.movie.presenter.cinema.CinemaPresenter;
import com.bw.movie.view.activity.CinemaDetailsActivity;
import com.bw.movie.view.adapter.cinema.RecyclerNearCinemaAdapter;
import com.bw.movie.view.contract.IContractView;
import com.bw.movie.view.customview.SyLinearLayoutManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**

 * function:附近影院
 */
public class NearTheCinemaFragment extends Fragment implements IContractView.ICinemaView {
    @BindView(R.id.recycler_NearCinema)
    RecyclerView recyclerNearCinema;
    Unbinder unbinder;
    private int page = 1;
    private int count = 10;
    private List<NearCinemaBean.ResultBean> nearCinemaResult;
    private RecyclerNearCinemaAdapter recyclerNearCinemaAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_near_cinema, null);
        unbinder = ButterKnife.bind(this, inflate);
        CinemaPresenter cinemaPresenter = new CinemaPresenter();
        cinemaPresenter.attachView(this);
        cinemaPresenter.nearCinema(page,count);
        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void recommendedCinemaSuccess(RecommendedCinemaBean recommendedCinemaBean) {

    }

    //附近影院
    @Override
    public void nearCinemaSuccess(NearCinemaBean nearCinemaBean) {
        nearCinemaResult = nearCinemaBean.getResult();
        recyclerNearCinemaAdapter = new RecyclerNearCinemaAdapter(getContext());
        recyclerNearCinemaAdapter.addData(nearCinemaResult);
        recyclerNearCinema.setLayoutManager(new SyLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerNearCinema.setAdapter(recyclerNearCinemaAdapter);

        recyclerNearCinemaAdapter.onItemClickListener(new RecyclerNearCinemaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, int cinemaId) {
                Intent intent = new Intent(getActivity(), CinemaDetailsActivity.class);
                intent.putExtra("cinemaname",nearCinemaResult.get(position).getName());
                intent.putExtra("cinemaId",cinemaId);
                startActivity(intent);
            }
        });
    }
}
