package com.tec.travelagency.home.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.home.entity.TravelPathDetailBean;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RouteDetailActivity extends BaseActivity {


    @BindView(R.id.pathBgImg)
    ImageView pathBgImg;
    @BindView(R.id.mine)
    ImageView mine;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.include)
    RelativeLayout headLayout;
    @BindView(R.id.navigationRv)
    RecyclerView navigationRv;
    private RVBaseAdapter<TravelPathDetailBean> mAdapter;
    private TravelRouteBean mTravelRouteBean;
    private List<TravelPathDetailBean> mList;


    @Override
    protected int getContentId() {
        return R.layout.activity_route_detail;
    }

    @Override
    protected void initView() {

        Intent intent = getIntent();
        mTravelRouteBean = ((TravelRouteBean) intent.getSerializableExtra("TravelRouteBean"));
        if (mTravelRouteBean == null) {
            finish();
            return;
        }
        initRV();
        getPathDetail();
        Glide.with(this).load(R.drawable.bg_pathtext).into(pathBgImg);
        title.setText("路线详情");
        headLayout.setBackgroundColor(Color.TRANSPARENT);
    }

    private void initRV() {
        mList = new LinkedList<>();
        mAdapter = new RVBaseAdapter<TravelPathDetailBean>() {
            @Override
            protected void onViewHolderBound(RVBaseViewHolder holder, int position) {

            }

            @Override
            protected void onViewClick(RVBaseViewHolder holder, int position) {
                if (mData == null) {
                    return;
                }
                View view1 = holder.getView(R.id.view1);
                View view2 = holder.getView(R.id.view2);
                if (position == 0) {
                    view1.setVisibility(View.GONE);
                }
                if (position == mData.size() - 1) {
                    view2.setVisibility(View.GONE);
                }

            }
        };
        mAdapter.setData(mList);
        navigationRv.setLayoutManager(new LinearLayoutManager(this));
        navigationRv.setAdapter(mAdapter);
    }

    private void getPathDetail() {

        Map<String, Object> parame = new HashMap<>();
        parame.put("id", mTravelRouteBean.getId());

        HttpProxy.obtain().get(PlatformContans.Route.getDetail, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {


            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("getDetail", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        JSONArray routeScenicSpotList = data.getJSONArray("routeScenicSpotList");
                        int length = routeScenicSpotList.length();
//                        mList = new LinkedList<>();
                        Gson gson = new Gson();
                        for (int i = 0; i < length; i++) {
                            JSONObject item = routeScenicSpotList.getJSONObject(i);
                            TravelPathDetailBean bean = gson.fromJson(item.toString(), TravelPathDetailBean.class);
                            mList.add(bean);
                        }
                        mAdapter.setDataByRemove(mList);
                    } else {
                        ToaskUtil.showToast(RouteDetailActivity.this, message);
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

    @OnClick({R.id.back, R.id.mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.mine:
                Intent intent = new Intent(this, SelectUpdatePriceOneDayActivity.class);
                intent.putExtra("TravelRouteBean", mTravelRouteBean);
                startActivity(intent);
                break;
        }
    }
}
