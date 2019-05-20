package com.tec.travelagency.common.rvItemHelper;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.tec.travelagency.common.rv.base.RVBaseAdapter;

import java.util.Collections;

/**
 * 作者：凌涛 on 2018/8/8 16:52
 * 邮箱：771548229@qq..com
 */
public class MyCallBack extends ItemTouchHelper.Callback {

    private RecyclerView.Adapter mAdapter;

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //得到当拖拽的viewHolder的Position
        int fromPosition = viewHolder.getAdapterPosition();
        //拿到当前拖拽到的item的viewHolder
        int toPosition = target.getAdapterPosition();
//        GoodsSortTypeBean.Entity entity = mAdapter.getData().get(fromPosition);
//        GoodsSortTypeBean.Entity entity2 = mAdapter.getData().get(toPosition);
//        Object o = mAdapter.getData().get(fromPosition);
//        Object o2 = mAdapter.getData().get(toPosition);
//        /*commodityNo*/
//        entity.setCommodityNo(entity2.getCommodityNo());
//        cacheMap.put(entity, entity2.getCommodityNo());
//
//        if (fromPosition < toPosition) {
//            for (int i = fromPosition; i < toPosition; i++) {
//                Collections.swap(mEntityRVBaseAdapter.getData(), i, i + 1);
//            }
//        } else {
//            for (int i = fromPosition; i > toPosition; i--) {
//                Collections.swap(mEntityRVBaseAdapter.getData(), i, i - 1);
//            }
//        }
        mAdapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(0);
    }
}