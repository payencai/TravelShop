package com.tec.travelagency.home.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseFragment;
import com.tec.travelagency.home.adapter.MyFragmentAdapter;
import com.tec.travelagency.home.entity.PathSelfOrderBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：凌涛 on 2018/9/6 15:40
 * 邮箱：771548229@qq..com
 */
@SuppressLint("ValidFragment")
public class PathDetailContainerFragment extends BaseFragment {

    @BindView(R.id.lineTablayout)
    TabLayout lineTablayout;
    @BindView(R.id.lineViewpager)
    ViewPager lineViewpager;

    PathSelfOrderBean mPathSelfOrderBean;


    private static final String[] titles = new String[]{"行程明细", "费用信息", "预定须知"};

    private List<Fragment> mFragments;

    public PathDetailContainerFragment(PathSelfOrderBean pathSelfOrderBean) {
        mPathSelfOrderBean = pathSelfOrderBean;
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

        mFragments.add(new PathDetailFragment(mPathSelfOrderBean));
        mFragments.add(new CostInfoFragment());//费用信息
        mFragments.add(new ReserveInfoFragment());//预定须知
        MyFragmentAdapter adapter = new MyFragmentAdapter(((FragmentActivity) getContext()).getSupportFragmentManager(), mFragments, titles);
        lineViewpager.setAdapter(adapter);
        lineViewpager.setOffscreenPageLimit(titles.length);
        lineTablayout.setupWithViewPager(lineViewpager);
    }

}
