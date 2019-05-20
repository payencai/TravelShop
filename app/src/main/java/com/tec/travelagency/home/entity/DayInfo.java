package com.tec.travelagency.home.entity;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/8/22 17:39
 * 邮箱：771548229@qq..com
 */
public class DayInfo implements Serializable {


    private String id;
    private String routeId;
    private String day;
    private double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "DayInfo{" +
                "id='" + id + '\'' +
                ", routeId='" + routeId + '\'' +
                ", day='" + day + '\'' +
                ", price=" + price +
                '}';
    }
}
