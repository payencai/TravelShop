package com.tec.travelagency.home.fragment;

import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseFragment;
import com.tec.travelagency.home.adapter.MyFragmentAdapter;
import com.tec.travelagency.home.entity.ScenicSelfOrderBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：凌涛 on 2018/9/6 15:40
 * 邮箱：771548229@qq..com
 */
@SuppressLint("ValidFragment")
public class ScenicDetailContainerFragment extends BaseFragment {

    @BindView(R.id.lineTablayout)
    TabLayout lineTablayout;
    @BindView(R.id.lineViewpager)
    ViewPager lineViewpager;

    private ScenicSelfOrderBean  mScenicSpotId;

    private static final String[] titles = new String[]{"成人票", "儿童票"};

    private List<Fragment> mFragments;

    public ScenicDetailContainerFragment(ScenicSelfOrderBean mScenicSelfOrderBean) {
        mScenicSpotId = mScenicSelfOrderBean;
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_mcai_headline;
    }

    @Override
    protected void initView() {

        initFragment();
    }

    private void initFragment() {
        mFragments = new ArrayList<>();

        mFragments.add(new AdultTickerFragment(1, mScenicSpotId));
        mFragments.add(new AdultTickerFragment(2, mScenicSpotId));
        MyFragmentAdapter adapter = new MyFragmentAdapter(((FragmentActivity) getContext()).getSupportFragmentManager(), mFragments, titles);
        lineViewpager.setAdapter(adapter);
        lineViewpager.setOffscreenPageLimit(titles.length);
        lineTablayout.setupWithViewPager(lineViewpager);
    }

}
