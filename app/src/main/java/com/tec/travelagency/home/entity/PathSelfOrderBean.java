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
import com.tec.travelagency.home.activity.PathSelfOrderDetailActivity;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/9/6 10:41
 * 邮箱：771548229@qq..com
 */
public class PathSelfOrderBean extends RVBaseCell implements Serializable {


    private String id;
    private String name;
    private String image;
    private String imageUrl;
    private String travelAgencyId;
    private double price;
    private String isCanCancel;
    private String cityBegin;
    private String routeUsedTime;
    private String reservationSpecification;
    private String trafficSpecification;
    private String ticketSpecification;
    private String hotelSpecification;
    private String isDeleted;
    private String createTime;
    private CommentDataBean commentData;

    public PathSelfOrderBean() {
        super(null);
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_path_self_order_layout, parent, false);
        return new RVBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position, final Context context, RVBaseAdapter adapter) {

        holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PathSelfOrderDetailActivity.class);
                intent.putExtra("PathSelfOrderBean", PathSelfOrderBean.this);
                context.startActivity(intent);
            }
        });

        holder.setText(R.id.name, name);
        holder.setText(R.id.price, "¥" + price + " 起");
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTravelAgencyId() {
        return travelAgencyId;
    }

    public void setTravelAgencyId(String travelAgencyId) {
        this.travelAgencyId = travelAgencyId;
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

    public String getCityBegin() {
        return cityBegin;
    }

    public void setCityBegin(String cityBegin) {
        this.cityBegin = cityBegin;
    }

    public String getRouteUsedTime() {
        return routeUsedTime;
    }

    public void setRouteUsedTime(String routeUsedTime) {
        this.routeUsedTime = routeUsedTime;
    }

    public String getReservationSpecification() {
        return reservationSpecification;
    }

    public void setReservationSpecification(String reservationSpecification) {
        this.reservationSpecification = reservationSpecification;
    }

    public String getTrafficSpecification() {
        return trafficSpecification;
    }

    public void setTrafficSpecification(String trafficSpecification) {
        this.trafficSpecification = trafficSpecification;
    }

    public String getTicketSpecification() {
        return ticketSpecification;
    }

    public void setTicketSpecification(String ticketSpecification) {
        this.ticketSpecification = ticketSpecification;
    }

    public String getHotelSpecification() {
        return hotelSpecification;
    }

    public void setHotelSpecification(String hotelSpecification) {
        this.hotelSpecification = hotelSpecification;
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

    public CommentDataBean getCommentData() {
        return commentData;
    }

    public void setCommentData(CommentDataBean commentData) {
        this.commentData = commentData;
    }

    public static class CommentDataBean implements Serializable {
        /**
         * score : 5
         * number : 0
         * rate : 1
         */

        private double score;
        private int number;
        private double rate;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }
    }
}
