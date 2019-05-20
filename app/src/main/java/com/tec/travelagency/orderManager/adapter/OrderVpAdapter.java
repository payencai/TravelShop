package com.tec.travelagency.orderManager.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 作者：凌涛 on 2018/8/7 10:29
 * 邮箱：771548229@qq..com
 */
public class OrderVpAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;

    public OrderVpAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    //返回每一项的数据
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    //返回ViewPager中Fragment的总数
    @Override
    public int getCount() {
        return list.size();
    }
}
