package com.tec.travelagency.orderManager.entity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tec.travelagency.R;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseCell;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.home.activity.rent.CarDetailActivity;
import com.tec.travelagency.orderManager.fragment.detail.HotelOrderDelActivity;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/11/19 15:41
 * 邮箱：771548229@qq..com
 */
public class LxsHotelOrder extends RVBaseCell implements Serializable {

    /**
     * amount : 0
     * bathroom : string
     * bedNum : string
     * bedType : string
     * breakfast : string
     * checkInPerson : string
     * createTime : 2018-11-19T07:40:22.278Z
     * days : 0
     * descr : string
     * finishedTime : 2018-11-19T07:40:22.278Z
     * finishedType : string
     * firstDate : 2018-11-19T07:40:22.278Z
     * floor : string
     * hotelAmount : 0
     * hotelId : string
     * hotelName : string
     * hotelPrice : 0
     * id : string
     * image1 : string
     * intnet : string
     * isCanCancel : string
     * isComment : string
     * isDeleted : string
     * isInner : string
     * isUseCoupon : string
     * lastDate : 2018-11-19T07:40:22.278Z
     * liveNum : string
     * managePrice : 0
     * modeOfPayment : string
     * name : string
     * nickname : string
     * orderNum : string
     * orderRoomNum : 0
     * paymentTime : 2018-11-19T07:40:22.278Z
     * peopleNum : 0
     * platformAmount : 0
     * price : 0
     * productNum : string
     * refundTime : 2018-11-19T07:40:22.278Z
     * regulation : string
     * roomtypeId : string
     * roomtypeName : string
     * status : string
     * telephoneNum : string
     * travelAgencyId : string
     * travelAgencyName : string
     * travelAgencyRoomtypeId : string
     * updateTime : 2018-11-19T07:40:22.278Z
     * userId : string
     * window : string
     */

    private double amount;
    private String bathroom;
    private String bedNum;
    private String bedType;
    private String breakfast;
    private String checkInPerson;
    private String createTime;
    private int days;
    private String descr;
    private String finishedTime;
    private String finishedType;
    private String firstDate;
    private String floor;
    private double hotelAmount;
    private String hotelId;
    private String hotelName;
    private double hotelPrice;
    private String id;
    private String image1;
    private String intnet;
    private String isCanCancel;
    private String isComment;
    private String isDeleted;
    private String isInner;
    private String isUseCoupon;
    private String lastDate;
    private String liveNum;
    private double managePrice;
    private String modeOfPayment;
    private String name;
    private String nickname;
    private String orderNum;
    private int orderRoomNum;
    private String paymentTime;
    private int peopleNum;
    private double platformAmount;
    private double price;
    private String productNum;
    private String refundTime;
    private String regulation;
    private String roomtypeId;
    private String roomtypeName;
    private String status;
    private String telephoneNum;
    private String travelAgencyId;
    private String travelAgencyName;
    private String travelAgencyRoomtypeId;
    private String updateTime;
    private String userId;
    private String window;

