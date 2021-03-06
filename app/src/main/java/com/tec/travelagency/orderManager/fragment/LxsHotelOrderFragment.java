package com.tec.travelagency.orderManager.fragment;


import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.common.rv.abs.AbsBaseFragment;
import com.tec.travelagency.common.rv.base.Cell;
import com.tec.travelagency.home.entity.UpdateOrderFragment;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.orderManager.entity.AccountPaidOrderInfo2;
import com.tec.travelagency.orderManager.entity.LxsHotelOrder;
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

/**
 * 作者：凌涛 on 2018/8/7 10:31
 * 邮箱：771548229@qq..com
 */
public class LxsHotelOrderFragment extends AbsBaseFragment<LxsHotelOrder> {

    private int page = 0;
    private boolean isLoadMore = false;


    public static LxsHotelOrderFragment getInstance(int categoryId) {
        LxsHotelOrderFragment baseFragment = new LxsHotelOrderFragment();
        return baseFragment;
    }

    @Override
    public void onRecyclerViewInitialized() {
        EventBus.getDefault().register(this);
        page = 1;
        isLoadMore = false;
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
    public void startLoadData() {

    }
    @Subscribe
    public void UpdateOrder(UpdateOrderFragment updateOrderFragment) {
        page=1;
        isLoadMore = false;
        requestData();
    }

    @Override
    protected List<Cell> getCells(List<LxsHotelOrder> list) {
        return null;
    }

    private void requestData() {
        Map<String, Object> parame = new HashMap<>();
        parame.put("page", page);
        HttpProxy.obtain().get(PlatformContans.Order.getHotelOrderList, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (isLoadMore) {
                    mBaseAdapter.hideLoadMore();
                }
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        JSONArray beanList = data.getJSONArray("beanList");
                        int length = beanList.length();
                        Gson gson = new Gson();
                        List<LxsHotelOrder> list = new ArrayList<>();

                        int pageNum = data.getInt("pageNum");
                        for (int i = 0; i < length; i++) {
                            JSONObject item = beanList.getJSONObject(i);
                            LxsHotelOrder bean = gson.fromJson(item.toString(), LxsHotelOrder.class);
                            list.add(bean);
                        }
                        if (list.size() == 0 && pageNum != 1) {
                            //ToaskUtil.showToast(getContext(), "真的到底了");
                            return;
                        }
                        if (isLoadMore) {
                            isLoadMore = false;
                            mBaseAdapter.setData(list);
                        } else {
                            mBaseAdapter.setDataByRemove(list);
                        }

                    } else {
                        ToaskUtil.showToast(getContext(), message);
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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
