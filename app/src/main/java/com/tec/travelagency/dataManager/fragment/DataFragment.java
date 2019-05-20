package com.tec.travelagency.dataManager.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseFragment;
import com.tec.travelagency.common.activity.MineActivity;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.dataManager.entity.MarketBead;
import com.tec.travelagency.dataManager.entity.TotalTypeOrder;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.orderManager.entity.ThisMonth;
import com.tec.travelagency.orderManager.entity.ToDay;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：凌涛 on 2018/8/8 11:00
 * 邮箱：771548229@qq..com
 */
public class DataFragment extends BaseFragment {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.mine)
    ImageView mine;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.orderRv)
    RecyclerView orderRv;
    @BindView(R.id.marketRv)
    RecyclerView marketRv;
    private RVBaseAdapter<TotalTypeOrder> mOrderAdapter;
    private RVBaseAdapter<MarketBead> mMarketAdapter;
    private List<TotalTypeOrder> mTodayList;
    private List<MarketBead> mMarketBeads;

    @Override
    protected int getContentId() {
        return R.layout.fragment_data;
    }

    @Override
    protected void initView() {
        title.setText("新旅盟");
        mine.setVisibility(View.VISIBLE);
        back.setVisibility(View.GONE);

        orderRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        marketRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mOrderAdapter = new RVBaseAdapter<TotalTypeOrder>() {
            @Override
            protected void onViewHolderBound(RVBaseViewHolder holder, int position) {

            }
        };
        mMarketAdapter = new RVBaseAdapter<MarketBead>() {
            @Override
            protected void onViewHolderBound(RVBaseViewHolder holder, int position) {

            }
        };

        mTodayList = new ArrayList<>();
        initOrderRv();
        initMarketRv();
        requestData();

    }

    private void requestData() {

        HttpProxy.obtain().get(PlatformContans.Statistics.countOrdersForTravelAgencyApp, App.getInstance().getUserEntity().getToken(), new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("countOrders" + result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        JSONArray today = data.getJSONArray("today");
                        JSONArray thisMonth = data.getJSONArray("thisMonth");
                        Gson gson = new Gson();
                        List<ToDay> toDays = new ArrayList<>();
                        List<ThisMonth> thisMonths = new ArrayList<>();
                        int todayLen = today.length();
                        int mouLen = thisMonth.length();

                        for (int i = 0; i < todayLen; i++) {
                            JSONObject item = today.getJSONObject(i);
                            ToDay bean = gson.fromJson(item.toString(), ToDay.class);
                            toDays.add(bean);
                        }

                        for (int i = 0; i < mouLen; i++) {
                            JSONObject item = thisMonth.getJSONObject(i);
                            ThisMonth bean = gson.fromJson(item.toString(), ThisMonth.class);
                            thisMonths.add(bean);
                        }
                        setUpdateUI(toDays, thisMonths);

                    } else {
                        ToaskUtil.showToast(getContext(), message);
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

    private void setUpdateUI(List<ToDay> toDays, List<ThisMonth> thisMonths) {
        for (ToDay day : toDays) {
            String categoryIdString = day.getCategoryId();
            if(!TextUtils.isEmpty(categoryIdString))
            for (TotalTypeOrder order : mTodayList) {
                String categoryId = order.getCategoryId();
                if (categoryIdString.equals(categoryId)) {
                    order.setNumber(day.getOrderTotal() + "");
                }
            }

            MarketBead marketBead = mMarketBeads.get(0);
            if(!TextUtils.isEmpty(categoryIdString))
            switch (categoryIdString) {
                case "1":
                    marketBead.setHotelNum(day.getAmountTotal() + "");
                    break;
                case "2"://门票
                    marketBead.setDoorTicketNum(day.getAmountTotal() + "");
                    break;
                case "3":
                    marketBead.setRentCarNum(day.getAmountTotal() + "");
                    break;
                case "4":
                    marketBead.setPathNum(day.getAmountTotal() + "");
                    break;
            }

        }
        MarketBead marketBead = mMarketBeads.get(1);
        for (ThisMonth thisMonth : thisMonths) {
            String categoryId = thisMonth.getCategoryId();
            if(!TextUtils.isEmpty(categoryId))
            switch (categoryId) {
                case "1":
                    marketBead.setHotelNum(thisMonth.getAmountTotal() + "");
                    break;
                case "2"://门票
                    marketBead.setDoorTicketNum(thisMonth.getAmountTotal() + "");
                    break;
                case "3":
                    marketBead.setRentCarNum(thisMonth.getAmountTotal() + "");
                    break;
                case "4":
                    marketBead.setPathNum(thisMonth.getAmountTotal() + "");
                    break;
            }

        }


        mOrderAdapter.setData(mTodayList);
        orderRv.setAdapter(mOrderAdapter);

        mMarketAdapter.setData(mMarketBeads);
        marketRv.setAdapter(mMarketAdapter);

    }


    private void initOrderRv() {
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                TotalTypeOrder order = new TotalTypeOrder();
                order.setName("酒店(日)");
                order.setNumber("0");
                order.setCategoryId("1");
                mTodayList.add(order);
            }
            if (i == 1) {
                TotalTypeOrder order = new TotalTypeOrder();
                order.setName("门票(日)");
                order.setNumber("0");
                order.setCategoryId("2");
                mTodayList.add(order);
            }
            if (i == 2) {
                TotalTypeOrder order = new TotalTypeOrder();
                order.setName("租车(日)");
                order.setCategoryId("3");
                order.setNumber("0");
                mTodayList.add(order);
            }
            if (i == 3) {
                TotalTypeOrder order = new TotalTypeOrder();
                order.setName("路线(日)");
                order.setCategoryId("4");
                order.setNumber("0");
                mTodayList.add(order);
            }
        }

    }

    private void setTodayDate(ToDay day) {

    }

    private void initMarketRv() {
        mMarketBeads = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            MarketBead order = new MarketBead();
            if (i == 0) {
                order.setCategoryId("1");
                order.setName("销售额(日)");
            }
            if (i == 1) {
                order.setCategoryId("2");
                order.setName("销售额(月)");
            }
            if (i == 2) {
                order.setCategoryId("3");
                order.setName("退款(日)");
            }
            mMarketBeads.add(order);
        }

    }


    @OnClick({R.id.mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine:
                startActivity(new Intent(getContext(), MineActivity.class));
                break;
        }
    }
}
