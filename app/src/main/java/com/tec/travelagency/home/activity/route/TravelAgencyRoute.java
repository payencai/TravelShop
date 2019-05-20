package com.tec.travelagency.home.activity.route;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/11/17 10:35
 * 邮箱：771548229@qq..com
 */
public class TravelAgencyRoute implements Serializable{

    /**
     * createTime : 2018-11-17T02:34:09.129Z
     * id : string
     * isAdopted : string
     * isDeleted : string
     * platformPrice : 0
     * routeId : string
     * sequence : 0
     * travelAgencyId : string
     * travelAgencyPrice : 0
     */

    private String createTime;
    private String id;
    private String isAdopted;
    private String isDeleted;
    private double platformPrice;
    private String routeId;
    private int sequence;
    private String travelAgencyId;
    private double travelAgencyPrice;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsAdopted() {
        return isAdopted;
    }

    public void setIsAdopted(String isAdopted) {
        this.isAdopted = isAdopted;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public double getPlatformPrice() {
        return platformPrice;
    }

    public void setPlatformPrice(double platformPrice) {
        this.platformPrice = platformPrice;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getTravelAgencyId() {
        return travelAgencyId;
    }

    public void setTravelAgencyId(String travelAgencyId) {
        this.travelAgencyId = travelAgencyId;
    }

    public double getTravelAgencyPrice() {
        return travelAgencyPrice;
    }

    public void setTravelAgencyPrice(double travelAgencyPrice) {
        this.travelAgencyPrice = travelAgencyPrice;
    }
}
