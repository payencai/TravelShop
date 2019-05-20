package com.tec.travelagency.home.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.home.fragment.AddedHotelFragment;
import com.tec.travelagency.home.fragment.NewHotelFragment;
import com.tec.travelagency.orderManager.adapter.OrderVpAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HotelManagerActivity extends BaseActivity {

    @BindView(R.id.headLayout)
    RelativeLayout headLayout;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.mine)
    ImageView mine;
    @BindView(R.id.hotel_viewpager)
    ViewPager hotelViewpager;

    @BindView(R.id.account_paid_text)
    TextView accountPaidText;
    @BindView(R.id.account_paid_view)
    View accountPaidView;
    @BindView(R.id.obligation_text)
    TextView obligationText;
    @BindView(R.id.obligation_view)
    View obligationView;

    private List<Fragment> mFragments;
    private OrderVpAdapter mVpAdapter;

    @Override
    protected int getContentId() {
        return R.layout.activity_hotel_manager;
    }

    @Override
    protected void initView() {
//        headLayout.setBackgroundColor(getResources().getColor(R.color.app_theme_color));
//        title.setTextColor(Color.WHITE);
//        back.setImageResource(R.mipmap.back_white);
        title.setText("酒店");
        initFragment();
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(new AddedHotelFragment());
        mFragments.add(new NewHotelFragment());

        mVpAdapter = new OrderVpAdapter(getSupportFragmentManager(), mFragments);
        hotelViewpager.setAdapter(mVpAdapter);
        hotelViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("onPageSelected", "onPageSelected: " + position);
                setFragmentUI(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setFragmentUI(0);

    }

    @OnClick({R.id.back, R.id.account_paid_layout, R.id.obligation_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.account_paid_layout:
                setFragmentUI(0);

                break;
            case R.id.obligation_layout:
                setFragmentUI(1);
                break;
        }
    }

    private void setFragmentUI(int type) {
        hotelViewpager.setCurrentItem(type);
        switch (type) {
            case 0:
                accountPaidText.setSelected(true);
                accountPaidView.setVisibility(View.VISIBLE);
                obligationText.setSelected(false);
                obligationView.setVisibility(View.GONE);
                break;
            case 1:
                accountPaidText.setSelected(false);
                accountPaidView.setVisibility(View.GONE);
                obligationText.setSelected(true);
                obligationView.setVisibility(View.VISIBLE);
                break;
        }

    }
}
