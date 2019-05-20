package com.tec.travelagency.home.activity;

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
import com.tec.travelagency.home.entity.HotelSelfOrderBean;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.orderManager.activity.HotelOrderDetailActivity;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HotelSelfOrderActivity extends AbsBaseActivity<HotelSelfOrderBean> {

    private int page = 1;
    private boolean isLoadMore = false;

    @Override
    public void onRecyclerViewInitialized() {
        addDividerItem(0);
        page = 1;
        isLoadMore = false;
        requestDate();
    }


    @Override
    public void onPullRefresh() {
        page = 1;
        isLoadMore = false;
        requestDate();

    }

    @Override
    public void onLoadMore() {
        page++;
        isLoadMore = true;
        requestDate();

    }

    @Override
    public View addToolbar() {
        View view = LayoutInflater.from(this).inflate(R.layout.toobar_head_layout, null);
        view.findViewById(R.id.back).setOnClickListener(this);
        ((TextView) view.findViewById(R.id.title)).setText("酒店");
        return view;
    }

    @Override
    protected List<Cell> getCells(List<HotelSelfOrderBean> list) {
        return null;
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    private void requestDate() {
        Map<String, Object> parame = new HashMap<>();
        parame.put("page", page);
        parame.put("travelAgencyId", App.getInstance().getUserEntity().getTravelAgencyId());
        HttpProxy.obtain().get(PlatformContans.TravelAgency.getHotelsForCustomer, parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (isLoadMore) {
                    mBaseAdapter.hideLoadMore();
                }
                LogUtil.Log("HotelsForCustomer", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {

                        JSONObject data = object.getJSONObject("data");
                        JSONArray beanList = data.getJSONArray("beanList");
                        int length = beanList.length();
                        List<HotelSelfOrderBean> list = new LinkedList<>();
                        Gson gson = new Gson();

                        int pageNum = data.getInt("pageNum");

                        for (int i = 0; i < length; i++) {
                            JSONObject item = beanList.getJSONObject(i);
                            HotelSelfOrderBean bean = gson.fromJson(item.toString(), HotelSelfOrderBean.class);
                            list.add(bean);
                        }
                        if (list.size() == 0 && pageNum != 1) {
                            ToaskUtil.showToast(HotelSelfOrderActivity.this, "到底了");
                            return;
                        }
                        if (isLoadMore) {
                            isLoadMore = false;
                            mBaseAdapter.setData(list);
                        } else {
                            mBaseAdapter.setDataByRemove(list);
                        }
                    } else {
                        ToaskUtil.showToast(HotelSelfOrderActivity.this, message);
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

}
