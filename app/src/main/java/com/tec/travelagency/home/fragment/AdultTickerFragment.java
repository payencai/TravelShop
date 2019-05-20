package com.tec.travelagency.home.fragment;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.common.rv.abs.AbsBaseFragment;
import com.tec.travelagency.common.rv.base.Cell;
import com.tec.travelagency.home.activity.DoorTicketActivity;
import com.tec.travelagency.home.activity.HotelSelfOrderActivity;
import com.tec.travelagency.home.entity.AdultTickerBean;
import com.tec.travelagency.home.entity.DoorTicket;
import com.tec.travelagency.home.entity.HotelSelfOrderBean;
import com.tec.travelagency.home.entity.ScenicSelfOrderBean;
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
 * 作者：凌涛 on 2018/9/6 17:58
 * 邮箱：771548229@qq..com
 */
@SuppressLint("ValidFragment")
public class AdultTickerFragment extends AbsBaseFragment<AdultTickerBean> {

    private int type = 1;//1为成人票，2为儿童票
    private ScenicSelfOrderBean mScenicSpotId;

    public AdultTickerFragment(int type, ScenicSelfOrderBean scenicSpotId) {
        this.type = type;
        mScenicSpotId = scenicSpotId;
    }

    @Override
    public void onRecyclerViewInitialized() {
        addDividerItem(0);
        requstDate();
    }

    @Override
    public void onPullRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onLoadMore() {
        requstDate();
    }

    @Override
    public void startLoadData() {

    }

    @Override
    protected List<Cell> getCells(List<AdultTickerBean> list) {
        return null;
    }

    private void requstDate() {

        Map<String, Object> parame = new HashMap<>();
        parame.put("scenicSpotId", mScenicSpotId.getId());
        parame.put("type", type);
        HttpProxy.obtain().get(PlatformContans.Ticket.getList, parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("AdultTicket", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONArray data = object.getJSONArray("data");
                        Gson gson = new Gson();
                        List<AdultTickerBean> list = new LinkedList<>();
                        int length = data.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject item = data.getJSONObject(i);
                            AdultTickerBean bean = gson.fromJson(item.toString(), AdultTickerBean.class);
//                            bean.setScenicBean(mScenicBean);
                            list.add(bean);
                        }

                        mBaseAdapter.setDataByRemove(list);
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
}
