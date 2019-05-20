package com.tec.travelagency.home.activity.rent;

/**
 * 作者：凌涛 on 2018/11/2 16:55
 * 邮箱：771548229@qq..com
 */
public class PlatCarBean {


    /**
     * id : 9c65f6a8-2494-4c3b-baae-043dd34d5ee6
     * name : 宝马5系
     * seatNumber : 4
     * image1 : journey/2018112217255336
     * image1Url : http://xinlvmeng.oss-cn-shenzhen.aliyuncs.com/journey/2018112217255336?Expires=1544784347&OSSAccessKeyId=LTAIPJcqlCr74QtJ&Signature=Zm/ZfhcjHMF8eOwkfXAZuZu72S8=
     * carType : 3
     * isCanCancel : 1
     * platformPrice : 110.45
     * travelAgencyPrice : 102.0
     * isAdopted : 2
     * numberEveryDay : 23
     * province : 广东省
     * city : 广州市
     * createTime : 2018-11-16 17:09:59
     * carRentalId : 5fd33429-add6-42b2-951c-7034c5f0ca66
     * commentData : {"score":5,"number":0,"rate":1}
     */

    private String id;
    private String name;
    private int seatNumber;
    private String image1;
    private String image1Url;
    private String carType;
    private String isCanCancel;
    private double platformPrice;
    private double travelAgencyPrice;
    private String isAdopted;
    private int numberEveryDay;
    private String province;
    private String city;
    private String createTime;
    private String carRentalId;
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

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
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

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getIsCanCancel() {
        return isCanCancel;
    }

    public void setIsCanCancel(String isCanCancel) {
        this.isCanCancel = isCanCancel;
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

    public int getNumberEveryDay() {
        return numberEveryDay;
    }

    public void setNumberEveryDay(int numberEveryDay) {
        this.numberEveryDay = numberEveryDay;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCarRentalId() {
        return carRentalId;
    }

    public void setCarRentalId(String carRentalId) {
        this.carRentalId = carRentalId;
    }

    public CommentDataBean getCommentData() {
        return commentData;
    }

    public void setCommentData(CommentDataBean commentData) {
        this.commentData = commentData;
    }

    public static class CommentDataBean {
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
