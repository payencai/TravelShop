package com.tec.travelagency.home.adapter.ticket;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tec.travelagency.R;
import com.tec.travelagency.home.entity.MeetDelBean;
import com.tec.travelagency.home.entity.MeetingBean;
import com.tec.travelagency.home.entity.test.TestTicket;

import java.util.List;

/**
 * 作者：凌涛 on 2018/11/2 11:37
 * 邮箱：771548229@qq..com
 */
public class TicketPlatAdapter  extends BaseQuickAdapter<TestTicket,BaseViewHolder> {

    public TicketPlatAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, TestTicket testTicket) {
        //View view=LayoutInflater.from(baseViewHolder.itemView.getContext()).inflate(R.layout.item_ticket,null);
    }
}
