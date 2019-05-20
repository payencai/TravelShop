package com.tec.travelagency.home.entity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tec.travelagency.R;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseCell;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.home.activity.DoorTicketDetailActivity;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/8/9 16:07
 * 邮箱：771548229@qq..com
 */
public class DoorTicket extends RVBaseCell implements Serializable {


    private String id;
    private String name;
    private String type;
    private String typeName;
    private double price;
    private String isCanCancel;
    private String oneDayValidity;
    private String scenicSpotId;
    private String travelAgencyId;
    private String isDeleted;
    private String createTime;
    private String sequence;
    private ScenicBean mScenicBean;

    public DoorTicket() {
        super(null);
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_door_ticket_rv_layout, parent, false);
        return new RVBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position, final Context context, RVBaseAdapter adapter) {
        holder.setText(R.id.name, name);
        holder.setText(R.id.tel, typeName);
        holder.setText(R.id.price, "¥" + price + "");
        String result = "";
        if (isCanCancel.equals("1")) {
            result = "可退 | ";
        } else {
            result = "不可退 | ";
        }
        if (oneDayValidity.equals("1")) {
            result += "随买随用，当日有效";
        }
        holder.setText(R.id.label, result);

        holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DoorTicketDetailActivity.class);
                intent.putExtra("doorTicket", DoorTicket.this);
                context.startActivity(intent);
            }
        });
    }

    public ScenicBean getScenicBean() {
        return mScenicBean;
    }

    public void setScenicBean(ScenicBean scenicBean) {
        mScenicBean = scenicBean;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIsCanCancel() {
        return isCanCancel;
    }

    public void setIsCanCancel(String isCanCancel) {
        this.isCanCancel = isCanCancel;
    }

    public String getOneDayValidity() {
        return oneDayValidity;
    }

    public void setOneDayValidity(String oneDayValidity) {
        this.oneDayValidity = oneDayValidity;
    }

    public String getScenicSpotId() {
        return scenicSpotId;
    }

    public void setScenicSpotId(String scenicSpotId) {
        this.scenicSpotId = scenicSpotId;
    }

    public String getTravelAgencyId() {
        return travelAgencyId;
    }

    public void setTravelAgencyId(String travelAgencyId) {
        this.travelAgencyId = travelAgencyId;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
