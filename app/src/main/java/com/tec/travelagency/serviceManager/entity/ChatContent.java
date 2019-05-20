package com.tec.travelagency.serviceManager.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tec.travelagency.R;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseCell;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;

/**
 * 作者：凌涛 on 2018/8/9 11:27
 * 邮箱：771548229@qq..com
 */
public class ChatContent extends RVBaseCell {

    private String content;
    private int itemType;


    public ChatContent(String content, int itemType) {
        super(null);
        this.content = content;
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (itemType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_left_rv_layout, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_right_rv_layout, parent, false);
        }
        return new RVBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position, Context context, RVBaseAdapter adapter) {

        holder.setText(R.id.content, content);

    }
}
