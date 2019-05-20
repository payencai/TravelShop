package com.tec.travelagency.orderManager.fragment;


import android.os.Bundle;

import com.google.gson.Gson;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.common.rv.abs.AbsBaseFragment;
import com.tec.travelagency.common.rv.base.Cell;
import com.tec.travelagency.home.entity.UpdateOrderFragment;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.orderManager.entity.AccountPaidOrderInfo;
import com.tec.travelagency.orderManager.entity.AccountPaidOrderInfo2;
import com.tec.travelagency.orderManager.entity.UserOrderListBean;
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
public class UserOrderCategoryFragment extends AbsBaseFragment<UserOrderListBean> {
    private int page = 0;
    private boolean isLoadMore = false;
    private int mCategoryId = 0;//订单类型


    public static UserOrderCategoryFragment getInstance(int categoryId) {
        UserOrderCategoryFragment baseFragment = new UserOrderCategoryFragment();
        Bundle args = new Bundle();
        args.putInt("categoryId", categoryId);
        baseFragment.setArguments(args);
        return baseFragment;
    }

    @Override
    public void onRecyclerViewInitialized() {
        EventBus.getDefault().register(this);

        Bundle bundle = getArguments();
        mCategoryId = bundle.getInt("categoryId", 0);
        if (mCategoryId <= 0) {
            return;
        }

        page = 1;
        isLoadMore = false;
        requestData();
//        setTestDate();
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

    @Subscribe
    public void UpdateOrder(UpdateOrderFragment updateOrderFragment) {
        page=1;
        isLoadMore = false;
        requestData();
    }

    @Override
    public void startLoadData() {

    }

    @Override
    protected List<Cell> getCells(List<UserOrderListBean> list) {
        return null;
    }

    private void requestData() {
        if (mCategoryId != 1) {
            return;
        }
        Map<String, Object> parame = new HashMap<>();
        parame.put("page", page);
        parame.put("categoryId", mCategoryId);
        HttpProxy.obtain().get(PlatformContans.Order.getOrdersForTravelAgency1, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("Orders1", result);
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
                        List<UserOrderListBean> list = new ArrayList<>();

                        int pageNum = data.getInt("pageNum");
                        for (int i = 0; i < length; i++) {
                            JSONObject item = beanList.getJSONObject(i);
                            UserOrderListBean bean = gson.fromJson(item.toString(), UserOrderListBean.class);
                            list.add(bean);
                        }
                        if (list.size() == 0 && pageNum != 1) {
                            ToaskUtil.showToast(getContext(), "真的到底了");
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

    private void setTestDate() {
        List<AccountPaidOrderInfo> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AccountPaidOrderInfo accountPaidOrderInfo = new AccountPaidOrderInfo();
            accountPaidOrderInfo.setCategoryId(i + "");
        }

        mBaseAdapter.setData(list);
    }

//    @Override
//    public View addToolbar() {
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.toobar_head_layout, null);
//        ((TextView) view.findViewById(R.id.title)).setText("订单");
//        ImageView mineImg = (ImageView) view.findViewById(R.id.mine);
//        view.findViewById(R.id.back).setVisibility(View.GONE);
//        mineImg.setVisibility(View.VISIBLE);
//        mineImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(), MineActivity.class));
//            }
//        });
//        return view;
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
