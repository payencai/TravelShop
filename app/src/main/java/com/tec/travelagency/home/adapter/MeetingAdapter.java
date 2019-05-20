package com.tec.travelagency.home.adapter;

import android.content.Context;
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
import com.tec.travelagency.home.entity.HotelRoomDetail;
import com.tec.travelagency.home.entity.MeetingBean;
import com.tec.travelagency.home.rvItemCallBack.RvItemActionListener;

import java.util.List;

public class MeetingAdapter extends RecyclerView.Adapter<RVBaseViewHolder> {
    private List<MeetingBean> list;
    private Context context;

    public MeetingAdapter(Context context, List<MeetingBean> list) {
        this.list = list;
        this.context = context;
    }


    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meeting_type, parent, false);
        return new RVBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, final int position) {
        MeetingBean bean = list.get(position);

        Glide.with(holder.getItemView().getContext()).load(bean.getImage1Url()).into(holder.getImageView(R.id.head));
        TextView adapot= (TextView) holder.getView(R.id.adapot);
        TextView plat= (TextView) holder.getView(R.id.plat);
        TextView sale= (TextView) holder.getView(R.id.sale);
        TextView name= (TextView) holder.getView(R.id.name);
        name.setText(bean.getName());
        sale.setText("售价: ￥"+bean.getTravelAgencyPrice()+"");
        plat.setText("平台价: ￥"+bean.getPlatformPrice()+"");
        String isAdopted = bean.getIsAdopted();
        if (isAdopted.equals("1")) {

        } else {
           adapot.setVisibility(View.GONE);
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    private RvItemActionListener mOnItemActionListener;

    public void setOnItemActionListener(RvItemActionListener onItemActionListener) {
        mOnItemActionListener = onItemActionListener;
    }

}
