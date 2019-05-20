package com.tec.travelagency.orderManager.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseFragment;
import com.tec.travelagency.common.activity.MineActivity;
import com.tec.travelagency.orderManager.activity.RefundActivity;
import com.tec.travelagency.orderManager.adapter.OrderManagerVpAdapter;
import com.tec.travelagency.orderManager.adapter.OrderVpAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：凌涛 on 2018/8/6 11:03
 * 邮箱：771548229@qq..com
 */
public class OrderManagerFragment extends BaseFragment {



    @BindView(R.id.user_order_bar)
    TextView userOrderBar;

    @BindView(R.id.lxs_order_bar)
    TextView lxsOrderBar;


    @BindView(R.id.mine)
    ImageView mine;
    private List<Fragment> mFragments;

    FragmentActivity mActivity;

    private FragmentManager fm;


//    private static final String[] titles = new String[]{"用户订单", "旅行社订单", "用户退款申请", "旅行社退款申请"};

    @Override
    protected int getContentId() {
        return R.layout.fragment_order_manager;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            mActivity = (FragmentActivity) context;
        }
    }

    @Override
    protected void initView() {
        mine.setVisibility(View.VISIBLE);


        initFragment();
    }

    private void initFragment() {
        fm = mActivity.getSupportFragmentManager();
        mFragments = new ArrayList<>();

//        mFragments.add(new UserOrderFragment());
//        mFragments.add(new LxsOrderFragment());

        //互换位置
        mFragments.add(new LxsOrderFragment());
        mFragments.add(new UserOrderFragment());


        for (Fragment fragment : mFragments) {
            if (!fragment.isAdded()) {
                fm.beginTransaction().add(R.id.order_container, fragment).commit();
            }
        }

        userOrderBar.setTextSize(24);
        lxsOrderBar.setTextSize(16);

        hideAllFragment();
        showFragment(0);



    }

    private void hideAllFragment() {
        for (Fragment fragment : mFragments) {
            fm.beginTransaction().hide(fragment).commit();
        }
    }

    private void showFragment(int position) {
        fm.beginTransaction().show(mFragments.get(position)).commit();
    }


    @OnClick({R.id.mine, R.id.user_order_bar, R.id.lxs_order_bar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine:
                startActivity(new Intent(getContext(), MineActivity.class));
                break;
//            case R.id.left_text:
//                startActivity(new Intent(getContext(), RefundActivity.class));
//                break;
            case R.id.user_order_bar:
                userOrderBar.setTextSize(24);
                lxsOrderBar.setTextSize(16);
                hideAllFragment();
                showFragment(0);
                break;
            case R.id.lxs_order_bar:
                lxsOrderBar.setTextSize(24);
                userOrderBar.setTextSize(16);
                hideAllFragment();
                showFragment(1);
                break;
        }
    }


}
