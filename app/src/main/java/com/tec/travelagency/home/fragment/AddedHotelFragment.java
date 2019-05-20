package com.tec.travelagency.home.fragment;

import com.tec.travelagency.common.rv.abs.AbsBaseFragment;
import com.tec.travelagency.common.rv.base.Cell;
import com.tec.travelagency.home.entity.AddedHotelBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：凌涛 on 2018/8/8 16:12
 * 邮箱：771548229@qq..com
 */
public class AddedHotelFragment extends AbsBaseFragment<AddedHotelBean> {
    @Override
    public void onRecyclerViewInitialized() {
        addDividerItem(0);
        requestData();
    }

    @Override
    public void onPullRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void startLoadData() {

    }

    @Override
    protected List<Cell> getCells(List<AddedHotelBean> list) {
        return null;
    }

    private void requestData() {
        List<AddedHotelBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            AddedHotelBean hotelBean = new AddedHotelBean();
            list.add(hotelBean);
        }
        mBaseAdapter.setDataByRemove(list);
    }
}
