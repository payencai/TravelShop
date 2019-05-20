package com.tec.travelagency.orderManager.fragment.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tec.travelagency.R;

import java.util.List;

/**
 * 作者：凌涛 on 2018/11/26 16:38
 * 邮箱：771548229@qq..com
 */
public class RefundAdapter extends BaseAdapter {
    Context  mContext;
    List<Refund> mRefunds;

    public RefundAdapter(Context context, List<Refund> refunds) {
        mContext = context;
        mRefunds = refunds;
    }
    @Override
    public int getCount() {
        return mRefunds.size();
    }

    @Override
    public Object getItem(int position) {
        return mRefunds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Refund refund=mRefunds.get(position);
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_refund,null);
        TextView applyCancelSucceedTime=view.findViewById(R.id.applyCancelSucceedTime);
        TextView type=view.findViewById(R.id.type);
        LinearLayout cancelReasonLayout=view.findViewById(R.id.cancelReasonLayout);
        TextView cancelReasonText=view.findViewById(R.id.cancelReasonText);
        if(refund.getStatus().equals("3")){
            applyCancelSucceedTime.setText(refund.getDisposeTime());
        }else{
            if(refund.getStatus().equals("1")){
                type.setText("申请取消");
                applyCancelSucceedTime.setText(refund.getCreateTime());
                cancelReasonLayout.setVisibility(View.VISIBLE);
                cancelReasonText.setText(refund.getRefundReason());
            }else{
                applyCancelSucceedTime.setText(refund.getDisposeTime());
                type.setText("取消成功");
            }
        }
        return view;
    }
}
