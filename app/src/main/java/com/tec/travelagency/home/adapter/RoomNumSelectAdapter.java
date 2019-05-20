package com.tec.travelagency.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tec.travelagency.R;
import com.tec.travelagency.home.entity.RoomNumBean;

import java.util.List;

/**
 * 作者：凌涛 on 2018/9/5 16:32
 * 邮箱：771548229@qq..com
 */
public class RoomNumSelectAdapter extends BaseAdapter {

    private Context mContext;
    private List<RoomNumBean> mList;
    private LayoutInflater mInflater;
    private ISelectRoomNum mSelectRoomNum;

    public RoomNumSelectAdapter(Context context, List<RoomNumBean> list,ISelectRoomNum mSelectRoomNum) {
        mContext = context;
        mList = list;
        this.mSelectRoomNum = mSelectRoomNum;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHodler hodler;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_room_number_select_gv_layout, parent, false);
            hodler = new ViewHodler();
            hodler.mTextView = convertView.findViewById(R.id.number);
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }
        RoomNumBean roomNumBean = mList.get(position);
        hodler.mTextView.setText(roomNumBean.number + "");

        if (roomNumBean.isChoose) {
            hodler.mTextView.setSelected(true);
            hodler.mTextView.setBackgroundResource(R.drawable.shape_circle_orange);
        } else {
            hodler.mTextView.setSelected(false);
            hodler.mTextView.setBackgroundResource(R.drawable.shape_order_days_bg);
        }

        final int finalPosition = position;
        hodler.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (RoomNumBean numBean : mList) {
                    numBean.isChoose = false;
                }

                RoomNumBean numBean = mList.get(finalPosition);
                if (numBean.isChoose) {
                    return;
                } else {
                    numBean.isChoose = true;
                }
                notifyDataSetChanged();
                mSelectRoomNum.selectNum(numBean.number);
            }
        });

        return convertView;
    }

    class ViewHodler {
        TextView mTextView;
    }


    public interface ISelectRoomNum {
        public void selectNum(int number);
    }

}
