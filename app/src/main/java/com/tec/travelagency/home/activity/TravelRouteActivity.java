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
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.common.rv.abs.AbsBaseActivity;
import com.tec.travelagency.common.rv.base.Cell;
import com.tec.travelagency.home.entity.ScenicBean;
import com.tec.travelagency.home.entity.TravelRouteBean;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TravelRouteActivity extends AbsBaseActivity<TravelRouteBean> {

    private int page = 1;
    private boolean isLoadMore = false;


    @Override
    public void onRecyclerViewInitialized() {
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
    protected List<Cell> getCells(List<TravelRouteBean> list) {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.exit:
                startActivity(new Intent(this, PathSelfOrderActivity.class));
                break;
        }
    }

    @Override
    public View addToolbar() {
        View view = LayoutInflater.from(this).inflate(R.layout.toobar_head_layout, null);
        view.findViewById(R.id.back).setOnClickListener(this);
        ((TextView) view.findViewById(R.id.title)).setText("路线");

        TextView orderText = (TextView) view.findViewById(R.id.exit);
//        orderText.setVisibility(View.VISIBLE);
//        orderText.setText("去下单");
        view.findViewById(R.id.back).setOnClickListener(this);
        orderText.setOnClickListener(this);

        return view;
    }

    private void requestData() {
//        List<TravelRouteBean> list = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            List<TravelRouteBean.NavigationPath> pathList = new ArrayList<>();
//            for (int j = 0; j < 10; j++) {
//                TravelRouteBean.NavigationPath navigationPath = new TravelRouteBean.NavigationPath();
//                pathList.add(navigationPath);
//            }
//            TravelRouteBean bean = new TravelRouteBean();
//            bean.setPathList(pathList);
//            list.add(bean);
//        }
//        mBaseAdapter.setData(list);


        Map<String, Object> parame = new HashMap<>();
        parame.put("page", page);
        HttpProxy.obtain().get(PlatformContans.Route.getList, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (isLoadMore) {
                    mBaseAdapter.hideLoadMore();
                }

                LogUtil.Log("getList", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        JSONArray beanList = data.getJSONArray("beanList");
                        Gson gson = new Gson();
                        List<TravelRouteBean> list = new ArrayList<>();
                        int length = beanList.length();
                        if (length <= 0 && data.getInt("pageNum") != 1) {
                            ToaskUtil.showToast(TravelRouteActivity.this, "亲，真的没有了");
                        }
                        for (int i = 0; i < length; i++) {
                            JSONObject item = beanList.getJSONObject(i);
                            TravelRouteBean bean = gson.fromJson(item.toString(), TravelRouteBean.class);
                            list.add(bean);
                        }

                        if (isLoadMore) {
                            isLoadMore = false;
                            mBaseAdapter.setData(list);
                        } else {
                            mBaseAdapter.setDataByRemove(list);
                        }

                    } else {
                        ToaskUtil.showToast(TravelRouteActivity.this, message);
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
