package com.tec.travelagency.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tec.travelagency.R;
import com.tec.travelagency.home.activity.HotelDetailActivity;
import com.tec.travelagency.home.entity.DetailsIntroduceBean;

import java.util.List;

/**
 * 作者：凌涛 on 2018/9/5 15:38
 * 邮箱：771548229@qq..com
 */
public class DetailsGvAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<DetailsIntroduceBean> list;

    public DetailsGvAdapter(Context context, List<DetailsIntroduceBean> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_gv_details, parent, false);
            holder = new ViewHolder();
            holder.key = (TextView) convertView.findViewById(R.id.key);
            holder.value = (TextView) convertView.findViewById(R.id.value);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.key.setText(list.get(position).key);
        holder.value.setText(list.get(position).value);
        return convertView;
    }

    class ViewHolder {
        TextView key;
        TextView value;
    }
}
