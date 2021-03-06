package com.tec.travelagency.home.fragment;

import android.annotation.SuppressLint;
import android.util.Log;


import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseFragment;
import com.tec.travelagency.home.entity.DayInfo;
import com.tec.travelagency.home.entity.TravelRouteBean;
import com.tec.travelagency.widget.MonthDataFormView;
import com.tec.travelagency.widget.MonthMoneyDataFormView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;

/**
 * 作者：凌涛 on 2018/8/14 16:38
 * 邮箱：771548229@qq..com
 */
@SuppressLint("ValidFragment")
public class LastDataFragment extends BaseFragment {
    @BindView(R.id.monthSignFormView)
    MonthMoneyDataFormView monthSignFormView;
    private TravelRouteBean mTravelRouteBean;
    private int requestUpdateType = 0;//更新类型，1为路线的更新，2为门票的更新
    public LastDataFragment(TravelRouteBean travelRouteBean,int requestUpdateType) {
        mTravelRouteBean = travelRouteBean;
        this.requestUpdateType = requestUpdateType;
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_lastdata;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
    }

    public void setTravelRouteBean(TravelRouteBean travelRouteBean) {
        mTravelRouteBean = travelRouteBean;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void upDateInfo(List<DayInfo> list) {
        monthSignFormView.setList(list,requestUpdateType);
    }


}
