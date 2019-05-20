package com.tec.travelagency.home.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DoorTicketActivity extends AbsBaseActivity<DoorTicket> {


    private int page = 1;
    private boolean isLoadMore = false;

    private ScenicBean mScenicBean;

    @Override
    public void onRecyclerViewInitialized() {
        Intent intent = getIntent();
        mScenicBean = ((ScenicBean) intent.getSerializableExtra("scenicBean"));
        if (mScenicBean == null) {
            finish();
            return;
        }
        addDividerItem(0);
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
    protected List<Cell> getCells(List<DoorTicket> list) {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public View addToolbar() {
        View view = LayoutInflater.from(this).inflate(R.layout.toobar_head_layout, null);
        view.findViewById(R.id.back).setOnClickListener(this);
        ((TextView) view.findViewById(R.id.title)).setText("门票");
        return view;
    }

    private void requestData() {

        Map<String, Object> parame = new HashMap<>();
        parame.put("scenicSpotId", mScenicBean.getId());
        HttpProxy.obtain().get(PlatformContans.Ticket.getList, parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("scenicSpotId", result);
                mSwipeRefreshLayout.setRefreshing(false);
                if (isLoadMore) {
                    mBaseAdapter.hideLoadMore();
                }
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONArray data = object.getJSONArray("data");
                        Gson gson = new Gson();
                        List<DoorTicket> list = new LinkedList<>();
                        int length = data.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject item = data.getJSONObject(i);
                            DoorTicket bean = gson.fromJson(item.toString(), DoorTicket.class);
                            bean.setScenicBean(mScenicBean);
                            list.add(bean);
                        }

                        if (isLoadMore) {
                            isLoadMore = false;
                            mBaseAdapter.setData(list);
                        } else {
                            mBaseAdapter.setDataByRemove(list);
                        }
                    } else {
                        ToaskUtil.showToast(DoorTicketActivity.this, message);
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
