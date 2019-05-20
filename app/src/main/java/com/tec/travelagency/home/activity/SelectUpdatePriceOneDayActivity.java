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
import com.tec.travelagency.home.fragment.LastDataFragment;
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

public class SelectUpdatePriceOneDayActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.dataViewpager)
    ViewPager dataViewpager;

    private List<Fragment> mFragments;
    private OrderVpAdapter mVpAdapter;
    private TravelRouteBean mTravelRouteBean;
    //    private RoomInfo mRoomInfo;

    @Override
    protected int getContentId() {
        return R.layout.activity_select_update_data;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        mTravelRouteBean = ((TravelRouteBean) intent.getSerializableExtra("TravelRouteBean"));
        if (mTravelRouteBean == null) {
            finish();
            return;
        }
        EventBus.getDefault().register(this);
        title.setText("修改不同日期价格");
        initFragment();
        reqeustData();
    }
    @Subscribe
    public void upPathDayPrice(UpPathDayPrice upPathDayPrice) {
        reqeustData();
    }

    private void initFragment() {
        mFragments = new ArrayList<>();

        mFragments.add(new CurDataFragment(mTravelRouteBean, 1));
        mFragments.add(new LastDataFragment(mTravelRouteBean, 1));

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
        EventBus.getDefault().unregister(this);
    }

    /**
     * 请求每一天的价格
     */
    private void reqeustData() {
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", mTravelRouteBean.getId());
        HttpProxy.obtain().get(PlatformContans.Route.getPricePerDay, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("getPricePerDay", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONArray data = object.getJSONArray("data");
                        List<DayInfo> list = new ArrayList<>();
                        int length = data.length();
                        Gson gson = new Gson();
                        for (int i = 0; i < length; i++) {
                            JSONObject item = data.getJSONObject(i);
                            DayInfo dayInfo = gson.fromJson(item.toString(), DayInfo.class);
                            list.add(dayInfo);
                        }
                        EventBus.getDefault().post(list);
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onLtFailure(String error) {

            }
        });
    }
}
