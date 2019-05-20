package com.tec.travelagency.orderManager.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseFragment;
import com.tec.travelagency.orderManager.adapter.OrderManagerVpAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：凌涛 on 2018/9/25 11:30
 * 邮箱：771548229@qq..com
 */
public class LxsOrderFragment extends BaseFragment {

    @BindView(R.id.lxs_viewpager)
    ViewPager lxsViewpager;
    @BindView(R.id.lxsTablayout)
    TabLayout lxsTablayout;

    private OrderManagerVpAdapter mVpAdapter;

    private static final String[] titles = new String[]{"酒店", "门票", "租车", "路线", "会议室"};

    private List<Fragment> mFragments;

    @Override
    protected int getContentId() {
        return R.layout.fragment_lxs_order;
    }

    @Override
    protected void initView() {

        initFragment();

        mVpAdapter = new OrderManagerVpAdapter(getActivity().getSupportFragmentManager(), mFragments, titles);
        lxsViewpager.setAdapter(mVpAdapter);
        lxsViewpager.setOffscreenPageLimit(mFragments.size());
        lxsTablayout.setupWithViewPager(lxsViewpager);
        lxsViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            switch (i) {
                case 1:
                    mFragments.add(LxsHotelOrderFragment.getInstance(i));
                    break;
                case 2:
                    mFragments.add(LxsTicketsOrderFragment.getInstance(i));
                    break;
                case 3:
                    mFragments.add(LxsCarOrderFragment.getInstance(i));
                    break;
                case 4:
                    mFragments.add(LxsPathOrderFragment.getInstance(i));
                    break;
                case 5:
                    mFragments.add(LxsMeetingOrderFragment.getInstance(i));
                    break;

            }
        }

    }

}
