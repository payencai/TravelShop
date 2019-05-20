package com.tec.travelagency.home.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tec.travelagency.R;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseCell;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;

/**
 * 作者：凌涛 on 2018/9/6 14:11
 * 邮箱：771548229@qq..com
 */
public class DateSelectBean extends RVBaseCell {

    private String id;
    private String routeId;
    private String day;

    private double price;
    private boolean isChoose;
    private String dateString;
    private String goOffString;




    public DateSelectBean() {
        super(null);
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date_select_rv_layout, parent, false);
        return new RVBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position, Context context, RVBaseAdapter adapter) {

        //@drawable/shape_rectangle_upper_harf
        //shape_border_apptheme_bg

        RelativeLayout itemView = (RelativeLayout) holder.getView(R.id.item);
        View view = holder.getView(R.id.view1);

        TextView priceTV = holder.getTextView(R.id.price);
        TextView dateTV = holder.getTextView(R.id.date);
        TextView goOffTV = holder.getTextView(R.id.go_off_time);
        int whiteColor = context.getResources().getColor(R.color.white);
        int test33Color = context.getResources().getColor(R.color.color_333);
        int appThemeColor = context.getResources().getColor(R.color.app_theme_color);

        if (isChoose) {
            itemView.setBackgroundResource(R.drawable.shape_rectangle_upper_harf);
            view.setBackgroundColor(whiteColor);
            dateTV.setTextColor(whiteColor);
            goOffTV.setTextColor(whiteColor);
            priceTV.setTextColor(whiteColor);
        } else {
            itemView.setBackgroundResource(R.drawable.shape_border_apptheme_bg);
            view.setBackgroundColor(appThemeColor);
            dateTV.setTextColor(test33Color);
            goOffTV.setTextColor(test33Color);
            priceTV.setTextColor(appThemeColor);
        }

        priceTV.setText("¥" + price);
        dateTV.setText(dateString);
        goOffTV.setText(goOffString);



    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGoOffString() {
        return goOffString;
    }

    public void setGoOffString(String goOffString) {
        this.goOffString = goOffString;
    }
}
