package com.tec.travelagency.home.entity;

import java.io.Serializable;

public class MeetDelBean implements Serializable{


    /**
     * travelAgencyConferenceRoom : {"id":"0a3c0697-aa2c-43a2-9fbb-14145110d065","conferenceRoomId":"7d7b8585-68b8-4881-946a-9f8ebec8c73b","conferenceRoomTypeId":"a5b013fa-da57-4a60-aff6-99355d934af6","travelAgencyId":"6d5674df-d1fb-4444-8067-e72857e737f4","createTime":"2018-10-30 09:43:14","platformPrice":22,"travelAgencyPrice":333,"isAdopted":"2","sequence":null,"isDeleted":"1"}
     * conferenceRoomType : {"id":"a5b013fa-da57-4a60-aff6-99355d934af6","conferenceRoomId":"7d7b8585-68b8-4881-946a-9f8ebec8c73b","typeName":"线上类型会议室测试1","periodOfValidity":"法国恢复供货","regulation":"电饭锅电饭锅","warmPrompt":"电饭锅电饭锅","image1":"journey/2018102919192269","image2":"","image3":"","image4":"","image5":"","image6":"","image1Url":"http://xinlvmeng.oss-cn-shenzhen.aliyuncs.com/journey/2018102919192269?Expires=1540868945&OSSAccessKeyId=LTAIPJcqlCr74QtJ&Signature=fMgG0QvKfFY4vSrEmGHv57H9XaI%3D","image2Url":null,"image3Url":null,"image4Url":null,"image5Url":null,"image6Url":null,"publishPrice":1,"managePriceFloor":1,"managePriceCeiling":7,"sequence":null,"createTime":"2018-10-29 19:19:26","isUsed":"1","isDeleted":"1"}
     */

    private TravelAgencyConferenceRoomBean travelAgencyConferenceRoom;
    private ConferenceRoomTypeBean conferenceRoomType;

    public TravelAgencyConferenceRoomBean getTravelAgencyConferenceRoom() {
        return travelAgencyConferenceRoom;
    }

    public void setTravelAgencyConferenceRoom(TravelAgencyConferenceRoomBean travelAgencyConferenceRoom) {
        this.travelAgencyConferenceRoom = travelAgencyConferenceRoom;
    }

    public ConferenceRoomTypeBean getConferenceRoomType() {
        return conferenceRoomType;
    }

    public void setConferenceRoomType(ConferenceRoomTypeBean conferenceRoomType) {
        this.conferenceRoomType = conferenceRoomType;
    }

    public static class TravelAgencyConferenceRoomBean implements Serializable{
        /**
         * id : 0a3c0697-aa2c-43a2-9fbb-14145110d065
         * conferenceRoomId : 7d7b8585-68b8-4881-946a-9f8ebec8c73b
         * conferenceRoomTypeId : a5b013fa-da57-4a60-aff6-99355d934af6
         * travelAgencyId : 6d5674df-d1fb-4444-8067-e72857e737f4
         * createTime : 2018-10-30 09:43:14
         * platformPrice : 22.0
         * travelAgencyPrice : 333.0
         * isAdopted : 2
         * sequence : null
         * isDeleted : 1
         */

        private String id;
        private String conferenceRoomId;
        private String conferenceRoomTypeId;
        private String travelAgencyId;
        private String createTime;
        private double platformPrice;
        private double travelAgencyPrice;
        private String isAdopted;
        private int sequence;
        private String isDeleted;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getConferenceRoomId() {
            return conferenceRoomId;
        }

        public void setConferenceRoomId(String conferenceRoomId) {
            this.conferenceRoomId = conferenceRoomId;
        }

        public String getConferenceRoomTypeId() {
            return conferenceRoomTypeId;
        }

        public void setConferenceRoomTypeId(String conferenceRoomTypeId) {
            this.conferenceRoomTypeId = conferenceRoomTypeId;
        }

        public String getTravelAgencyId() {
            return travelAgencyId;
        }

        public void setTravelAgencyId(String travelAgencyId) {
            this.travelAgencyId = travelAgencyId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public int getSequence() {
            return sequence;
        }

        public void setSequence(int sequence) {
            this.sequence = sequence;
        }

        public String getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }
    }

    public static class ConferenceRoomTypeBean implements Serializable{
        /**
         * id : a5b013fa-da57-4a60-aff6-99355d934af6
         * conferenceRoomId : 7d7b8585-68b8-4881-946a-9f8ebec8c73b
         * typeName : 线上类型会议室测试1
         * periodOfValidity : 法国恢复供货
         * regulation : 电饭锅电饭锅
         * warmPrompt : 电饭锅电饭锅
         * image1 : journey/2018102919192269
         * image2 :
         * image3 :
         * image4 :
         * image5 :
         * image6 :
         * image1Url : http://xinlvmeng.oss-cn-shenzhen.aliyuncs.com/journey/2018102919192269?Expires=1540868945&OSSAccessKeyId=LTAIPJcqlCr74QtJ&Signature=fMgG0QvKfFY4vSrEmGHv57H9XaI%3D
         * image2Url : null
         * image3Url : null
         * image4Url : null
         * image5Url : null
         * image6Url : null
         * publishPrice : 1.0
         * managePriceFloor : 1.0
         * managePriceCeiling : 7.0
         * sequence : null
         * createTime : 2018-10-29 19:19:26
         * isUsed : 1
         * isDeleted : 1
         */

        private String id;
        private String conferenceRoomId;
        private String typeName;
        private String periodOfValidity;
        private String regulation;
        private String warmPrompt;
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
        private double publishPrice;
        private double managePriceFloor;
        private double managePriceCeiling;
        private int sequence;
        private String createTime;
        private String isUsed;
        private String isDeleted;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getConferenceRoomId() {
            return conferenceRoomId;
        }

        public void setConferenceRoomId(String conferenceRoomId) {
            this.conferenceRoomId = conferenceRoomId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getPeriodOfValidity() {
            return periodOfValidity;
        }

        public void setPeriodOfValidity(String periodOfValidity) {
            this.periodOfValidity = periodOfValidity;
        }

        public String getRegulation() {
            return regulation;
        }

        public void setRegulation(String regulation) {
            this.regulation = regulation;
        }

        public String getWarmPrompt() {
            return warmPrompt;
        }

        public void setWarmPrompt(String warmPrompt) {
            this.warmPrompt = warmPrompt;
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

        public double getPublishPrice() {
            return publishPrice;
        }

        public void setPublishPrice(double publishPrice) {
            this.publishPrice = publishPrice;
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

        public int getSequence() {
            return sequence;
        }

        public void setSequence(int sequence) {
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
    }
}
