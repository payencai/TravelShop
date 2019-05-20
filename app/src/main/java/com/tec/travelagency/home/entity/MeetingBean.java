package com.tec.travelagency.home.entity;

public class MeetingBean {

    /**
     * id : 53d36e6b-0461-44c7-b638-d31b5eb71150
     * typeName : sdf
     * image1 : journey/2018102916013116
     * image1Url : http://xinlvmeng.oss-cn-shenzhen.aliyuncs.com/journey/2018102916013116?Expires=1540811030&OSSAccessKeyId=LTAIPJcqlCr74QtJ&Signature=l0QvaBBjQhU7WcpariP0iB48xSs%3D
     * isAdopted : 2
     * platformPrice : 0.01
     * travelAgencyPrice : 0.01
     */
    private String name;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    private String image1;
    private String image1Url;
    private String isAdopted;
    private double platformPrice;
    private double travelAgencyPrice;

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
