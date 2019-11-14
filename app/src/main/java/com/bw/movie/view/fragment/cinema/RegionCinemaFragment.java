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
import com.bw.movie.model.bean.cinema.CinemaByRegionBean;
import com.bw.movie.model.bean.cinema.RegionListBean;
import com.bw.movie.presenter.cinema.RegionListPresenter;
import com.bw.movie.view.activity.CinemaDetailsActivity;
import com.bw.movie.view.adapter.cinema.RecyclerRegionAdapter;
import com.bw.movie.view.adapter.cinema.RecyclerRegionCinemaAdapter;
import com.bw.movie.view.contract.IContractView;
import com.bw.movie.view.customview.SyLinearLayoutManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**

 * function:地区内的影院
 */
public class RegionCinemaFragment extends Fragment implements IContractView.IRegionListView {
    @BindView(R.id.recycler_region)
    RecyclerView recyclerRegion;
    @BindView(R.id.recycler_region_cinema)
    RecyclerView recyclerRegionCinema;
    Unbinder unbinder;
    private RegionListPresenter regionListPresenter;
    //区域 集合
    private List<RegionListBean.ResultBean> result;
    //区域 适配器
    private RecyclerRegionAdapter recyclerRegionAdapter;
    //区域 影院适配器
    private RecyclerRegionCinemaAdapter recyclerRegionCinemaAdapter;
    //区域影院集合
    private List<CinemaByRegionBean.ResultBean> cinemaByRegionList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_region_cinema, null);
        unbinder = ButterKnife.bind(this, inflate);
        regionListPresenter = new RegionListPresenter();
        regionListPresenter.attachView(this);
        regionListPresenter.regionList();
        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        regionListPresenter.detachView();
    }

    //区域列表查询
    @Override
    public void regionSuccess(RegionListBean regionListBean) {
        Log.e("RegionList",regionListBean.getResult().get(0).getRegionName());
        result = regionListBean.getResult();
        //自适应布局
        SyLinearLayoutManager syLinearLayoutManager = new SyLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerRegionAdapter = new RecyclerRegionAdapter(getContext());
        recyclerRegionAdapter.addData(result);
        recyclerRegion.setLayoutManager(syLinearLayoutManager);
        recyclerRegion.setAdapter(recyclerRegionAdapter);

        recyclerRegionAdapter.onItemClickListener(new RecyclerRegionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, int regionId) {
                Log.e("regionId",regionId+"");
                regionListPresenter.cinemaByRegion(regionId);
            }
        });
    }

    //根据区域查询影院
    @Override
    public void cinemaByRegionSuccess(CinemaByRegionBean cinemaByRegionBean) {
        cinemaByRegionList = cinemaByRegionBean.getResult();
        //自适应布局
        SyLinearLayoutManager syLinearLayoutManager = new SyLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerRegionCinemaAdapter = new RecyclerRegionCinemaAdapter(getContext());
        recyclerRegionCinemaAdapter.addData(cinemaByRegionList);
        recyclerRegionCinema.setLayoutManager(syLinearLayoutManager);
        recyclerRegionCinema.setAdapter(recyclerRegionCinemaAdapter);

        recyclerRegionCinemaAdapter.onItemClickListener(new RecyclerRegionCinemaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, int cinemaId) {
                Intent intent = new Intent(getContext(), CinemaDetailsActivity.class);
                intent.putExtra("cinemaId",cinemaId);
                startActivity(intent);
            }
        });
    }
}
