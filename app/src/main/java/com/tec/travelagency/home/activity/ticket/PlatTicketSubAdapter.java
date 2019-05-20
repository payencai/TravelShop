package com.tec.travelagency.home.activity.ticket;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.tec.travelagency.R;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.home.rvItemCallBack.RvItemActionListener;

import java.util.List;

/**
 * 作者：凌涛 on 2018/11/17 19:08
 * 邮箱：771548229@qq..com
 */
public class PlatTicketSubAdapter extends RecyclerView.Adapter<RVBaseViewHolder> {
    private List<PlatTicketSubBean> list;
    private Context context;

    public PlatTicketSubAdapter(Context context, List<PlatTicketSubBean> list) {
        this.list = list;
        this.context = context;
    }


    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plat_detail, parent, false);
        return new RVBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, final int position) {
        PlatTicketSubBean bean = list.get(position);
        loadIntoUseFitWidth(holder.getItemView().getContext(),bean.getImage1Url(),holder.getImageView(R.id.head));
        TextView sale= (TextView) holder.getView(R.id.plat2);
        TextView plat= (TextView) holder.getView(R.id.plat);
        TextView name= (TextView) holder.getView(R.id.name);
        TextView isadd= (TextView) holder.getView(R.id.isadd);
        TextView saleout= (TextView) holder.getView(R.id.sale);
        TextView label1= (TextView) holder.getView(R.id.label1);
        TextView label2= (TextView) holder.getView(R.id.label2);
        if (bean.getOneDayValidity().equals("1")) {
            label2.setText("当日有效");
        } else {
            label2.setVisibility(View.GONE);
        }
        if(bean.getIsCanCancel().equals("1")) {
            label1.setText("可退");
        } else {
            label1.setText("不可退");
        }
        saleout.setText("已售"+bean.getSoldOutAmount());
        sale.setText("平台价: ￥"+bean.getPlatformPrice());
        plat.setText("售价: ￥"+bean.getTravelAgencyPrice());
        name.setText(bean.getName());
        if(bean.getIsAdopted().equals("1")){
            isadd.setVisibility(View.VISIBLE);
            plat.setVisibility(View.VISIBLE);
        }else{
            isadd.setVisibility(View.GONE);
        }
        holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemActionListener != null) {
                    mOnItemActionListener.OnItemClick(position);
                }
            }
        });

    }
    public static void loadIntoUseFitWidth(Context context, final String imageUrl, final ImageView imageView) {
        final RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.test_img);
        Glide.with(context)
                .load(imageUrl)
                .apply(requestOptions)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(imageView);

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

