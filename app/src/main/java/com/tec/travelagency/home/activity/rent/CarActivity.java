package com.tec.travelagency.home.activity.rent;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;


import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;


import butterknife.BindView;

public class CarActivity extends BaseActivity {
    private Fragment[] mFragmensts;
    @BindView(R.id.tab_ticket)
    TabLayout mTabLayout;
    @BindView(R.id.fl_content)
    FrameLayout fl_content;
    @BindView(R.id.back)
    ImageView back;
    @Override
    protected int getContentId() {
        return R.layout.activity_car;
    }

    @Override
    protected void initView() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTabLayout.addTab(mTabLayout.newTab().setText("平台租车"));
        mTabLayout.addTab(mTabLayout.newTab().setText("自营租车"));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position= tab.getPosition();
                if(position==0){
                    showFragment(0);
                    hideFragment(1);
                }else{
                    showFragment(1);
                    hideFragment(0);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        initFragment();
    }
    private void initFragment(){
        getFragments("car");
        addFragments();
        showFragment(0);
        hideFragment(1);
    }
    public  void getFragments(String from) {
        mFragmensts=new Fragment[2];
        mFragmensts[0] = CarPlatFragment.newInstance(from);
        mFragmensts[1] = CarSaleFragment.newInstance(from);
        //fragments[2] = DeliveryFragment.newInstance(from);

    }

    private void addFragments() {
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, mFragmensts[0]).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, mFragmensts[1]).commit();
        //getActivity().getSupportFragmentManager().beginTransaction().add(R.id.content, mFragmensts[2]).commit();
    }

    private void showFragment(int index) {
        getSupportFragmentManager().beginTransaction().show(mFragmensts[index]).commit();
    }

    private void hideFragment(int index) {
        getSupportFragmentManager().beginTransaction().hide(mFragmensts[index]).commit();
    }
}


