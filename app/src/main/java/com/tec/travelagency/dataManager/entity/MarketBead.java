package com.tec.travelagency.dataManager.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tec.travelagency.R;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseCell;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;

/**
 * 作者：凌涛 on 2018/8/9 17:21
 * 邮箱：771548229@qq..com
 */
public class MarketBead extends RVBaseCell {

    private String name;
    private String hotelNum = "0";
    private String doorTicketNum = "0";
    private String rentCarNum = "0";
    private String pathNum = "0";
    private String categoryId;


    public MarketBead() {
        super(null);
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_market_rv_layout, parent, false);
        return new RVBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position, Context context, RVBaseAdapter adapter) {
        holder.setText(R.id.name, name);
        holder.setText(R.id.hotel, hotelNum);
        holder.setText(R.id.dooricket, doorTicketNum);
        holder.setText(R.id.rentCar, rentCarNum);
        holder.setText(R.id.path, pathNum);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHotelNum() {
        return hotelNum;
    }

    public void setHotelNum(String hotelNum) {
        this.hotelNum = hotelNum;
    }

    public String getDoorTicketNum() {
        return doorTicketNum;
    }

    public void setDoorTicketNum(String doorTicketNum) {
        this.doorTicketNum = doorTicketNum;
    }

    public String getRentCarNum() {
        return rentCarNum;
    }

    public void setRentCarNum(String rentCarNum) {
        this.rentCarNum = rentCarNum;
    }

    public String getPathNum() {
        return pathNum;
    }

    public void setPathNum(String pathNum) {
        this.pathNum = pathNum;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
