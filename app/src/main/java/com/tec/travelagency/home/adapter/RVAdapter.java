package com.tec.travelagency.home.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.common.myviewpager.CommonViewPager;
import com.tec.travelagency.common.myviewpager.ViewPagerHolderCreator;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.home.activity.HotelManagerNewActivity;
import com.tec.travelagency.home.entity.AddedHotelBean;
import com.tec.travelagency.home.entity.DataEntry;
import com.tec.travelagency.home.entity.HotelNew2Bean;
import com.tec.travelagency.home.rvItemCallBack.RvItemActionListener;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.widget.StarLinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 酒店主界面的Rv  Adapter
 */
public class RVAdapter extends RecyclerView.Adapter<RVBaseViewHolder> {
    private List<HotelNew2Bean> list;
    private Context context;

    private MyHandler mHandler;

    public RVAdapter(Context context, List<HotelNew2Bean> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.other_banner_layout, parent, false);
        }
        RVBaseViewHolder holder = new RVBaseViewHolder(view);
        return holder;
    }

    public void restart() {
        if (mHandler == null) {
            return;
        }
//        if (mHandler.hasMessages(0)) {
//            mHandler.removeMessages(0);
//        }
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, final int position) {
        Context context = holder.getItemView().getContext();
        if (getItemViewType(position) == 0) {
            ImageView imageView = holder.getImageView(R.id.hotelHead);
            final HotelNew2Bean bean = list.get(position);
            Glide.with(context).load(bean.getImage1Url()).into(imageView);
            holder.setText(R.id.name, bean.getName());
            holder.setText(R.id.address, bean.getProvince() + bean.getCity() + bean.getDistrict() + bean.getAddressDetail());
//        holder.setText(R.id.telephone, "电话:"+bean.getContactNumber());
            holder.setText(R.id.name, bean.getName());
            holder.setText(R.id.tv_min,"￥"+bean.getBottomPrice()+"起");
            StarLinearLayout starLinearLayout = (StarLinearLayout) holder.getView(R.id.star);

            starLinearLayout.setScore((float) bean.getCommentData().getScore());
            TextView score = holder.getTextView(R.id.score);
            TextView evaluate = holder.getTextView(R.id.evaluate);
            score.setText(bean.getCommentData().getScore() + "分");
            evaluate.setText(bean.getCommentData().getNumber() + "条评论");
            holder.getTextView(R.id.hotel_des).setText(bean.getOneWord());

            holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemActionListener != null) {
                        mOnItemActionListener.OnItemClick(position);
                    }
                }
            });
            TextView tv_star = (TextView) holder.getView(R.id.tv_star);
            String star = bean.getHotelRating();
            tv_star.setText(star);
            TextView putaway = holder.getTextView(R.id.isPutaway);
            if (bean.isAdd()) {
                putaway.setVisibility(View.VISIBLE);
            } else {
                putaway.setVisibility(View.GONE);
            }

            ImageView callImg = holder.getImageView(R.id.callImg);
            if (!TextUtils.isEmpty(bean.getContactNumber())) {
                callImg.setVisibility(View.VISIBLE);
            }
            callImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mICallTel != null) {
                        mICallTel.call(bean.contactNumber, v);
                    }
                }
            });
            callImg.setVisibility(View.GONE);
        } else {
            ViewPager homeBannerVp = (ViewPager) holder.getView(R.id.home_banner);
            // 设置数据
            requestViewpagerImg(context, homeBannerVp);

        }

    }

    private void requestViewpagerImg(Context context, final ViewPager homeBannerVp) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", "2");
        HttpProxy.obtain().post(PlatformContans.Banner.getList, params, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("type5", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    if (resultCode == 0) {
                        JSONArray data = object.getJSONArray("data");
                        int length = data.length();
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < length; i++) {
                            JSONObject item = data.getJSONObject(i);
                            String imageUrl = item.getString("imageUrl");
//                            list.add(new DataEntry(imageUrl));
                            list.add(imageUrl);
                        }
                        setViewPagerView(list, homeBannerVp);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {

            }
        });
    }

    public void setViewPagerView(final List<String> mVPImagerUrlList, final ViewPager homeBannerVp) {
        HotelHeadImgAdapter hotelHeadImgAdapter = new HotelHeadImgAdapter(context, mVPImagerUrlList);
        hotelHeadImgAdapter.setOnClickImageListener(new HotelHeadImgAdapter.OnClickImageListener() {
            @Override
            public void onClick(Context context) {

            }
        });
        homeBannerVp.setAdapter(hotelHeadImgAdapter);
        homeBannerVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mHandler = new MyHandler(homeBannerVp);
        mHandler.sendEmptyMessage(0);

    }


    @Override
    public int getItemViewType(int position) {
        return list.get(position).itemType;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private ICallTel mICallTel;

    public void setICallTel(ICallTel ICallTel) {
        mICallTel = ICallTel;
    }

    public interface ICallTel {
        public void call(String number, View view);
    }

    private RvItemActionListener mOnItemActionListener;

    public void setOnItemActionListener(RvItemActionListener onItemActionListener) {
        mOnItemActionListener = onItemActionListener;
    }

    public static class MyHandler extends Handler {

        private WeakReference<ViewPager> viewpager;

        public MyHandler(ViewPager viewpager) {
            this.viewpager = new WeakReference<>(viewpager);
        }

        @Override
        public void handleMessage(Message msg) {
            ViewPager viewPager = viewpager.get();
            if (viewPager == null) {
                return;
            }

            switch (msg.what) {
                case 0:
                    int item = viewPager.getCurrentItem() + 1;
                    viewPager.setCurrentItem(item % viewPager.getAdapter().getCount());
                    sendEmptyMessageDelayed(0, 3000);
                    break;

            }
        }
    }

}
