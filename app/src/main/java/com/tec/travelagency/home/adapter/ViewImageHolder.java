package com.tec.travelagency.home.adapter;

/**
 * 作者：凌涛 on 2018/9/6 09:39
 * 邮箱：771548229@qq..com
 */

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tec.travelagency.R;
import com.tec.travelagency.common.myviewpager.ViewPagerHolder;
import com.tec.travelagency.home.entity.DataEntry;

/**
 * 提供ViewPager展示的ViewHolder
 * <P>用于提供布局和绑定数据</P>
 */
public class ViewImageHolder implements ViewPagerHolder<DataEntry> {
    private ImageView mImageView;
    private TextView mTextView;

    @Override
    public View createView(Context context) {
        //  返回ViewPager 页面展示的布局
        View view = LayoutInflater.from(context).inflate(R.layout.view_pager_item, null);
        mImageView = (ImageView) view.findViewById(R.id.viewPager_item_image);
        mTextView = (TextView) view.findViewById(R.id.item_desc);
        return view;
    }

    @Override
    public void onBind(Context context, int position, DataEntry data) {
        if (!TextUtils.isEmpty(data.desc)) {
            mTextView.setText(data.desc);
        }
        Glide.with(context).load(data.imageUrl).into(mImageView);

    }
}
