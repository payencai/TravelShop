package com.tec.travelagency.home.activity.ticket;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tec.travelagency.R;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.home.entity.MeetingBean;
import com.tec.travelagency.home.rvItemCallBack.RvItemActionListener;

import java.util.List;

/**
 * 作者：凌涛 on 2018/11/17 17:36
 * 邮箱：771548229@qq..com
 */
public class SaleTicketSubAdapter extends RecyclerView.Adapter<RVBaseViewHolder> {
    private List<SaleTicketsDetailBean> list;
    private Context context;

    public SaleTicketSubAdapter(Context context, List<SaleTicketsDetailBean> list) {
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
        SaleTicketsDetailBean bean = list.get(position);
        Glide.with(holder.getItemView().getContext()).load(bean.getImage1Url()).into(holder.getImageView(R.id.head));
        //TextView adapot= (TextView) holder.getView(R.id.adapot);
        TextView plat= (TextView) holder.getView(R.id.plat2);
        TextView sale= (TextView) holder.getView(R.id.sale);
        TextView label1= (TextView) holder.getView(R.id.label1);
        TextView label2= (TextView) holder.getView(R.id.label2);
        TextView isadd=(TextView) holder.getView(R.id.isadd);
        label2.setText(bean.getPeriodOfValidity());
        if(bean.getIsUsed().equals("2")){
            isadd.setVisibility(View.GONE);
        }else{
            isadd.setVisibility(View.VISIBLE);
        }
        if (bean.getOneDayValidity().equals("1")) {
            label2.setText("当日有效");
        } else {
            label2.setVisibility(View.GONE);
        }
        if (bean.getIsCanCancel().equals("1")) {
            label1.setText("可退");
        } else {
            label1.setText("不可退");
        }
        TextView name= (TextView) holder.getView(R.id.name);
        name.setText(bean.getName());
        plat.setText("售价: ￥"+bean.getPublishPrice()+"");
        sale.setText("已售"+bean.getSoldOutAmount()+"");

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