    public LxsHotelOrder(Object o) {
        super(o);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getBathroom() {
        return bathroom;
    }

    public void setBathroom(String bathroom) {
        this.bathroom = bathroom;
    }

    public String getBedNum() {
        return bedNum;
    }

    public void setBedNum(String bedNum) {
        this.bedNum = bedNum;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getCheckInPerson() {
        return checkInPerson;
    }

    public void setCheckInPerson(String checkInPerson) {
        this.checkInPerson = checkInPerson;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(String finishedTime) {
        this.finishedTime = finishedTime;
    }

    public String getFinishedType() {
        return finishedType;
    }

    public void setFinishedType(String finishedType) {
        this.finishedType = finishedType;
    }

    public String getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(String firstDate) {
        this.firstDate = firstDate;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public double getHotelAmount() {
        return hotelAmount;
    }

    public void setHotelAmount(double hotelAmount) {
        this.hotelAmount = hotelAmount;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public double getHotelPrice() {
        return hotelPrice;
    }

    public void setHotelPrice(double hotelPrice) {
        this.hotelPrice = hotelPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getIntnet() {
        return intnet;
    }

    public void setIntnet(String intnet) {
        this.intnet = intnet;
    }

    public String getIsCanCancel() {
        return isCanCancel;
    }

    public void setIsCanCancel(String isCanCancel) {
        this.isCanCancel = isCanCancel;
    }

    public String getIsComment() {
        return isComment;
    }

    public void setIsComment(String isComment) {
        this.isComment = isComment;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getIsInner() {
        return isInner;
    }

    public void setIsInner(String isInner) {
        this.isInner = isInner;
    }

    public String getIsUseCoupon() {
        return isUseCoupon;
    }

    public void setIsUseCoupon(String isUseCoupon) {
        this.isUseCoupon = isUseCoupon;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getLiveNum() {
        return liveNum;
    }

    public void setLiveNum(String liveNum) {
        this.liveNum = liveNum;
    }

    public double getManagePrice() {
        return managePrice;
    }

    public void setManagePrice(double managePrice) {
        this.managePrice = managePrice;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getOrderRoomNum() {
        return orderRoomNum;
    }

    public void setOrderRoomNum(int orderRoomNum) {
        this.orderRoomNum = orderRoomNum;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
    }

    public double getPlatformAmount() {
        return platformAmount;
    }

    public void setPlatformAmount(double platformAmount) {
        this.platformAmount = platformAmount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(String refundTime) {
        this.refundTime = refundTime;
    }

    public String getRegulation() {
        return regulation;
    }

    public void setRegulation(String regulation) {
        this.regulation = regulation;
    }

    public String getRoomtypeId() {
        return roomtypeId;
    }

    public void setRoomtypeId(String roomtypeId) {
        this.roomtypeId = roomtypeId;
    }

    public String getRoomtypeName() {
        return roomtypeName;
    }

    public void setRoomtypeName(String roomtypeName) {
        this.roomtypeName = roomtypeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTelephoneNum() {
        return telephoneNum;
    }

    public void setTelephoneNum(String telephoneNum) {
        this.telephoneNum = telephoneNum;
    }

    public String getTravelAgencyId() {
        return travelAgencyId;
    }

    public void setTravelAgencyId(String travelAgencyId) {
        this.travelAgencyId = travelAgencyId;
    }

    public String getTravelAgencyName() {
        return travelAgencyName;
    }

    public void setTravelAgencyName(String travelAgencyName) {
        this.travelAgencyName = travelAgencyName;
    }

    public String getTravelAgencyRoomtypeId() {
        return travelAgencyRoomtypeId;
    }

    public void setTravelAgencyRoomtypeId(String travelAgencyRoomtypeId) {
        this.travelAgencyRoomtypeId = travelAgencyRoomtypeId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWindow() {
        return window;
    }

    public void setWindow(String window) {
        this.window = window;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
//        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_paid_order_rv_layout2, parent, false);
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_car, parent, false);
        return new RVBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position, final Context context, RVBaseAdapter adapter) {
        holder.setText(R.id.number,orderNum+"");
        holder.setText(R.id.tv_amount,"￥"+amount);
        holder.setText(R.id.time,"下单时间: "+createTime);
        holder.setText(R.id.visi_time,firstDate.substring(0,10)+"----"+lastDate.substring(0,10) +"   "+days+"晚");
        holder.setText(R.id.title1,hotelName);
        holder.setText(R.id.title2,roomtypeName);
        holder.setText(R.id.contact,checkInPerson);
        holder.setText(R.id.phone,telephoneNum);
        holder.setText(R.id.tv_name,"酒店");
        holder.setText(R.id.num,"x"+orderRoomNum);
        holder.getView(R.id.title1).setVisibility(View.VISIBLE);
        holder.getTextView(R.id.tv_name).setTextColor(context.getResources().getColor(R.color.order_hotel));
        holder.getTextView(R.id.tv_name).setBackgroundResource(R.drawable.shape_order_hotel);
        switch (status){
            case "1":
                holder.setText(R.id.tv_status,"待消费");
                holder.getTextView(R.id.tv_status).setTextColor(context.getResources().getColor(R.color.status_nouse));
                break;
            case "2":
                holder.setText(R.id.tv_status,"申请取消");
                holder.getTextView(R.id.tv_status).setTextColor(context.getResources().getColor(R.color.status_applycancel));
                break;
            case "3":
                holder.setText(R.id.tv_status,"已取消");
                holder.getTextView(R.id.tv_status).setTextColor(context.getResources().getColor(R.color.status_canceled));
                break;
            case "4":
                holder.setText(R.id.tv_status,"已完成");
                holder.getTextView(R.id.tv_status).setTextColor(context.getResources().getColor(R.color.status_finished));
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, HotelOrderDelActivity.class);
                intent.putExtra("id",id);
                context.startActivity(intent);
            }
        });
    }

}
