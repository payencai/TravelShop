package com.tec.travelagency.common.rv.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.tec.travelagency.R;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.common.rv.base.RVSimpleAdapter;


/**
 * Created by HIAPAD on 2017/12/2.
 */

public class EmptyCell extends RVAbsStateCell {
    public EmptyCell(Object o) {
        super(o);
    }

    @Override
    public int getItemType() {
        return RVSimpleAdapter.EMPTY_TYPE;
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position, Context context, RVBaseAdapter adapter) {

    }

    @Override
    protected View getDefaultView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.rv_empty_layout, null);
    }

    @Override
    public void releaseResource() {
        super.releaseResource();
    }
}
