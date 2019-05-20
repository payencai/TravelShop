package com.tec.travelagency.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 作者：凌涛 on 2018/8/6 16:08
 * 邮箱：771548229@qq..com
 */
public class ScreenUtils {


    public static int getScreenWidth(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        return width;
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int height = outMetrics.heightPixels;
        return height;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = 0;
        Class<?> localClass;
        try {
            localClass = Class.forName("com.android.internal.R$dimen");
            Object localObject = localClass.newInstance();
            int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
            statusHeight = context.getResources().getDimensionPixelSize(i5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }
}
