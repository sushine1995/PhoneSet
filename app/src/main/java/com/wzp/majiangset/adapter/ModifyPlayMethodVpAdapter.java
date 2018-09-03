package com.wzp.majiangset.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by wzp on 2017/6/24.
 */

public class ModifyPlayMethodVpAdapter extends FragmentPagerAdapter {

    private String[] titles = new String[]{"基本方式", "选牌方式", "色子参数"};
    private List<Fragment> fragmentList;


    public ModifyPlayMethodVpAdapter(FragmentManager fm, List<Fragment> fragmentList) {
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
