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
import com.tec.travelagency.orderManager.fragment.detail.PathOrderDelActivity;
import com.tec.travelagency.orderManager.fragment.user.UserPathOrderDelActivity;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/11/19 15:55
 * 邮箱：771548229@qq..com
 */
public class UserPathOrder extends RVBaseCell implements Serializable {

    /**
     * amount : 0
     * city : string
     * contactLocation : string
     * contactName : string
     * contactNumber : string
     * createTime : 2018-11-19T07:57:01.259Z
     * district : string
     * finishedTime : 2018-11-19T07:57:01.259Z
     * finishedType : string
     * id : string
     * image1 : string
     * introduce : string
     * isCanCancel : string
     * isComment : string
     * isDeleted : string
     * isInner : string
     * isUseCoupon : string
     * latitude : string
     * longitude : string
     * modeOfPayment : string
     * name : string
     * nickname : string
     * note : string
     * number : 0
     * numberEveryDay : 0
     * orderNum : string
     * paymentTime : 2018-11-19T07:57:01.259Z
     * placeBegin : string
     * placeBeginDetail : string
     * platformAmount : 0
     * platformPrice : 0
     * province : string
     * publishPrice : 0
     * pulishAmount : 0
     * refundTime : 2018-11-19T07:57:01.259Z
     * routeContactNumber : string
     * routeId : string
     * routeUsedTime : string
     * specification : string
     * status : string
     * travelAgencyId : string
     * travelAgencyName : string
     * travelAgencyPrice : 0
     * travelAgencyRouteId : string
     * updateTime : 2018-11-19T07:57:01.259Z
     * useTime : 2018-11-19T07:57:01.259Z
     * userId : string
     */

    private double amount;
    private String city;
    private String contactLocation;
    private String contactName;
    private String contactNumber;
    private String createTime;
    private String district;
    private String finishedTime;
    private String finishedType;
    private String id;
    private String image1;
    private String introduce;
    private String isCanCancel;
    private String isComment;
    private String isDeleted;
    private String isInner;
    private String isUseCoupon;
    private String latitude;
    private String longitude;
    private String modeOfPayment;
    private String name;
    private String nickname;
    private String note;
    private int number;
    private int numberEveryDay;
    private String orderNum;
    private String paymentTime;
    private String placeBegin;
    private String placeBeginDetail;
    private double platformAmount;
    private double platformPrice;
    private String province;
    private double publishPrice;
    private double pulishAmount;
    private String refundTime;
    private String routeContactNumber;
    private String routeId;
    private String routeUsedTime;
    private String specification;
    private String status;
    private String travelAgencyId;
    private String travelAgencyName;
    private double travelAgencyPrice;
    private String travelAgencyRouteId;
    private String updateTime;
    private String useTime;
    private String userId;

    public UserPathOrder(Object o) {
        super(o);
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

    public String getContactLocation() {
        return contactLocation;
    }

    public void setContactLocation(String contactLocation) {
        this.contactLocation = contactLocation;
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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumberEveryDay() {
        return numberEveryDay;
    }

    public void setNumberEveryDay(int numberEveryDay) {
        this.numberEveryDay = numberEveryDay;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getPlaceBegin() {
        return placeBegin;
    }

    public void setPlaceBegin(String placeBegin) {
        this.placeBegin = placeBegin;
    }

    public String getPlaceBeginDetail() {
        return placeBeginDetail;
    }

    public void setPlaceBeginDetail(String placeBeginDetail) {
        this.placeBeginDetail = placeBeginDetail;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public double getPublishPrice() {
        return publishPrice;
    }

    public void setPublishPrice(double publishPrice) {
        this.publishPrice = publishPrice;
    }

    public double getPulishAmount() {
        return pulishAmount;
    }

    public void setPulishAmount(double pulishAmount) {
        this.pulishAmount = pulishAmount;
    }

    public String getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(String refundTime) {
        this.refundTime = refundTime;
    }

    public String getRouteContactNumber() {
        return routeContactNumber;
    }

    public void setRouteContactNumber(String routeContactNumber) {
        this.routeContactNumber = routeContactNumber;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteUsedTime() {
        return routeUsedTime;
    }

    public void setRouteUsedTime(String routeUsedTime) {
        this.routeUsedTime = routeUsedTime;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public void setTravelAgencyPrice(double travelAgencyPrice) {
        this.travelAgencyPrice = travelAgencyPrice;
    }

    public String getTravelAgencyRouteId() {
        return travelAgencyRouteId;
    }

    public void setTravelAgencyRouteId(String travelAgencyRouteId) {
        this.travelAgencyRouteId = travelAgencyRouteId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_car, parent, false);
        return new RVBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position, final Context context, RVBaseAdapter adapter) {

        holder.setText(R.id.number,orderNum+"");
        holder.setText(R.id.tv_amount,"￥"+amount);
        holder.setText(R.id.time,"下单时间: "+createTime);
        holder.setText(R.id.visi_time,"出发时间: "+useTime.substring(0,10)+"   "+"1天");
        holder.setText(R.id.title2,name);
        holder.setText(R.id.contact,contactName);
        holder.setText(R.id.phone,contactNumber);
        holder.setText(R.id.tv_name,"路线");
        holder.setText(R.id.num,"x"+number);
        holder.getTextView(R.id.tv_name).setBackgroundResource(R.drawable.shape_order_path);
        holder.getTextView(R.id.tv_name).setTextColor(context.getResources().getColor(R.color.order_path));
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
                Intent intent=new Intent(context, UserPathOrderDelActivity.class);
                intent.putExtra("id",id);
                context.startActivity(intent);
            }
        });
    }
}
