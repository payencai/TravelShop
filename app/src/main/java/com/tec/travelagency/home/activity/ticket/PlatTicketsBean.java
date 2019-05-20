package com.tec.travelagency.home.activity.ticket;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/11/17 18:25
 * 邮箱：771548229@qq..com
 */
public class PlatTicketsBean implements Serializable{


    /**
     * scenicSpot : {"id":"61e9c7f2-89a0-4c52-a568-574493deb15c","name":"九寨沟景点","starLevel":"5","oneWord":"四川旅游胜地九寨沟,蓝天、白云、雪山、森林、尽融于瀑、河、滩、缀成一串串宛若从天而降的珍珠；篝火、烤羊、锅庄和古老而美丽的传说，展现出藏羌人热情强悍的民族风情","address":"四川省阿坝藏族羌族自治州九寨沟市301省道扎如寺路段","addressDetail":"某某某山","longitude":"103.926576","latitude":"33.185479","province":"四川省","city":"阿坝藏族羌族自治州","district":"九寨沟市","image1":"journey/2018112014461696","image2":"journey/2018112014461618","image3":"journey/2018112014461630","image4":"journey/2018112014461611","image5":"","image6":"","image1Url":"http://xinlvmeng.oss-cn-shenzhen.aliyuncs.com/journey/2018112014461696?Expires=1543223173&OSSAccessKeyId=LTAIPJcqlCr74QtJ&Signature=P0j1WgSCmghzYVPPXmeOvT/E4Zs=","image2Url":"http://xinlvmeng.oss-cn-shenzhen.aliyuncs.com/journey/2018112014461618?Expires=1543223173&OSSAccessKeyId=LTAIPJcqlCr74QtJ&Signature=lSUDS147LfDr+cCAVAOjrUGdySw=","image3Url":"http://xinlvmeng.oss-cn-shenzhen.aliyuncs.com/journey/2018112014461630?Expires=1543223173&OSSAccessKeyId=LTAIPJcqlCr74QtJ&Signature=Sj79GsO1BCmgge5vLxFqgzUND3k=","image4Url":"http://xinlvmeng.oss-cn-shenzhen.aliyuncs.com/journey/2018112014461611?Expires=1543223173&OSSAccessKeyId=LTAIPJcqlCr74QtJ&Signature=tc68lv/tv0CdFJPXvu+BO2g0aCI=","image5Url":null,"image6Url":null,"contactNumber":"13202908144","origin":"1","travelAgencyId":"0","sequence":null,"createTime":"2018-11-15 11:39:45","isUsed":"1","isDeleted":"1","commentData":{"score":4,"number":2,"rate":0.5}}
     * isAdded : 1
     */

    private ScenicSpotBean scenicSpot;
    private String isAdded;

    public ScenicSpotBean getScenicSpot() {
        return scenicSpot;
    }

    public void setScenicSpot(ScenicSpotBean scenicSpot) {
        this.scenicSpot = scenicSpot;
    }

    public String getIsAdded() {
        return isAdded;
    }

    public void setIsAdded(String isAdded) {
        this.isAdded = isAdded;
    }

    public static class ScenicSpotBean {
        /**
         * id : 61e9c7f2-89a0-4c52-a568-574493deb15c
         * name : 九寨沟景点
         * starLevel : 5
         * oneWord : 四川旅游胜地九寨沟,蓝天、白云、雪山、森林、尽融于瀑、河、滩、缀成一串串宛若从天而降的珍珠；篝火、烤羊、锅庄和古老而美丽的传说，展现出藏羌人热情强悍的民族风情
         * address : 四川省阿坝藏族羌族自治州九寨沟市301省道扎如寺路段
         * addressDetail : 某某某山
         * longitude : 103.926576
         * latitude : 33.185479
         * province : 四川省
         * city : 阿坝藏族羌族自治州
         * district : 九寨沟市
         * image1 : journey/2018112014461696
         * image2 : journey/2018112014461618
         * image3 : journey/2018112014461630
         * image4 : journey/2018112014461611
         * image5 :
         * image6 :
         * image1Url : http://xinlvmeng.oss-cn-shenzhen.aliyuncs.com/journey/2018112014461696?Expires=1543223173&OSSAccessKeyId=LTAIPJcqlCr74QtJ&Signature=P0j1WgSCmghzYVPPXmeOvT/E4Zs=
         * image2Url : http://xinlvmeng.oss-cn-shenzhen.aliyuncs.com/journey/2018112014461618?Expires=1543223173&OSSAccessKeyId=LTAIPJcqlCr74QtJ&Signature=lSUDS147LfDr+cCAVAOjrUGdySw=
         * image3Url : http://xinlvmeng.oss-cn-shenzhen.aliyuncs.com/journey/2018112014461630?Expires=1543223173&OSSAccessKeyId=LTAIPJcqlCr74QtJ&Signature=Sj79GsO1BCmgge5vLxFqgzUND3k=
         * image4Url : http://xinlvmeng.oss-cn-shenzhen.aliyuncs.com/journey/2018112014461611?Expires=1543223173&OSSAccessKeyId=LTAIPJcqlCr74QtJ&Signature=tc68lv/tv0CdFJPXvu+BO2g0aCI=
         * image5Url : null
         * image6Url : null
         * contactNumber : 13202908144
         * origin : 1
         * travelAgencyId : 0
         * sequence : null
         * createTime : 2018-11-15 11:39:45
         * isUsed : 1
         * isDeleted : 1
         * commentData : {"score":4,"number":2,"rate":0.5}
         */
        private double bottomPrice;

        public double getBottomPrice() {
            return bottomPrice;
        }

        public void setBottomPrice(double bottomPrice) {
            this.bottomPrice = bottomPrice;
        }

        private String id;
        private String name;
        private String starLevel;
        private String oneWord;
        private String address;
        private String addressDetail;
        private String longitude;
        private String latitude;
        private String province;
        private String city;
        private String district;
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
        private Object image5Url;
        private Object image6Url;
        private String contactNumber;
        private String origin;
        private String travelAgencyId;
        private Object sequence;
        private String createTime;
        private String isUsed;
        private String isDeleted;
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

        public String getStarLevel() {
            return starLevel;
        }

        public void setStarLevel(String starLevel) {
            this.starLevel = starLevel;
        }

        public String getOneWord() {
            return oneWord;
        }

        public void setOneWord(String oneWord) {
            this.oneWord = oneWord;
        }

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

        public Object getImage5Url() {
            return image5Url;
        }

        public void setImage5Url(Object image5Url) {
            this.image5Url = image5Url;
        }

        public Object getImage6Url() {
            return image6Url;
        }

        public void setImage6Url(Object image6Url) {
            this.image6Url = image6Url;
        }

        public String getContactNumber() {
            return contactNumber;
        }

        public void setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getTravelAgencyId() {
            return travelAgencyId;
        }

        public void setTravelAgencyId(String travelAgencyId) {
            this.travelAgencyId = travelAgencyId;
        }

        public Object getSequence() {
            return sequence;
        }

        public void setSequence(Object sequence) {
            this.sequence = sequence;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public CommentDataBean getCommentData() {
            return commentData;
        }

        public void setCommentData(CommentDataBean commentData) {
            this.commentData = commentData;
        }

        public static class CommentDataBean {
            /**
             * score : 4
             * number : 2
             * rate : 0.5
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
}
