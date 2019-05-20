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
import com.tec.travelagency.home.activity.WriteDoorTickerInfoActivity;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/9/6 17:58
 * 邮箱：771548229@qq..com
 */
public class AdultTickerBean extends RVBaseCell implements Serializable{
    

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
    private CommentDataBean commentData;

    public AdultTickerBean() {
        super(null);
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adult_ticker_self_rv_layout, parent, false);
        return new RVBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position, final Context context, RVBaseAdapter adapter) {

        holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WriteDoorTickerInfoActivity.class);
                intent.putExtra("AdultTickerBean", AdultTickerBean.this);
                context.startActivity(intent);
            }
        });
        holder.setText(R.id.name, name);
        holder.setText(R.id.cancel, isCanCancel.equals("否") ? "不可取消" : "可取消");
        holder.setText(R.id.price, "¥ " + price);
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
