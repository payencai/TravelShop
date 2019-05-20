package com.tec.travelagency.orderManager.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.common.rv.abs.AbsBaseActivity;
import com.tec.travelagency.common.rv.base.Cell;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.orderManager.entity.AllOderBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AllOderActivity extends AbsBaseActivity<AllOderActivity> {

    @Override
    public void onRecyclerViewInitialized() {
        requstData();
    }

    @Override
    public void onPullRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    protected List<Cell> getCells(List<AllOderActivity> list) {
        return null;
    }

    private void requstData() {
        List<AllOderBean> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new AllOderBean());
        }
        mBaseAdapter.setData(list);

    }


    @Override
    public View addToolbar() {
        View view = LayoutInflater.from(this).inflate(R.layout.toobar_head_layout, null);
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((TextView) view.findViewById(R.id.title)).setText("订单");
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
