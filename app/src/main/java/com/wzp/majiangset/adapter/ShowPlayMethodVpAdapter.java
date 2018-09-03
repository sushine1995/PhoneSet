package com.wzp.majiangset.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by wzp on 2017/6/24.
 */

public class ShowPlayMethodVpAdapter extends FragmentPagerAdapter {

    private String[] titles = new String[]{"玩法一", "玩法二", "玩法三"};
    private List<Fragment> fragmentList;


    public ShowPlayMethodVpAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
