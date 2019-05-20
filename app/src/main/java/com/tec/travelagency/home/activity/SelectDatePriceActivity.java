package com.tec.travelagency.home.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.eventBusBean.UpPathDayPrice;
import com.tec.travelagency.home.entity.DayInfo;
import com.tec.travelagency.home.entity.TravelRouteBean;
import com.tec.travelagency.home.fragment.CurDataFragment;
import com.tec.travelagency.home.fragment.CurMonDataSelectFragment;
import com.tec.travelagency.home.fragment.LastDataFragment;
import com.tec.travelagency.home.fragment.LastMonDataSelectFragment;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.orderManager.adapter.OrderVpAdapter;
import com.tec.travelagency.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectDatePriceActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.dataViewpager)
    ViewPager dataViewpager;

    private List<Fragment> mFragments;
    private OrderVpAdapter mVpAdapter;
    private List<DayInfo> mDataSelectOtherBeans;
    //    private RoomInfo mRoomInfo;

    @Override
    protected int getContentId() {
        return R.layout.activity_select_update_data;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        mDataSelectOtherBeans = ((List<DayInfo>) intent.getSerializableExtra("mDataSelectOtherBeans"));
        if (mDataSelectOtherBeans == null) {
            finish();
            return;
        }
        title.setText("请选择时间");
        initFragment();
    }


    private void initFragment() {
        mFragments = new ArrayList<>();

        mFragments.add(new CurMonDataSelectFragment(mDataSelectOtherBeans));
        mFragments.add(new LastMonDataSelectFragment(mDataSelectOtherBeans));

        mVpAdapter = new OrderVpAdapter(getSupportFragmentManager(), mFragments);
        dataViewpager.setAdapter(mVpAdapter);
    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
