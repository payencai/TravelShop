package com.tec.travelagency.orderManager.fragment.user;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.gson.Gson;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.common.rv.abs.AbsBaseFragment;
import com.tec.travelagency.common.rv.base.Cell;
import com.tec.travelagency.home.entity.UpdateOrderFragment;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.orderManager.entity.LxsCarOrder;
import com.tec.travelagency.orderManager.entity.UserCarOrder;
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
 * A simple {@link Fragment} subclass.
 */
public class UserCarOrderFragment extends AbsBaseFragment<UserCarOrder> {

    private int page = 0;
    private boolean isLoadMore = false;
    private int mCategoryId = 5;//订单类型

    public static UserCarOrderFragment getInstance(int categoryId) {
        UserCarOrderFragment baseFragment = new UserCarOrderFragment();
        Bundle args = new Bundle();
        args.putInt("categoryId", categoryId);
        baseFragment.setArguments(args);
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
    protected List<Cell> getCells(List<UserCarOrder> list) {

        return null;
    }

    private void requestData() {
        ///order_of_travel_agency/getOrdersForTravelAgency
        Map<String, Object> parame = new HashMap<>();
        Log.e("token", App.getInstance().getUserEntity().getToken());
        parame.put("page", page);
        HttpProxy.obtain().get(PlatformContans.UserOrder.getUserCarOrderList, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                Log.e("carorder", result);
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
                        List<UserCarOrder> list = new ArrayList<>();
                        int pageNum = data.getInt("pageNum");
                        for (int i = 0; i < length; i++) {
                            JSONObject item = beanList.getJSONObject(i);
                            UserCarOrder bean = gson.fromJson(item.toString(), UserCarOrder.class);
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