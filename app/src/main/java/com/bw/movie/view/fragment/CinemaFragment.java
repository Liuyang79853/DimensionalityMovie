package com.bw.movie.view.fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.bw.movie.R;
import com.bw.movie.view.adapter.cinema.CinemaFragmentAdapter;
import com.bw.movie.view.fragment.cinema.NearTheCinemaFragment;
import com.bw.movie.view.fragment.cinema.RecommendedCinemaFragment;
import com.bw.movie.view.fragment.cinema.RegionCinemaFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;



public class CinemaFragment extends Fragment {
    @BindView(R.id.frag_movie_ib_location)
    ImageButton fragMovieIbLocation;
    @BindView(R.id.frag_movie_ib_search)
    ImageButton fragMovieIbSearch;
    @BindView(R.id.tab_layout_cinema)
    TabLayout tabLayoutCinema;
    @BindView(R.id.view_pager_cinema)
    ViewPager viewPagerCinema;
    Unbinder unbinder;
    private List<android.support.v4.app.Fragment> fragments;
    private CinemaFragmentAdapter cinemaFragmentAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_cinema, null);
        unbinder = ButterKnife.bind(this, inflate);

        fragments = new ArrayList<>();
        //推荐影院
        fragments.add(new RecommendedCinemaFragment());
        //推荐影院
        fragments.add(new NearTheCinemaFragment());
        //推荐影院
        fragments.add(new RegionCinemaFragment());

        cinemaFragmentAdapter = new CinemaFragmentAdapter(getChildFragmentManager(),fragments);
        viewPagerCinema.setAdapter(cinemaFragmentAdapter);

        for (int i = 0; i < fragments.size(); i++) {
            tabLayoutCinema.addTab(tabLayoutCinema.newTab());
        }
        tabLayoutCinema.setupWithViewPager(viewPagerCinema);
        tabLayoutCinema.getTabAt(0).setText("推荐影院");
        tabLayoutCinema.getTabAt(1).setText("附近影院");
        tabLayoutCinema.getTabAt(2).setText("北京");
        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.frag_movie_ib_location, R.id.frag_movie_ib_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frag_movie_ib_location:
                break;
            case R.id.frag_movie_ib_search:
                break;
        }
    }
}
