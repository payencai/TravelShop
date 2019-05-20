package com.tec.travelagency.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.tec.travelagency.R;

/**
 * 作者：凌涛 on 2018/8/8 14:36
 * 邮箱：771548229@qq..com
 */
public class RoundedRectangleLayout extends RelativeLayout {

    private Paint mPaint;

    private int bgColor;

    //矩形叫的圆半径
    private int radius;


    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        invalidate();
    }
    public RoundedRectangleLayout(Context context) {
        super(context);
        init(context, null);
    }

    public RoundedRectangleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public RoundedRectangleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @SuppressLint("ResourceAsColor")
    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundedRectangleLayout);
        bgColor = ta.getColor(R.styleable.RoundedRectangleLayout_bgRoundedColor, Color.BLACK);
        radius = ta.getDimensionPixelSize(R.styleable.RoundedRectangleLayout_radius, R.dimen.fbutton_default_shadow_height);
        ta.recycle();
        mPaint.setColor(bgColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        //获取控件的宽高
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        canvas.drawRoundRect(new RectF(0, 0, measuredWidth, measuredHeight), radius, radius, mPaint);
        canvas.drawColor(bgColor);
    }
}
