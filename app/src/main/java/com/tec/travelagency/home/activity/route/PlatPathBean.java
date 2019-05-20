package com.tec.travelagency.home.activity.route;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/11/17 10:01
 * 邮箱：771548229@qq..com
 */
public class PlatPathBean implements Serializable{

    /**
     * commentData : {"number":0,"rate":0,"score":0}
     * id : string
     * image1 : string
     * image1Url : string
     * isAdopted : string
     * isCanCancel : string
     * name : string
     * platformPrice : 0
     * routeUsedTime : string
     * travelAgencyPrice : 0
     */

    private CommentDataBean commentData;
    private String id;
    private String image1;
    private String image1Url;
    private String isAdopted;
    private String isCanCancel;
    private String name;
    private double platformPrice;
    private String routeUsedTime;
    private double travelAgencyPrice;

    public CommentDataBean getCommentData() {
        return commentData;
    }

    public void setCommentData(CommentDataBean commentData) {
        this.commentData = commentData;
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

    public String getImage1Url() {
        return image1Url;
    }

    public void setImage1Url(String image1Url) {
        this.image1Url = image1Url;
    }

    public String getIsAdopted() {
        return isAdopted;
    }

    public void setIsAdopted(String isAdopted) {
        this.isAdopted = isAdopted;
    }

    public String getIsCanCancel() {
        return isCanCancel;
    }

    public void setIsCanCancel(String isCanCancel) {
        this.isCanCancel = isCanCancel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPlatformPrice() {
        return platformPrice;
    }

    public void setPlatformPrice(double platformPrice) {
        this.platformPrice = platformPrice;
    }

    public String getRouteUsedTime() {
        return routeUsedTime;
    }

    public void setRouteUsedTime(String routeUsedTime) {
        this.routeUsedTime = routeUsedTime;
    }

    public double getTravelAgencyPrice() {
        return travelAgencyPrice;
    }

    public void setTravelAgencyPrice(double travelAgencyPrice) {
        this.travelAgencyPrice = travelAgencyPrice;
    }

    public static class CommentDataBean implements Serializable{
        /**
         * number : 0
         * rate : 0
         * score : 0
         */

        private int number;
        private int rate;
        private float score;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getRate() {
            return rate;
        }

        public void setRate(int rate) {
            this.rate = rate;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }
    }
}
