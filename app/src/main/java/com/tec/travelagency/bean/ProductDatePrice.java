package com.tec.travelagency.bean;

import java.io.Serializable;

/**
 * yangqiangyu on 09/11/2016 14:17
 */

public class ProductDatePrice implements Serializable {


    private String date;
    private double price;
    private int emptyNum;
    private int id;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getEmptyNum() {
        return emptyNum;
    }

    public void setEmptyNum(int emptyNum) {
        this.emptyNum = emptyNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%s %s",date,price);
    }
}
