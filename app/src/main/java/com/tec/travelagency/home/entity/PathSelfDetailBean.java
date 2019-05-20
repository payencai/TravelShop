package com.tec.travelagency.home.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：凌涛 on 2018/9/10 14:37
 * 邮箱：771548229@qq..com
 */
public class PathSelfDetailBean implements Serializable {

    private String id;
    private String name;
    private String image;
    private String imageUrl;
    private String travelAgencyId;
    private double price;
    private String isCanCancel;
    private String cityBegin;
    private String routeUsedTime;
    private String reservationSpecification;
    private String trafficSpecification;
    private String ticketSpecification;
    private String hotelSpecification;
    private String isDeleted;
    private String createTime;
    private List<RoutePerdayListBean> routePerdayList;
    private List<RouteScenicSpotListBean> routeScenicSpotList;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTravelAgencyId() {
        return travelAgencyId;
    }

    public void setTravelAgencyId(String travelAgencyId) {
        this.travelAgencyId = travelAgencyId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIsCanCancel() {
        return isCanCancel;
    }

    public void setIsCanCancel(String isCanCancel) {
        this.isCanCancel = isCanCancel;
    }

    public String getCityBegin() {
        return cityBegin;
    }

    public void setCityBegin(String cityBegin) {
        this.cityBegin = cityBegin;
    }

    public String getRouteUsedTime() {
        return routeUsedTime;
    }

    public void setRouteUsedTime(String routeUsedTime) {
        this.routeUsedTime = routeUsedTime;
    }

    public String getReservationSpecification() {
        return reservationSpecification;
    }

    public void setReservationSpecification(String reservationSpecification) {
        this.reservationSpecification = reservationSpecification;
    }

    public String getTrafficSpecification() {
        return trafficSpecification;
    }

    public void setTrafficSpecification(String trafficSpecification) {
        this.trafficSpecification = trafficSpecification;
    }

    public String getTicketSpecification() {
        return ticketSpecification;
    }

    public void setTicketSpecification(String ticketSpecification) {
        this.ticketSpecification = ticketSpecification;
    }

    public String getHotelSpecification() {
        return hotelSpecification;
    }

    public void setHotelSpecification(String hotelSpecification) {
        this.hotelSpecification = hotelSpecification;
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

    public List<RoutePerdayListBean> getRoutePerdayList() {
        return routePerdayList;
    }

    public void setRoutePerdayList(List<RoutePerdayListBean> routePerdayList) {
        this.routePerdayList = routePerdayList;
    }

    public List<RouteScenicSpotListBean> getRouteScenicSpotList() {
        return routeScenicSpotList;
    }

    public void setRouteScenicSpotList(List<RouteScenicSpotListBean> routeScenicSpotList) {
        this.routeScenicSpotList = routeScenicSpotList;
    }

    public static class RoutePerdayListBean implements Serializable{

        private String id;
        private int dayNum;
        private String title;
        private String assemblingPlace;
        private String timeAgreement;
        private String hotelId;
        private String roomtypeId;
        private String roomtypeName;
        private String scenicSpotId;
        private String transportation;
        private String runningTime;
        private String runningPath;
        private String havaCarRental;
        private String carRentalId;
        private String routeId;
        private String travelAgencyId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getDayNum() {
            return dayNum;
        }

        public void setDayNum(int dayNum) {
            this.dayNum = dayNum;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAssemblingPlace() {
            return assemblingPlace;
        }

        public void setAssemblingPlace(String assemblingPlace) {
            this.assemblingPlace = assemblingPlace;
        }

        public String getTimeAgreement() {
            return timeAgreement;
        }

        public void setTimeAgreement(String timeAgreement) {
            this.timeAgreement = timeAgreement;
        }

        public String getHotelId() {
            return hotelId;
        }

        public void setHotelId(String hotelId) {
            this.hotelId = hotelId;
        }

        public String getRoomtypeId() {
            return roomtypeId;
        }

        public void setRoomtypeId(String roomtypeId) {
            this.roomtypeId = roomtypeId;
        }

        public String getRoomtypeName() {
            return roomtypeName;
        }

        public void setRoomtypeName(String roomtypeName) {
            this.roomtypeName = roomtypeName;
        }

        public String getScenicSpotId() {
            return scenicSpotId;
        }

        public void setScenicSpotId(String scenicSpotId) {
            this.scenicSpotId = scenicSpotId;
        }

        public String getTransportation() {
            return transportation;
        }

        public void setTransportation(String transportation) {
            this.transportation = transportation;
        }

        public String getRunningTime() {
            return runningTime;
        }

        public void setRunningTime(String runningTime) {
            this.runningTime = runningTime;
        }

        public String getRunningPath() {
            return runningPath;
        }

        public void setRunningPath(String runningPath) {
            this.runningPath = runningPath;
        }

        public String getHavaCarRental() {
            return havaCarRental;
        }

        public void setHavaCarRental(String havaCarRental) {
            this.havaCarRental = havaCarRental;
        }

        public String getCarRentalId() {
            return carRentalId;
        }

        public void setCarRentalId(String carRentalId) {
            this.carRentalId = carRentalId;
        }

        public String getRouteId() {
            return routeId;
        }

        public void setRouteId(String routeId) {
            this.routeId = routeId;
        }

        public String getTravelAgencyId() {
            return travelAgencyId;
        }

        public void setTravelAgencyId(String travelAgencyId) {
            this.travelAgencyId = travelAgencyId;
        }
    }

    public static class RouteScenicSpotListBean implements Serializable {
        private String id;
        private String name;
        private String introduce;
        private String image;
        private String imageUrl;
        private String routeId;
        private int sequence;
        private String travelAgencyId;

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

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getRouteId() {
            return routeId;
        }

        public void setRouteId(String routeId) {
            this.routeId = routeId;
        }

        public int getSequence() {
            return sequence;
        }

        public void setSequence(int sequence) {
            this.sequence = sequence;
        }

        public String getTravelAgencyId() {
            return travelAgencyId;
        }

        public void setTravelAgencyId(String travelAgencyId) {
            this.travelAgencyId = travelAgencyId;
        }
    }
}
