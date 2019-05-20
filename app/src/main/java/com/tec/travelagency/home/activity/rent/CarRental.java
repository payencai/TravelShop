package com.tec.travelagency.home.activity.rent;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/11/2 17:52
 * 邮箱：771548229@qq..com
 */
public class CarRental implements Serializable{


    /**
     * id : a9a809bf-5611-4e76-af21-fe5e2d8c1400
     * name : 摩托车
     * address : 广东省广州市番禺区大学城外环西路230号
     * contactNumber : 13202908144
     * longitude : 113.372243
     * latitude : 23.041037
     * addressDetail : 某某某学校
     * province : 广东省
     * city : 广州市
     * district : 番禺区
     * publishPrice : 200.0
     * oneSignPrice : 1.0
     * twoSignPrice : 1.0
     * threeSignPrice : 1.0
     * numberEveryDay : 1
     * seatNumber : 2
     * specification : 取车开始计算，每小时200块钱
     * introduce : 豪华的越野摩托车，非一般的速度
     * image1 : journey/2018110515595164
     * image2 : journey/2018110516000127
     * image3 : journey/2018110516000372
     * image4 : journey/2018110516000340
     * image5 : journey/2018110516000430
     * image6 : journey/2018110516000339
     * image1Url : http://xinlvmeng.oss-cn-shenzhen.aliyuncs.com/journey/2018110515595164?Expires=1542348936&OSSAccessKeyId=LTAIPJcqlCr74QtJ&Signature=lEfo/kdbpWEwFQXYMNatGzP4g8M=
     * image2Url : http://xinlvmeng.oss-cn-shenzhen.aliyuncs.com/journey/2018110516000127?Expires=1542348936&OSSAccessKeyId=LTAIPJcqlCr74QtJ&Signature=vixskj0sLR7naV7M6byFdAYnCI4=
     * image3Url : http://xinlvmeng.oss-cn-shenzhen.aliyuncs.com/journey/2018110516000372?Expires=1542348936&OSSAccessKeyId=LTAIPJcqlCr74QtJ&Signature=se5a5kJWWjPzHuBkuIAvVo+J5r4=
     * image4Url : http://xinlvmeng.oss-cn-shenzhen.aliyuncs.com/journey/2018110516000340?Expires=1542348936&OSSAccessKeyId=LTAIPJcqlCr74QtJ&Signature=qxzKYmPECJlTISQxabM5sRo8Kz0=
     * image5Url : http://xinlvmeng.oss-cn-shenzhen.aliyuncs.com/journey/2018110516000430?Expires=1542348936&OSSAccessKeyId=LTAIPJcqlCr74QtJ&Signature=v1GccO5Q44eho8AlPNtJFzKJuQQ=
     * image6Url : http://xinlvmeng.oss-cn-shenzhen.aliyuncs.com/journey/2018110516000339?Expires=1542348936&OSSAccessKeyId=LTAIPJcqlCr74QtJ&Signature=U28LfMDz+yiNJ1Y+aF4RwfDNKz8=
     * carType : 1
     * travelAgencyId : 0
     * managePriceFloor : 180.0
     * managePriceCeiling : 250.0
     * origin : 1
     * isCanCancel : 1
     * sequence : null
     * isUsed : 1
     * isDeleted : 1
     * createTime : 2018-11-05 16:00:18
     * commentData : {"score":5,"number":0,"rate":1}
     */

    private String id;
    private String name;
    private String address;
    private String contactNumber;
    private String longitude;
    private String latitude;
    private String addressDetail;
    private String province;
    private String city;
    private String district;
    private double publishPrice;
    private double oneSignPrice;
    private double twoSignPrice;
    private double threeSignPrice;
    private int numberEveryDay;
    private int seatNumber;
    private String specification;
    private String introduce;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String image6;
    private String image1Url;
    private String image2Url;
    private String image3Url;
    private String image4Url;
    private String image5Url;
    private String image6Url;
    private String carType;
    private String travelAgencyId;
    private double managePriceFloor;
    private double managePriceCeiling;
    private String origin;
    private String isCanCancel;
    private int sequence;
    private String isUsed;
    private String isDeleted;
    private String createTime;
    private CommentDataBean commentData;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public double getPublishPrice() {
        return publishPrice;
    }

    public void setPublishPrice(double publishPrice) {
        this.publishPrice = publishPrice;
    }

    public double getOneSignPrice() {
        return oneSignPrice;
    }

    public void setOneSignPrice(double oneSignPrice) {
        this.oneSignPrice = oneSignPrice;
    }

    public double getTwoSignPrice() {
        return twoSignPrice;
    }

    public void setTwoSignPrice(double twoSignPrice) {
        this.twoSignPrice = twoSignPrice;
    }

    public double getThreeSignPrice() {
        return threeSignPrice;
    }

    public void setThreeSignPrice(double threeSignPrice) {
        this.threeSignPrice = threeSignPrice;
    }

    public int getNumberEveryDay() {
        return numberEveryDay;
    }

    public void setNumberEveryDay(int numberEveryDay) {
        this.numberEveryDay = numberEveryDay;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getImage5() {
        return image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }

    public String getImage6() {
        return image6;
    }

    public void setImage6(String image6) {
        this.image6 = image6;
    }

    public String getImage1Url() {
        return image1Url;
    }

    public void setImage1Url(String image1Url) {
        this.image1Url = image1Url;
    }

    public String getImage2Url() {
        return image2Url;
    }

    public void setImage2Url(String image2Url) {
        this.image2Url = image2Url;
    }

    public String getImage3Url() {
        return image3Url;
    }

    public void setImage3Url(String image3Url) {
        this.image3Url = image3Url;
    }

    public String getImage4Url() {
        return image4Url;
    }

    public void setImage4Url(String image4Url) {
        this.image4Url = image4Url;
    }

    public String getImage5Url() {
        return image5Url;
    }

    public void setImage5Url(String image5Url) {
        this.image5Url = image5Url;
    }

    public String getImage6Url() {
        return image6Url;
    }

    public void setImage6Url(String image6Url) {
        this.image6Url = image6Url;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getTravelAgencyId() {
        return travelAgencyId;
    }

    public void setTravelAgencyId(String travelAgencyId) {
        this.travelAgencyId = travelAgencyId;
    }

    public double getManagePriceFloor() {
        return managePriceFloor;
    }

    public void setManagePriceFloor(double managePriceFloor) {
        this.managePriceFloor = managePriceFloor;
    }

    public double getManagePriceCeiling() {
        return managePriceCeiling;
    }

    public void setManagePriceCeiling(double managePriceCeiling) {
        this.managePriceCeiling = managePriceCeiling;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getIsCanCancel() {
        return isCanCancel;
    }

    public void setIsCanCancel(String isCanCancel) {
        this.isCanCancel = isCanCancel;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public CommentDataBean getCommentData() {
        return commentData;
    }

    public void setCommentData(CommentDataBean commentData) {
        this.commentData = commentData;
    }

    public static class CommentDataBean implements Serializable{
        /**
         * score : 5.0
         * number : 0
         * rate : 1.0
         */

        private float score;
        private int number;
        private double rate;

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }
    }
}
