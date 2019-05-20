package com.tec.travelagency.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseFragment;
import com.tec.travelagency.home.entity.PathSelfDetailBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：凌涛 on 2018/9/6 15:02
 * 邮箱：771548229@qq..com
 */
public class CostInfoFragment extends BaseFragment {
    @BindView(R.id.trafficSpecification)
    TextView trafficSpecification;
    @BindView(R.id.ticketSpecification)
    TextView ticketSpecification;
    @BindView(R.id.hotelSpecification)
    TextView hotelSpecification;

    @Override
    protected int getContentId() {
        return R.layout.fragment_cost_info_layout;
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

        trafficSpecification.setText("【交通】\n\t" + bean.getTrafficSpecification());
        ticketSpecification.setText("【门票】\n\t" + bean.getTicketSpecification());
        hotelSpecification.setText("【住宿】\n\t" + bean.getHotelSpecification());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
