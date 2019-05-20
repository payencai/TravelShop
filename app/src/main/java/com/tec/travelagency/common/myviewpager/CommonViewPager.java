package com.tec.travelagency.common.myviewpager;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.tec.travelagency.R;
import com.tec.travelagency.common.myviewpager.indicatorview.CircleIndicatorView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 作者：凌涛 on 2018/9/5 11:53
 * 邮箱：771548229@qq..com
 */
public class CommonViewPager<T> extends RelativeLayout {

    private CarouselHandle mHandle = new CarouselHandle(this);

    private ViewPager mViewPager;
    private CommonViewPagerAdapter mAdapter;
    private CircleIndicatorView mCircleIndicatorView;
    public static final int OPEN_CAROUSEL_CODE = 0;
    //是否开启轮播图片
    private boolean isOpenCarousel = false;
    //是否正在轮播中
    private boolean isRunningCarousel = false;

    public CommonViewPager(@NonNull Context context) {
        super(context);
        init();
    }

    public CommonViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommonViewPager(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CommonViewPager(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.common_view_pager_layout, this, true);
        mViewPager = (ViewPager) view.findViewById(R.id.common_view_pager);
        mCircleIndicatorView = (CircleIndicatorView) view.findViewById(R.id.common_view_pager_indicator_view);
    }

    /**
     * 设置数据 * @param data * @param creator
     */
    public void setPages(List<T> data, ViewPagerHolderCreator creator) {
        mAdapter = new CommonViewPagerAdapter(data, creator);
        mViewPager.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mCircleIndicatorView.setUpWithViewPager(mViewPager);
    }

    public void setCurrentItem(int currentItem) {
        mViewPager.setCurrentItem(currentItem);
    }

    public boolean isRunningCarousel() {
        return isRunningCarousel;
    }

    public void setRunningCarousel(boolean runningCarousel) {
        isRunningCarousel = runningCarousel;
    }

    public int getCurrentItem() {
        return mViewPager.getCurrentItem();
    }

    public void setOffscreenPageLimit(int limit) {
        mViewPager.setOffscreenPageLimit(limit);
    }

    /**
     * 设置切换动画，see {@link ViewPager#setPageTransformer(boolean, ViewPager.PageTransformer)}
     *
     * @param reverseDrawingOrder
     * @param transformer
     */
    public void setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        mViewPager.setPageTransformer(reverseDrawingOrder, transformer);
    }

    /**
     * see {@link ViewPager#setPageTransformer(boolean, ViewPager.PageTransformer)}
     *
     * @param reverseDrawingOrder
     * @param transformer
     * @param pageLayerType
     */
    public void setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer, int pageLayerType) {
        mViewPager.setPageTransformer(reverseDrawingOrder, transformer, pageLayerType);
    }

    /**
     * see {@link ViewPager#addOnPageChangeListener(ViewPager.OnPageChangeListener)}
     *
     * @param listener
     */
    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mViewPager.addOnPageChangeListener(listener);
    }

    /**
     * 设置是否显示Indicator *
     *
     * @param visible
     */
    private void setIndicatorVisible(boolean visible) {
        if (visible) {
            mCircleIndicatorView.setVisibility(VISIBLE);
        } else {
            mCircleIndicatorView.setVisibility(GONE);
        }
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    public boolean isOpenCarousel() {
        return isOpenCarousel;
    }

    public void setOpenCarousel(boolean openCarousel) {
        isOpenCarousel = openCarousel;
        if (isRunningCarousel) {
            return;
        }
        mHandle.sendEmptyMessageDelayed(OPEN_CAROUSEL_CODE, 3000);
    }

    private static class CarouselHandle extends Handler {

        private WeakReference<CommonViewPager> mCommonViewPager;

        public CarouselHandle(CommonViewPager commonViewPager) {
            this.mCommonViewPager = new WeakReference<>(commonViewPager);
        }

        @Override
        public void handleMessage(Message msg) {
            CommonViewPager commonViewPager = mCommonViewPager.get();
            if (commonViewPager == null) {
                return;
            }

            switch (msg.what) {
                case OPEN_CAROUSEL_CODE:
                    ViewPager viewPager = commonViewPager.getViewPager();
                    if (viewPager == null) {
                        return;
                    }
                    //如果开启轮播
                    if (commonViewPager.isOpenCarousel()) {
                        commonViewPager.isRunningCarousel = true;
                        int cur = viewPager.getCurrentItem() + 1;
                        if(viewPager.getAdapter().getCount()>0)
                        viewPager.setCurrentItem(cur % viewPager.getAdapter().getCount());
                        sendEmptyMessageDelayed(OPEN_CAROUSEL_CODE, 3000);
                    } else {
                        commonViewPager.isRunningCarousel = false;
                    }
                    break;
            }

        }
    }


}
