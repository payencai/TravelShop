package com.tec.travelagency.home.fragment;

import com.tec.travelagency.common.rv.abs.AbsBaseFragment;
import com.tec.travelagency.common.rv.base.Cell;
import com.tec.travelagency.home.entity.AddedHotelBean;
import com.tec.travelagency.home.entity.NewHotelBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：凌涛 on 2018/8/8 16:14
 * 邮箱：771548229@qq..com
 */
public class NewHotelFragment extends AbsBaseFragment<NewHotelBean> {

    private boolean isLoadMore = false;
    private int page=1;

    @Override
    public void onRecyclerViewInitialized() {
        addDividerItem(0);
        page=1;
        requestData();
    }

    @Override
    public void onPullRefresh() {
        page++;
        isLoadMore = false;
        startLoadData();
    }

    @Override
    public void onLoadMore() {
        page = 1;
        isLoadMore = true;
        startLoadData();
    }

    @Override
    public void startLoadData() {

    }

    @Override
    protected List<Cell> getCells(List<NewHotelBean> list) {
        return null;
    }

    private void requestData() {
        List<NewHotelBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NewHotelBean hotelBean = new NewHotelBean();
            list.add(hotelBean);
        }
        mBaseAdapter.setDataByRemove(list);
    }
}
