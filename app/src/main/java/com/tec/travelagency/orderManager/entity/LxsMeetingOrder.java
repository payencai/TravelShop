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
import com.tec.travelagency.home.activity.meeting.MeetingOrderActivity;
import com.tec.travelagency.home.activity.rent.CarDetailActivity;
import com.tec.travelagency.orderManager.fragment.detail.MeetOrderDelActivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者：凌涛 on 2018/11/16 14:23
 * 邮箱：771548229@qq..com
 */
public class LxsMeetingOrder extends RVBaseCell implements Serializable {

    /**
     * address : string
     * addressDetail : string
     * amount : 0
     * city : string
     * conferenceRoomContactNumber : string
     * conferenceRoomId : string
     * conferenceRoomName : string
     * conferenceRoomTypeId : string
     * conferenceRoomTypeName : string
     * contactName : string
     * contactNumber : string
     * createTime : 2018-11-16T06:17:21.602Z
     * district : string
     * finishedTime : 2018-11-16T06:17:21.602Z
     * finishedType : string
     * firstDate : 2018-11-16T06:17:21.602Z
     * id : string
     * image1 : string
     * isCanCancel : string
     * isComment : string
     * isDeleted : string
     * isInner : string
     * isUseCoupon : string
     * lastDate : 2018-11-16T06:17:21.602Z
     * latitude : string
     * longitude : string
     * modeOfPayment : string
     * nickname : string
     * oneWord : string
     * orderNum : string
     * orderRoomNum : 0
     * paymentTime : 2018-11-16T06:17:21.602Z
     * periodOfValidity : string
     * platformAmount : 0
     * platformPrice : 0
     * productNum : string
     * province : string
     * publishAmount : 0
     * publishPrice : 0
     * refundTime : 2018-11-16T06:17:21.602Z
     * regulation : string
     * starLevel : string
     * status : string
     * travelAgencyConferenceRoomId : string
     * travelAgencyId : string
     * travelAgencyName : string
     * travelAgencyPrice : 0
     * updateTime : 2018-11-16T06:17:21.602Z
     * userId : string
     * warmPrompt : string
     *
     */
    private int orderType;

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    private String address;
    private String addressDetail;
    private double amount;
    private String city;
    private String conferenceRoomContactNumber;
    private String conferenceRoomId;
    private String conferenceRoomName;
    private String conferenceRoomTypeId;
    private String conferenceRoomTypeName;
    private String contactName;
    private String contactNumber;
    private String createTime;
    private String district;
    private String finishedTime;
    private String finishedType;
    private String firstDate;
    private String id;
    private String image1;
    private String isCanCancel;
    private String isComment;
    private String isDeleted;
    private String isInner;
    private String isUseCoupon;
    private String lastDate;
    private String latitude;
    private String longitude;
    private String modeOfPayment;
    private String nickname;
    private String oneWord;
    private String orderNum;
    private int orderRoomNum;
    private String paymentTime;
    private String periodOfValidity;
    private double platformAmount;
    private double platformPrice;
    private String productNum;
    private String province;
    private double publishAmount;
    private double publishPrice;
    private String refundTime;
    private String regulation;
    private String starLevel;
    private String status;
    private String travelAgencyConferenceRoomId;
    private String travelAgencyId;
    private String travelAgencyName;
    private double travelAgencyPrice;
    private String updateTime;
    private String userId;
    private String warmPrompt;

