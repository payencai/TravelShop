package com.tec.travelagency.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tec.travelagency.R;

import java.util.List;

/**
 * 作者：凌涛 on 2018/9/28 14:09
 * 邮箱：771548229@qq..com
 */
public class ScrollBanner extends LinearLayout {

    private TextView mBannerTV1;
    private TextView mBannerTV2;
    private Handler handler;
    //表示textview1 是否是显示i状态
    private boolean isShow = false;
    private int startY1, endY1, startY2, endY2;
    private Runnable runnable;
    private List<String> list;
    private int position = 0;
    private int offsetY = 2;

    public ScrollBanner(Context context) {
        this(context, null);
    }

    public ScrollBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.view_scroll_banner, this);
//        offsetY = view.getLayoutParams().height;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScrollBanner);
        offsetY = ta.getDimensionPixelOffset(R.styleable.ScrollBanner_offsetY, 120);
        ta.recycle();

        mBannerTV1 = (TextView) view.findViewById(R.id.tv_banner1);
        mBannerTV2 = (TextView) view.findViewById(R.id.tv_banner2);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                //1、isShow=true
                //2、isShow=false
                isShow = !isShow;

                if (position == list.size() - 1) {//position=2
                    position = 0;
                }
                if (isShow) {//position=0
                    mBannerTV1.setText(list.get(position));//0
                    mBannerTV2.setText(list.get(++position));//1

                } else {//position=1
                    mBannerTV2.setText(list.get(position));
                    mBannerTV1.setText(list.get(++position));
                }

                if (position == list.size() - 1) {//position=2

                }

                //两趟后position=2

                Log.d("caonima", "mBannerTV1: "+mBannerTV1.getText().toString());
                Log.d("caonima", "mBannerTV2: "+mBannerTV2.getText().toString());

                startY1 = isShow ? 0 : offsetY;
                endY1 = isShow ? -offsetY : 0;
                //1 startY1=0  endY1=-100;
                //2 startY1=100 endY1=0;
                ObjectAnimator.ofFloat(mBannerTV1, "translationY", startY1, endY1).setDuration(300).start();
                startY2 = isShow ? offsetY : 0;
                endY2 = isShow ? 0 : -offsetY;
                //1 startY2=100  endY2=0;
                //2 startY2=0 endY2=-100;
                ObjectAnimator.ofFloat(mBannerTV2, "translationY", startY2, endY2).setDuration(300).start();

                if (isShow) {
                    Log.d("caonima", "run: true");
                } else {
                    Log.d("caonima", "run: false");

                }
                handler.postDelayed(runnable, 3000);
            }
        };
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void startScroll() {
        handler.post(runnable);
    }

    public void stopScroll() {
        handler.removeCallbacks(runnable);
    }


}
