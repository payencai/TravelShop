package com.tec.travelagency.orderManager.fragment.detail;

/**
 * 作者：凌涛 on 2018/11/26 16:36
 * 邮箱：771548229@qq..com
 */
public class Refund {

    /**
     * id : b432c8cc-6afe-4d31-9397-eedc963c5971
     * orderId : 95e2636256fa4ace921f808bf33b16f5
     * refundAmount : 4123.0
     * createTime : 2018-11-26 16:12:31
     * disposeTime : 2018-11-26 16:12:46
     * status : 3
     * userId : null
     * hotelId : null
     * travelAgencyId : 6d5674df-d1fb-4444-8067-e72857e737f4
     * refundReason : 发发发v
     * rejectReason : null
     * type : 2
     * categoryId : 2
     */

    private String id;
    private String orderId;
    private double refundAmount;
    private String createTime;
    private String disposeTime;
    private String status;
    private Object userId;
    private Object hotelId;
    private String travelAgencyId;
    private String refundReason;
    private Object rejectReason;
    private String type;
    private String categoryId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDisposeTime() {
        return disposeTime;
    }

    public void setDisposeTime(String disposeTime) {
        this.disposeTime = disposeTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Object getHotelId() {
        return hotelId;
    }

    public void setHotelId(Object hotelId) {
        this.hotelId = hotelId;
    }

    public String getTravelAgencyId() {
        return travelAgencyId;
    }

    public void setTravelAgencyId(String travelAgencyId) {
        this.travelAgencyId = travelAgencyId;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public Object getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(Object rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
