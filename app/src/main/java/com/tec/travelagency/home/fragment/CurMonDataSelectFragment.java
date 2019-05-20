package com.tec.travelagency.home.fragment;


import android.annotation.SuppressLint;

import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseFragment;
import com.tec.travelagency.home.activity.PathSelfOrderDetailActivity;
import com.tec.travelagency.home.entity.DayInfo;
import com.tec.travelagency.home.entity.PathSelfDetailBean;
import com.tec.travelagency.home.entity.TravelRouteBean;
import com.tec.travelagency.widget.MonthMoneyDataFormView;
import com.tec.travelagency.widget.MonthMoneyDataFormView2;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;

/**
 * 作者：凌涛 on 2018/8/14 16:38
 * 邮箱：771548229@qq..com
 */
@SuppressLint("ValidFragment")
public class CurMonDataSelectFragment extends BaseFragment implements MonthMoneyDataFormView2.onIClickItem {
    @BindView(R.id.monthSignFormView)
    MonthMoneyDataFormView2 monthSignFormView;
    List<DayInfo> mDataSelectOtherBeans;

    public CurMonDataSelectFragment(List<DayInfo> dataSelectOtherBeans) {
        mDataSelectOtherBeans = dataSelectOtherBeans;
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_curmont_data_select;
    }


    @Override
    protected void initView() {
        monthSignFormView.setList(mDataSelectOtherBeans);
        monthSignFormView.setOnIClickItem(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    

    @Override
    public void onClickItem(MonthMoneyDataFormView2.Point point) {
        EventBus.getDefault().post(point);
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
