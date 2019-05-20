package com.tec.travelagency.orderManager.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.orderManager.adapter.OrderManagerVpAdapter;
import com.tec.travelagency.orderManager.fragment.UserOrderCategoryFragment;
import com.tec.travelagency.orderManager.fragment.LxsHotelOrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RefundActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.order_viewpager)
    ViewPager orderViewpager;
    @BindView(R.id.lineTablayout)
    TabLayout lineTablelayout;
    @BindView(R.id.left_text)
    TextView left_text;

    @BindView(R.id.mine)
    ImageView mine;

    private List<Fragment> mFragments;
    private OrderManagerVpAdapter mVpAdapter;

    private static final String[] titles = new String[]{"用户退款订单", "旅行社退款订单"};

    @Override
    protected int getContentId() {
        return R.layout.activity_refund;
    }

    @Override
    protected void initView() {
        left_text.setVisibility(View.GONE);
        title.setText("退款订单");
        initFragment();
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(new UserOrderCategoryFragment());
        mFragments.add(new LxsHotelOrderFragment());

        mVpAdapter = new OrderManagerVpAdapter(getSupportFragmentManager(), mFragments,titles);
        orderViewpager.setAdapter(mVpAdapter);
        orderViewpager.setOffscreenPageLimit(mFragments.size());
        lineTablelayout.setupWithViewPager(orderViewpager);

    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