    public LxsMeetingOrder(Object o) {
        super(null);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getConferenceRoomContactNumber() {
        return conferenceRoomContactNumber;
    }

    public void setConferenceRoomContactNumber(String conferenceRoomContactNumber) {
        this.conferenceRoomContactNumber = conferenceRoomContactNumber;
    }

    public String getConferenceRoomId() {
        return conferenceRoomId;
    }

    public void setConferenceRoomId(String conferenceRoomId) {
        this.conferenceRoomId = conferenceRoomId;
    }

    public String getConferenceRoomName() {
        return conferenceRoomName;
    }

    public void setConferenceRoomName(String conferenceRoomName) {
        this.conferenceRoomName = conferenceRoomName;
    }

    public String getConferenceRoomTypeId() {
        return conferenceRoomTypeId;
    }

    public void setConferenceRoomTypeId(String conferenceRoomTypeId) {
        this.conferenceRoomTypeId = conferenceRoomTypeId;
    }

    public String getConferenceRoomTypeName() {
        return conferenceRoomTypeName;
    }

    public void setConferenceRoomTypeName(String conferenceRoomTypeName) {
        this.conferenceRoomTypeName = conferenceRoomTypeName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOneWord() {
        return oneWord;
    }

    public void setOneWord(String oneWord) {
        this.oneWord = oneWord;
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

    public String getPeriodOfValidity() {
        return periodOfValidity;
    }

    public void setPeriodOfValidity(String periodOfValidity) {
        this.periodOfValidity = periodOfValidity;
    }

    public double getPlatformAmount() {
        return platformAmount;
    }

    public void setPlatformAmount(double platformAmount) {
        this.platformAmount = platformAmount;
    }

    public double getPlatformPrice() {
        return platformPrice;
    }

    public void setPlatformPrice(double platformPrice) {
        this.platformPrice = platformPrice;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public double getPublishAmount() {
        return publishAmount;
    }

    public void setPublishAmount(double publishAmount) {
        this.publishAmount = publishAmount;
    }

    public double getPublishPrice() {
        return publishPrice;
    }

    public void setPublishPrice(double publishPrice) {
        this.publishPrice = publishPrice;
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

    public String getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(String starLevel) {
        this.starLevel = starLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTravelAgencyConferenceRoomId() {
        return travelAgencyConferenceRoomId;
    }

    public void setTravelAgencyConferenceRoomId(String travelAgencyConferenceRoomId) {
        this.travelAgencyConferenceRoomId = travelAgencyConferenceRoomId;
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

    public double getTravelAgencyPrice() {
        return travelAgencyPrice;
    }

    public void setTravelAgencyPrice(int travelAgencyPrice) {
        this.travelAgencyPrice = travelAgencyPrice;
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

    public String getWarmPrompt() {
        return warmPrompt;
    }

    public void setWarmPrompt(String warmPrompt) {
        this.warmPrompt = warmPrompt;
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
    public int getDays(String firstString, String secondString) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date firstDate = null;
        Date secondDate = null;
        try {
            firstDate = df.parse(firstString);
            secondDate = df.parse(secondString);
        } catch (Exception e) {
            // 日期型字符串格式错误
            System.out.println("日期型字符串格式错误");
        }
        int nDay = (int) ((secondDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000));
        return nDay;
    }
    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position, final Context context, RVBaseAdapter adapter) {
        int datenum=getDays(firstDate,lastDate)+1;
        holder.setText(R.id.visi_time,firstDate.substring(0,10)+"---"+lastDate.substring(0,10) +"   "+datenum+"天");
        holder.setText(R.id.number,orderNum+"");
        holder.setText(R.id.tv_amount,"￥"+amount);
        holder.setText(R.id.time,"下单时间: "+createTime);
        holder.setText(R.id.title1,conferenceRoomName);
        holder.setText(R.id.title2,conferenceRoomTypeName);
        holder.setText(R.id.contact,contactName);
        holder.setText(R.id.phone,contactNumber);
        holder.setText(R.id.tv_name,"会议室");
        holder.setText(R.id.num,"x"+orderRoomNum);
        holder.getView(R.id.title1).setVisibility(View.VISIBLE);
        holder.getTextView(R.id.tv_name).setTextColor(context.getResources().getColor(R.color.order_meeting));
        holder.getTextView(R.id.tv_name).setBackgroundResource(R.drawable.shape_order_meeting);

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
                Intent intent=new Intent(context, MeetOrderDelActivity.class);
                intent.putExtra("id",id);
                context.startActivity(intent);
            }
        });
    }

}
