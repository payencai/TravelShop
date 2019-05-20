package com.tec.travelagency.home.activity.ticket;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/11/17 16:47
 * 邮箱：771548229@qq..com
 */
public class SaleTicketsBean implements Serializable{

    /**
     * address : string
     * addressDetail : string
     * city : string
     * commentData : {"number":0,"rate":0,"score":0}
     * contactNumber : string
     * createTime : 2018-11-17T08:47:06.368Z
     * district : string
     * id : string
     * image1 : string
     * image1Url : string
     * image2 : string
     * image2Url : string
     * image3 : string
     * image3Url : string
     * image4 : string
     * image4Url : string
     * image5 : string
     * image5Url : string
     * image6 : string
     * image6Url : string
     * isDeleted : string
     * isUsed : string
     * latitude : string
     * longitude : string
     * name : string
     * oneWord : string
     * origin : string
     * province : string
     * sequence : 0
     * starLevel : string
     * travelAgencyId : string
     */
    private double bottomPrice;
    private String address;

    public double getBottomPrice() {
        return bottomPrice;
    }

    public void setBottomPrice(double bottomPrice) {
        this.bottomPrice = bottomPrice;
    }

    private String addressDetail;
    private String city;
    private CommentDataBean commentData;
    private String contactNumber;
    private String createTime;
    private String district;
    private String id;
    private String image1;
    private String image1Url;
    private String image2;
    private String image2Url;
    private String image3;
    private String image3Url;
    private String image4;
    private String image4Url;
    private String image5;
    private String image5Url;
    private String image6;
    private String image6Url;
    private String isDeleted;
    private String isUsed;
    private String latitude;
    private String longitude;
    private String name;
    private String oneWord;
    private String origin;
    private String province;
    private int sequence;
    private String starLevel;
    private String travelAgencyId;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public CommentDataBean getCommentData() {
        return commentData;
    }

    public void setCommentData(CommentDataBean commentData) {
        this.commentData = commentData;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
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

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage2Url() {
        return image2Url;
    }

    public void setImage2Url(String image2Url) {
        this.image2Url = image2Url;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage3Url() {
        return image3Url;
    }

    public void setImage3Url(String image3Url) {
        this.image3Url = image3Url;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getImage4Url() {
        return image4Url;
    }

    public void setImage4Url(String image4Url) {
        this.image4Url = image4Url;
    }

    public String getImage5() {
        return image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }

    public String getImage5Url() {
        return image5Url;
    }

    public void setImage5Url(String image5Url) {
        this.image5Url = image5Url;
    }

    public String getImage6() {
        return image6;
    }

    public void setImage6(String image6) {
        this.image6 = image6;
    }

    public String getImage6Url() {
        return image6Url;
    }

    public void setImage6Url(String image6Url) {
        this.image6Url = image6Url;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOneWord() {
        return oneWord;
    }

    public void setOneWord(String oneWord) {
        this.oneWord = oneWord;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(String starLevel) {
        this.starLevel = starLevel;
    }

    public String getTravelAgencyId() {
        return travelAgencyId;
    }

    public void setTravelAgencyId(String travelAgencyId) {
        this.travelAgencyId = travelAgencyId;
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
