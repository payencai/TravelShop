package com.tec.travelagency.home.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.common.rv.abs.AbsBaseActivity;
import com.tec.travelagency.common.rv.base.Cell;
import com.tec.travelagency.eventBusBean.UpdatRentCarUI;
import com.tec.travelagency.home.entity.RentCarBean;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RentCarActivity extends AbsBaseActivity<RentCarBean> {

    private int page = 1;
    private boolean isLoadMore = false;

    @Override

    public void onRecyclerViewInitialized() {
        addDividerItem(0);
        EventBus.getDefault().register(this);
        requestData();
    }

    @Override
    public void onPullRefresh() {
        page = 1;
        isLoadMore = false;
        requestData();
    }

    @Override
    public void onLoadMore() {
        page++;
        isLoadMore = true;
        requestData();
    }

    @Override
    protected List<Cell> getCells(List<RentCarBean> list) {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.exit:
                startActivity(new Intent(this, RentalSelfOrderActivity.class));
                break;
        }
    }

    private void requestData() {
        Map<String, Object> parame = new HashMap<>();
        parame.put("page", page);
        HttpProxy.obtain().get(PlatformContans.CarRental.getCarRental, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (isLoadMore) {
                    mBaseAdapter.hideLoadMore();
                }
                LogUtil.Log("getCarRental", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONArray beanList = object.getJSONObject("data").getJSONArray("beanList");
                        Gson gson = new Gson();
                        List<RentCarBean> list = new ArrayList<>();
                        int length = beanList.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject item = beanList.getJSONObject(i);
                            RentCarBean bean = gson.fromJson(item.toString(), RentCarBean.class);
                            list.add(bean);
                        }

                        if (isLoadMore) {
                            isLoadMore = false;
                            mBaseAdapter.setData(list);
                        } else {
                            mBaseAdapter.setDataByRemove(list);
                        }

                    } else {
                        ToaskUtil.showToast(RentCarActivity.this, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onLtFailure(String error) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (isLoadMore) {
                    mBaseAdapter.hideLoadMore();
                }

            }
        });
    }

    @Override
    public View addToolbar() {
        View view = LayoutInflater.from(this).inflate(R.layout.toobar_head_layout, null);
        TextView orderText = (TextView) view.findViewById(R.id.exit);
//        orderText.setVisibility(View.VISIBLE);
//        orderText.setText("去下单");
        view.findViewById(R.id.back).setOnClickListener(this);
        orderText.setOnClickListener(this);
        ((TextView) view.findViewById(R.id.title)).setText("租车");
        return view;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void updatRentCarUI(UpdatRentCarUI updatRentCarUI) {
        LogUtil.Log("updatRentCarUI", "更新租车数据");
        page = 1;
        isLoadMore = false;
        requestData();
    }

}
