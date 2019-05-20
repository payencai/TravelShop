package com.tec.travelagency.home.activity.ticket;

/**
 * 作者：凌涛 on 2018/11/17 19:42
 * 邮箱：771548229@qq..com
 */
public class PlatTicketSubBean {

    /**
     * id : string
     * image1 : string
     * image1Url : string
     * isAdopted : string
     * platformPrice : 0
     * travelAgencyPrice : 0
     * typeName : string
     */
    private int soldOutAmount;
    private String id;
    private String image1;
    private String image1Url;
    private String isAdopted;
    private double platformPrice;
    private double travelAgencyPrice;
    private String isCanCancel;
    private String oneDayValidity;

    public String getIsCanCancel() {
        return isCanCancel;
    }

    public void setIsCanCancel(String isCanCancel) {
        this.isCanCancel = isCanCancel;
    }

    public String getOneDayValidity() {
        return oneDayValidity;
    }

    public void setOneDayValidity(String oneDayValidity) {
        this.oneDayValidity = oneDayValidity;
    }

    private String name;

    public int getSoldOutAmount() {
        return soldOutAmount;
    }

    public void setSoldOutAmount(int soldOutAmount) {
        this.soldOutAmount = soldOutAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


}
