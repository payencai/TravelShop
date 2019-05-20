package com.tec.travelagency.home.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.eventBusBean.UpDoorTicketDayPrice;
import com.tec.travelagency.home.entity.DayInfo;
import com.tec.travelagency.home.entity.DoorTicket;
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

public class SelectUpdateDataActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.dataViewpager)
    ViewPager dataViewpager;

    private List<Fragment> mFragments;
    private OrderVpAdapter mVpAdapter;


    private TravelRouteBean mTravelRouteBean;
    private DoorTicket mDoorTicket;

//    private RoomInfo mRoomInfo;

    @Override
    protected int getContentId() {
        return R.layout.activity_select_update_data;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        mDoorTicket = ((DoorTicket) intent.getSerializableExtra("DoorTicket"));
        if (mDoorTicket == null) {
            finish();
            return;
        }
        EventBus.getDefault().register(this);
        mTravelRouteBean = new TravelRouteBean();
        mTravelRouteBean.setId(mDoorTicket.getId());
        title.setText("修改不同日期价格");

        initFragment();
        reqeustData();
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(new CurDataFragment(mTravelRouteBean, 2));
        mFragments.add(new LastDataFragment(mTravelRouteBean, 2));

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

    @Subscribe
    public void upDoorTicketDayPrice(UpDoorTicketDayPrice ticketDayPrice) {
        reqeustData();
    }

    private void reqeustData() {
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", mDoorTicket.getId());
        HttpProxy.obtain().get(PlatformContans.Ticket.getPricePerDay, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("ltgetPricePerDay", result);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
