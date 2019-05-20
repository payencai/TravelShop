package com.tec.travelagency.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tec.travelagency.R;

import java.util.List;

/**
 * 作者：凌涛 on 2018/8/8 17:55
 * 邮箱：771548229@qq..com
 */
public class HotelHeadImgAdapter extends PagerAdapter {

    private Context context;
    private List<String> mList;

    public HotelHeadImgAdapter( Context context,List<String> list) {
        this.context = context;
        mList = list;
    }
    public OnClickImageListener mOnClickImageListener;
    public  interface OnClickImageListener{
        void onClick(Context context);
    }
    public  void setOnClickImageListener(OnClickImageListener mOnClickImageListener){
        this.mOnClickImageListener=mOnClickImageListener;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView iv = new ImageView(context);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickImageListener.onClick(context);
            }
        });
        String imgUrl = mList.get(position);
//        iv.setImageResource(imgs[position % imgs.length]);
        if (position % 2 == 0) {
            Picasso.with(context).load(imgUrl).placeholder(R.drawable.p001).error(R.drawable.p001).into(iv);
        } else {
            Picasso.with(context).load(imgUrl).placeholder(R.drawable.p002).error(R.drawable.p002).into(iv);
        }
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
