package com.tec.travelagency.common.myviewpager;

/**
 * 作者：凌涛 on 2018/9/5 11:43
 * 邮箱：771548229@qq..com
 */

import android.content.Context;
import android.view.View;

public interface ViewPagerHolder<T> {
    /**
     * 创建View * @param context * @return
     */
    View createView(Context context);

    /**
     * 绑定数据 * @param context * @param position * @param data
     */
    void onBind(Context context, int position, T data);
}

