package com.tec.travelagency.orderManager.fragment.detail;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 作者：凌涛 on 2018/11/20 15:18
 * 邮箱：771548229@qq..com
 */

public class MaxTwoLineTextLabelLayout extends ViewGroup {
    private static final int MAX_LINE = 2;//如果要改成TextView最大其他行数，这里跟xml文件同时修改，保持值一致(目前支持2行，其他行还有bug，需要下面代码调整下
    private static final int CHILD_COUNT = 2;//目前支持包含两个子控件，左边必须是TextView，右边是任意的View或ViewGroup

    public MaxTwoLineTextLabelLayout(Context context) {
        super(context);
    }

    public MaxTwoLineTextLabelLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() == CHILD_COUNT && getChildAt(0) instanceof TextView) {
            int width = r - l;

            TextView child0 = (TextView) getChildAt(0);
            int child0Width = child0.getMeasuredWidth();
            int child0Height = child0.getMeasuredHeight();

            View child1 = getChildAt(1);
            int child1Width = child1.getMeasuredWidth();
            MarginLayoutParams lp = (MarginLayoutParams) child1.getLayoutParams();
            int child1Height = child1.getMeasuredHeight();

            int destWidth = child0Width + child1Width + lp.leftMargin;
            if (destWidth > width) {//一行显示不下
                if (child0.getLineCount() == 1) {//文本只有一行
                    child0.layout(0, 0, child0Width, child0Height);
                    int top = child0Height + lp.topMargin;
                    child1.layout(0, top, child1Width, top + child1Height);
                } else {
                    child0.layout(0, 0, child0Width, child0Height);
                    int lineWidth = getLineWidth(child0, MAX_LINE - 1);
                    if (lineWidth + lp.leftMargin + child1Width < width) {
                        int left = lineWidth + lp.leftMargin;
                        int top = (child0Height + child0Height / 2 - child1Height) / 2;
                        child1.layout(left, top, left + child1Width, top + child1Height);
                    }
                }
            } else {
                child0.layout(0, 0, child0Width, child0Height);
                int left = child0Width + lp.leftMargin;
                int top = (child0Height - child1Height) / 2;
                child1.layout(left, top, left + child1Width, top + child1Height);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);

        if (getChildCount() == 2 && getChildAt(0) instanceof TextView) {
            TextView child0 = (TextView) getChildAt(0);
            measureChild(child0, widthMeasureSpec, heightMeasureSpec);
            int child0Width = child0.getMeasuredWidth();
            int child0Height = child0.getMeasuredHeight();

            View child1 = getChildAt(1);
            measureChild(child1, widthMeasureSpec, heightMeasureSpec);
            int child1Width = child1.getMeasuredWidth();
            MarginLayoutParams lp = (MarginLayoutParams) child1.getLayoutParams();
            int child1Height = child1.getMeasuredHeight();

            int destWidth = child0Width + child1Width + lp.leftMargin;
            int destHeight = 0;
            if (destWidth > maxWidth) {//一行显示不下
                if (child0.getLineCount() == 1) {//文本只有一行
                    destWidth = child0Width;
                    destHeight = lp.topMargin + child0Height + child1Height;//+ line extraspace
                } else {
                    destWidth = Math.max(child0Width, maxWidth);
                    destHeight = child0Height;
                }
            } else {
                destHeight = child0Height;
            }
            setMeasuredDimension(destWidth, destHeight);
        } else {
            setMeasuredDimension(maxWidth, maxHeight);
        }
    }


    private int getLineWidth(TextView textView, int lineNum) {
        Layout layout = textView.getLayout();
        int lineCount = textView.getLineCount();
        if (layout != null && lineNum >= 0 && lineNum < lineCount) {
            return (int) (layout.getLineWidth(lineNum) + 0.5);
        }

        return 0;
    }
}

