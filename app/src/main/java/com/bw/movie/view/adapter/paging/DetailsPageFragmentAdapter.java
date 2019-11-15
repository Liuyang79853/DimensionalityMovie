package com.bw.movie.view.adapter.paging;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


public class DetailsPageFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> detailsPageFragments;

    public DetailsPageFragmentAdapter(FragmentManager fm, List<Fragment> detailsPageFragments) {
        super(fm);
        this.detailsPageFragments = detailsPageFragments;
    }

    @Override
    public Fragment getItem(int i) {
        return detailsPageFragments.get(i);
    }

    @Override
    public int getCount() {
        return detailsPageFragments.size();
    }
}
