package com.tec.travelagency.home.entity;

/**
 * 作者：凌涛 on 2018/8/23 10:13
 * 邮箱：771548229@qq..com
 */
public class HotelRoomDetail {

    private String id= "";
    private String roomtypeId= "";
    private String hotelId= "";
    private String travelAgencyId= "";
    private String isAdopted= "";
    private String travelAgencyPrice= "";
    private double managePrice=0;
    private String name= "";
    private String floor = "";
    private String bedType= "";
    private String image1= "";
    private String image1Url= "";
    private String createTime= "";
    private String liveNum= "";
    private String bathroom= "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomtypeId() {
        return roomtypeId;
    }

    public void setRoomtypeId(String roomtypeId) {
        this.roomtypeId = roomtypeId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getTravelAgencyId() {
        return travelAgencyId;
    }

    public void setTravelAgencyId(String travelAgencyId) {
        this.travelAgencyId = travelAgencyId;
    }

    public String getIsAdopted() {
        return isAdopted;
    }

    public void setIsAdopted(String isAdopted) {
        this.isAdopted = isAdopted;
    }

    public String getTravelAgencyPrice() {
        return travelAgencyPrice;
    }

    public void setTravelAgencyPrice(String travelAgencyPrice) {
        this.travelAgencyPrice = travelAgencyPrice;
    }

    public double getManagePrice() {
        return managePrice;
    }

    public void setManagePrice(double managePrice) {
        this.managePrice = managePrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage1Url() {
        return image1Url;
    }

    public void setImage1Url(String image1Url) {
        this.image1Url = image1Url;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLiveNum() {
        return liveNum;
    }

    public void setLiveNum(String liveNum) {
        this.liveNum = liveNum;
    }

    public String getBathroom() {
        return bathroom;
    }

    public void setBathroom(String bathroom) {
        this.bathroom = bathroom;
    }
}
