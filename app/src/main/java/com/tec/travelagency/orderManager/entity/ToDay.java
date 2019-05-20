package com.tec.travelagency.orderManager.entity;

/**
 * 作者：凌涛 on 2018/8/27 16:27
 * 邮箱：771548229@qq..com
 */
public class ToDay {

    private String categoryId;
    private String categoryName;
    private int orderTotal;
    private double amountTotal;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(int orderTotal) {
        this.orderTotal = orderTotal;
    }

    public double getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(double amountTotal) {
        this.amountTotal = amountTotal;
    }
}
