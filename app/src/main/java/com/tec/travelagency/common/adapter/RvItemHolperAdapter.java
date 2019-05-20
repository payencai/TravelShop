package com.tec.travelagency.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tec.travelagency.R;

import java.util.Collections;
import java.util.List;

/**
 * 作者：凌涛 on 2018/8/8 16:21
 * 邮箱：771548229@qq..com
 */
public class RvItemHolperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {

    //数据
    private Context context;
    private List<String> mData;
    private LayoutInflater inflater;

    public RvItemHolperAdapter(Context context, List<String> data) {
        this.context = context;
        mData = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        String user = mData.get(position);
        myViewHolder.tv.setText(user);
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //交换位置
        Collections.swap(mData, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);

    }

    @Override
    public void onItemDissmiss(int position) {
        //移除数据
        mData.remove(position);
        notifyItemRemoved(position);

    }


}
