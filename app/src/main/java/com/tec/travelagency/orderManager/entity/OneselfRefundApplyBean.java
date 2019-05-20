package com.tec.travelagency.orderManager.entity;

import android.content.Context;
import android.view.ViewGroup;

import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseCell;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/9/25 11:30
 * 邮箱：771548229@qq..com
 */
public class OneselfRefundApplyBean extends RVBaseCell implements Serializable {

    public OneselfRefundApplyBean() {
        super(null);
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position, Context context, RVBaseAdapter adapter) {

    }
}
