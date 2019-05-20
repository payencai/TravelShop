package com.tec.travelagency.home.activity.route;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.home.activity.rent.CarPlatFragment;
import com.tec.travelagency.home.activity.rent.CarSaleFragment;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.GlideImageLoader;
import com.tec.travelagency.utils.LogUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class NewPathActivity extends BaseActivity {
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
        mTabLayout.addTab(mTabLayout.newTab().setText("平台路线"));
        mTabLayout.addTab(mTabLayout.newTab().setText("自营路线"));
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
        mFragmensts[0] = PlatPathFragment.newInstance(from);
        mFragmensts[1] = SalePathFragment.newInstance(from);
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
