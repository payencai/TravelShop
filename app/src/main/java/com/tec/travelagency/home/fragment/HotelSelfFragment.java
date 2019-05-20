package com.tec.travelagency.home.fragment;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.common.rv.abs.AbsBaseFragment;
import com.tec.travelagency.common.rv.base.Cell;
import com.tec.travelagency.home.activity.HotelSelfOrderActivity;
import com.tec.travelagency.home.entity.HotelSelfBean;
import com.tec.travelagency.home.entity.HotelSelfOrderBean;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 作者：凌涛 on 2018/9/5 15:03
 * 邮箱：771548229@qq..com
 */
@SuppressLint("ValidFragment")
public class HotelSelfFragment extends AbsBaseFragment<HotelSelfBean> {

    //下单界面的列表item
    private HotelSelfOrderBean mHotelSelfOrderBean;

    public HotelSelfFragment(HotelSelfOrderBean hotelSelfOrderBean) {
        mHotelSelfOrderBean = hotelSelfOrderBean;
    }

    @Override
    public void onRecyclerViewInitialized() {
        requestDate();
    }

    @Override
    public boolean isCanShowLoadMore() {
        return false;
    }


    @Override
    public void onPullRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMore() {
//        page++;
//        isLoadMore = true;
//        requestDate();
    }

    @Override
    public void startLoadData() {

    }

    @Override
    protected List<Cell> getCells(List<HotelSelfBean> list) {
        return null;
    }


    private void requestDate() {
        Map<String, Object> parame = new HashMap<>();
        parame.put("hotelId", mHotelSelfOrderBean.getId());
        parame.put("travelAgencyId", App.getInstance().getUserEntity().getTravelAgencyId());
        HttpProxy.obtain().get(PlatformContans.TravelAgency.getRoomtypeForCustomer, parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("Roomtype", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {

                        JSONArray data= object.getJSONArray("data");
                        int length = data.length();
                        List<HotelSelfBean> list = new LinkedList<>();
                        Gson gson = new Gson();

                        for (int i = 0; i < length; i++) {
                            JSONObject item = data.getJSONObject(i);
                            HotelSelfBean bean = gson.fromJson(item.toString(), HotelSelfBean.class);
                            bean.setHotelName(mHotelSelfOrderBean.getName());
                            list.add(bean);
                        }
                        mBaseAdapter.setData(list);
                    } else {
                        ToaskUtil.showToast(getContext( ), message);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {

            }
        });

//        List<HotelSelfBean> list = new LinkedList<>();
//        for (int i = 0; i < 10; i++) {
//            list.add(new HotelSelfBean());
//        }
//        mBaseAdapter.setDataByRemove(list);

    }
}
