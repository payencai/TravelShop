package com.tec.travelagency.home.fragment;

import android.widget.TextView;

import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseFragment;
import com.tec.travelagency.home.entity.PathSelfDetailBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

/**
 * 作者：凌涛 on 2018/9/6 15:03
 * 邮箱：771548229@qq..com
 */
public class ReserveInfoFragment extends BaseFragment {
    @BindView(R.id.reservationSpecification)
    TextView reservationSpecification;

    @Override
    protected int getContentId() {
        return R.layout.fragment_reserve_info_layout;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

    }

    /**
     * 更新界面，由寄主Activity 发出
     * @param bean
     */
    @Subscribe
    public void changeUI(PathSelfDetailBean bean) {
        reservationSpecification.setText(bean.getReservationSpecification());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
