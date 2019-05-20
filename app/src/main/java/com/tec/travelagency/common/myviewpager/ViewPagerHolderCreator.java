package com.tec.travelagency.common.myviewpager;

/**
 * 作者：凌涛 on 2018/9/5 11:43
 * 邮箱：771548229@qq..com
 */
public interface ViewPagerHolderCreator<VH extends ViewPagerHolder> {

    /**
     * 创建ViewHolder
     * @return
     */
    public VH createViewHolder();

}
