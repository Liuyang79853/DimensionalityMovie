package com.bw.movie.view.adapter.paging;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * date:2019/9/10
 * author:孙杰健(fdg)
 * function:
 */
public class MovieMorePageAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public MovieMorePageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
