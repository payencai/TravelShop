package com.tec.travelagency.common.adapter;

/**
 * 作者：凌涛 on 2018/8/8 16:21
 * 邮箱：771548229@qq..com
 */
public interface ItemTouchHelperAdapter {
    //数据交换
    void onItemMove(int fromPosition,int toPosition);
    //数据删除
    void onItemDissmiss(int position);

}
