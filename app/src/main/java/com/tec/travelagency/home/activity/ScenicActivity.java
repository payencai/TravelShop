package com.tec.travelagency.home.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.common.rv.abs.AbsBaseActivity;
import com.tec.travelagency.common.rv.base.Cell;
import com.tec.travelagency.home.entity.DoorTicket;
import com.tec.travelagency.home.entity.ScenicBean;
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

public class ScenicActivity extends AbsBaseActivity<ScenicBean> {


    private int page = 1;
    private boolean isLoadMore = false;

    @Override
    public void onRecyclerViewInitialized() {
        addDividerItem(0);
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
    protected List<Cell> getCells(List<ScenicBean> list) {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.exit:
                startActivity(new Intent(this, ScenicSelfOrderActivity.class));
                break;
        }
    }

    @Override
    public View addToolbar() {
        View view = LayoutInflater.from(this).inflate(R.layout.toobar_head_layout, null);
        view.findViewById(R.id.back).setOnClickListener(this);
        ((TextView) view.findViewById(R.id.title)).setText("景点");

        TextView orderText = (TextView) view.findViewById(R.id.exit);
//        orderText.setVisibility(View.VISIBLE);
//        orderText.setText("去下单");
        view.findViewById(R.id.back).setOnClickListener(this);
        orderText.setOnClickListener(this);
        return view;
    }

    private void requestData() {
        Map<String, Object> parame = new HashMap<>();
        parame.put("page", page);
        HttpProxy.obtain().post(PlatformContans.Scenic.getList, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
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
                        List<ScenicBean> list = new ArrayList<>();
                        int length = beanList.length();
                        if (length <= 0 && data.getInt("pageNum") != 1) {
                            ToaskUtil.showToast(ScenicActivity.this, "亲，真的没有了");
                        }
                        for (int i = 0; i < length; i++) {
                            JSONObject item = beanList.getJSONObject(i);
                            ScenicBean bean = gson.fromJson(item.toString(), ScenicBean.class);
                            list.add(bean);
//                            for (int j = 0; j < 10; j++) {
//                            }
                        }

                        if (isLoadMore) {
                            isLoadMore = false;
                            mBaseAdapter.setData(list);
                        } else {
                            mBaseAdapter.setDataByRemove(list);
                        }

                    } else {
                        ToaskUtil.showToast(ScenicActivity.this, message);
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
