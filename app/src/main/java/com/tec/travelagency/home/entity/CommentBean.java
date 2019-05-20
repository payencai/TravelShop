package com.tec.travelagency.home.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tec.travelagency.R;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseCell;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.widget.StarLinearLayout;

/**
 * 作者：凌涛 on 2018/9/12 14:26
 * 邮箱：771548229@qq..com
 */
public class CommentBean extends RVBaseCell {

    private String id;
    private String name;
    private String image;
    private String homeType;
    private double score;
    private String content;
    private String hotelId;
    private String orderId;
    private int state;
    private String createTime;

    public CommentBean() {
        super(null);
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_rv_layout, parent, false);
        return new RVBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position, Context context, RVBaseAdapter adapter) {
        holder.setText(R.id.name, name);
        ImageView imageView = holder.getImageView(R.id.headerImage);
        Glide.with(context).load(image).into(imageView);
        StarLinearLayout star = (StarLinearLayout) holder.getView(R.id.Star);
        star.setScore((float) score);
        holder.setText(R.id.content, content);
        holder.setText(R.id.time, createTime.split(" ")[0]);
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

    public String getHomeType() {
        return homeType;
    }

    public void setHomeType(String homeType) {
        this.homeType = homeType;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
