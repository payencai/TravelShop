package com.tec.travelagency.common.rvItemHelper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.tec.travelagency.common.adapter.ItemTouchHelperAdapter;

/**
 * 作者：凌涛 on 2018/8/8 16:19
 * 邮箱：771548229@qq..com
 */
public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private ItemTouchHelperAdapter mAdapter;

    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
        mAdapter = adapter;
    }

    //限制ImageView长度所能增加的最大值
    private double ICON_MAX_SIZE = 50;
    //ImageView的初始长宽
    private int fixedWidth = 150;


    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
//        //重置改变，防止由于复用而导致的显示问题
//        viewHolder.itemView.setScrollX(0);
//        ((RvItemHolperAdapter.NormalItem) viewHolder).tv.setText("左滑删除");
//        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) ((MyAdapter.NormalItem) viewHolder).iv.getLayoutParams();
//        params.width = 150;
//        params.height = 150;
//        ((MyAdapter.NormalItem) viewHolder).iv.setLayoutParams(params);
//        ((MyAdapter.NormalItem) viewHolder).iv.setVisibility(View.INVISIBLE);

    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;  //允许上下的拖动
        int swipeFlags = ItemTouchHelper.LEFT;   //只允许从右向左侧滑
        return makeMovementFlags(dragFlags, swipeFlags);
//        return swipeFlags;

    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //onItemMove是接口方法
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //onItemDissmiss是接口方法
        mAdapter.onItemDissmiss(viewHolder.getAdapterPosition());

    }
}
