package com.tec.travelagency.home.activity.rent;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/11/2 17:53
 * 邮箱：771548229@qq..com
 */
public class TravelAgencyCarRental implements Serializable{


    /**
     * id : 7b45f153-d012-48ae-b569-646cbc4f9648
     * carRentalId : a9a809bf-5611-4e76-af21-fe5e2d8c1400
     * travelAgencyId : 6d5674df-d1fb-4444-8067-e72857e737f4
     * createTime : 2018-11-05 16:03:07
     * platformPrice : 300.0
     * travelAgencyPrice : 51.0
     * isAdopted : 1
     * sequence : null
     * isDeleted : 1
     */

    private String id;
    private String carRentalId;
    private String travelAgencyId;
    private String createTime;
    private double platformPrice;
    private double travelAgencyPrice;
    private String isAdopted;
    private Object sequence;
    private String isDeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarRentalId() {
        return carRentalId;
    }

    public void setCarRentalId(String carRentalId) {
        this.carRentalId = carRentalId;
    }

    public String getTravelAgencyId() {
        return travelAgencyId;
    }

    public void setTravelAgencyId(String travelAgencyId) {
        this.travelAgencyId = travelAgencyId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public double getPlatformPrice() {
        return platformPrice;
    }

    public void setPlatformPrice(double platformPrice) {
        this.platformPrice = platformPrice;
    }

    public double getTravelAgencyPrice() {
        return travelAgencyPrice;
    }

    public void setTravelAgencyPrice(double travelAgencyPrice) {
        this.travelAgencyPrice = travelAgencyPrice;
    }

    public String getIsAdopted() {
        return isAdopted;
    }

    public void setIsAdopted(String isAdopted) {
        this.isAdopted = isAdopted;
    }

    public Object getSequence() {
        return sequence;
    }

    public void setSequence(Object sequence) {
        this.sequence = sequence;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }
}
