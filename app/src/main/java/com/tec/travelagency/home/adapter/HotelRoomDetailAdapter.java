package com.tec.travelagency.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tec.travelagency.R;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.home.entity.HotelNew2Bean;
import com.tec.travelagency.home.entity.HotelRoomDetail;
import com.tec.travelagency.home.rvItemCallBack.RvItemActionListener;

import java.util.List;

/**
 * 酒店主界面的Rv  Adapter
 */
public class HotelRoomDetailAdapter extends RecyclerView.Adapter<RVBaseViewHolder> {
    private List<HotelRoomDetail> list;
    private Context context;

    public HotelRoomDetailAdapter(Context context, List<HotelRoomDetail> list) {
        this.list = list;
        this.context = context;
    }


    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotel_room_rv_layout, parent, false);
        return new RVBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, final int position) {
        HotelRoomDetail bean = list.get(position);

        Glide.with(holder.getItemView().getContext()).load(bean.getImage1Url()).into(holder.getImageView(R.id.hotelHead));
        holder.setText(R.id.name, bean.getName());

        SpannableString platformPrice = new SpannableString("平台价: ￥" + bean.getManagePrice());
        int color = context.getResources().getColor(R.color.price_text_color);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        platformPrice.setSpan(span, 4, platformPrice.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        holder.setText(R.id.address, platformPrice);

        TextView sellPriceText = holder.getTextView(R.id.sellPrice);
        TextView addRoomTypeBtn = holder.getTextView(R.id.addRoomTypeBtn);
        String isAdopted = bean.getIsAdopted();
        if (isAdopted.equals("1")) {
            sellPriceText.setVisibility(View.VISIBLE);
            addRoomTypeBtn.setText("已上架");
        } else {
            sellPriceText.setVisibility(View.GONE);
            addRoomTypeBtn.setText("");
        }
        SpannableString sellPriceStr = new SpannableString("售价: ￥" + bean.getTravelAgencyPrice());
        sellPriceStr.setSpan(span, 4, sellPriceStr.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        sellPriceText.setText(sellPriceStr);

        holder.setText(R.id.label1, bean.getName());
        holder.setText(R.id.label2, bean.getBathroom());

        holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemActionListener != null) {
                    mOnItemActionListener.OnItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private RvItemActionListener mOnItemActionListener;

    public void setOnItemActionListener(RvItemActionListener onItemActionListener) {
        mOnItemActionListener = onItemActionListener;
    }

}
