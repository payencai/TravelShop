package com.tec.travelagency.Constant;

/**
 * 作者：凌涛 on 2018/8/6 09:38
 * 邮箱：771548229@qq..com
 */
public class PlatformContans {

    //public static String root = "http://172.20.10.3:8083";//测试地址
//    public static String root = "http://192.168.3.29:8083";//测试地址
//    public static String root = "http://47.107.55.94:8083";//正式地址

    public static String root = "https://www.ynshake.com:8443";//正式地址
    //public static String root = "http://192.168.3.29:8083";//正式地址

    public static class TravelAgencyUser {
        public static final String login = root + "/travelAgency_user/login";//登录接口
        public static final String updatePassword = root + "/travelAgency_user/updatePassword";//修改密码
    }

    public static class Statistics {
        public static final String countOrdersForTravelAgencyApp = root + "/statistics/countOrdersForTravelAgencyApp";//登录接口

    }  public static class Roomnum {
        ///roomnum/getEmptyNumofRoomtype
        public static final String getEmptyNumofRoomtype = root + "/roomnum/getEmptyNumofRoomtype";//获取开始时间到离店时间段内的空房数量

    }

    public static class Notice {
        public static final String get = root + "/notice/get";//获取平台公告

    }
    public static class Push {
        public static final String get = root  + "/push/getMyMessage";//获取平台公告

    }
    //
    public static class Banner {
        public static final String getList = root + "/banner_for_travel_agency_app/getList";//获取列表

    }
    //

    public static class Comment {
        public static final String getHotelCommentList = root + "/comment/getHotelCommentList";//根据酒店id分页获取酒店评价列表数据
        public static final String getCarRentalCommentList = root + "/comment/getCarRentalCommentList";//根据租车id分页获取租车评价列表数据
        public static final String getRouteCommentList = root + "/comment/getRouteCommentList";//根据路线id分页获取租车评价列表数据
        public static final String getTicketCommentList = root + "/comment/getTicketCommentList";//根据景点id分页获取租车评价列表数据
        public static final String getConferenceRoomCommentList = root + "/comment/getConferenceRoomCommentList";//根据景点id分页获取租车评价列表数据


        public static final String getHotelCommentScoreById = root + "/comment/getHotelCommentScoreById";//根据酒店id获取综合评分
        public static final String getCarRentalCommentScoreById = root + "/comment/getCarRentalCommentScoreById";//根据租车id获取综合评分
        public static final String getRouteCommentScoreById = root + "/comment/getRouteCommentScoreById";//根据路线id获取综合评分
        public static final String getTicketCommentScoreById = root + "/comment/getTicketCommentScoreById";//根据景点id获取综合评分
        public static final String getConferenceRoomCommentScoreById = root + "/comment/getConferenceRoomCommentScoreById";//根据景点id获取综合评分



        public static final String commetCar = root + "/comment/addCarRentalCommentForTravelAgency";//根据酒店id获取综合评分
        public static final String commetPath = root + "/comment/addRouteCommentForTravelAgency";//根据租车id获取综合评分
        public static final String commetTickets = root + "/comment/addTicketCommentForTravelAgency";//根据路线id获取综合评分
        public static final String commetMeeting = root + "/comment/addConferenceRoomCommentForTravelAgency";//根据景点id获取综合评分
        public static final String commetHotel = root + "/comment/addHotelCommentForTravelAgency";//根据景点id获取综合评分
    }

    public static class TravelAgency {
        public static final String getMyInfo = root + "/travelAgency/getMyInfo";//获取个人信息
        public static final String uploadImage = root + "/travelAgency/uploadImage";
        public static final String updateIntroduce = root + "/travelAgency/updateIntroduce";//更新简介
        public static final String updateLocation = root + "/travelAgency/updateLocation";//更新地址
        public static final String updateContactNum = root + "/travelAgency/updateContactNum";//更新地址
        public static final String getHotels = root + "/travel_agency/roomtype/getHotels";//获取平台酒店列表
        public static final String getHotelsAdded = root + "/travel_agency/roomtype/getHotelsAdded";//获取酒店列表
        public static final String getRoomtype = root + "/travel_agency/roomtype/getRoomtype";//获取某个酒店下的房间类型
        public static final String updatePrice = root + "/travel_agency/roomtype/updatePrice";//更改房型价格
        public static final String adopt = root + "/travel_agency/roomtype/adopt";//添加房型
        public static final String cancel = root + "/travel_agency/roomtype/cancel";//删除房型
        public static final String uploadLogo = root + "/travelAgency/uploadLogo";//删除房型
        public static final String getHotelsForCustomer = root + "/travel_agency/roomtype/getHotelsForCustomer";//服务号获取酒店列表
        public static final String getRoomtypeForCustomer = root + "/travel_agency/roomtype/getRoomtypeForCustomer";//服务号获取旅行社的某个酒店下的房间类型
        public static final String getRoomtypeDetailForCustomer = root + "/travel_agency/roomtype/getRoomtypeDetailForCustomer";//服务号获取某个酒店下的房间类型详情
        public static final String getRoomtypeDetail = root + "/travel_agency/roomtype/getRoomtypeDetail";//旅行社端获取某个酒店下的房间类型详情


        public static final String getPlatCarList = root + "/travel_agency/car_rental/getCarRentals";//删除房型
        public static final String adapotPlatCar = root + "/travel_agency/car_rental/adopt";//删除房型
        public static final String getPlatCarDetail = root + "/travel_agency/car_rental/getCarRental";//服务号获取酒店列表
        public static final String removePlatCar = root + "/travel_agency/car_rental/remove";//服务号获取旅行社的某个酒店下的房间类型
        public static final String updatePlatCarPrice = root + "/travel_agency/car_rental/updatePrice";//服务号获取某个酒店下的房间类型详情

        public static final String getSaleCarList = root + "/car_rental/getCarRentalsForTravelAgencyApp";//删除房型
        public static final String getSaleCarDetail = root + "/car_rental/getDetailForTravalAgencyApp";//服务号获取酒店列表
        public static final String downSaleCar = root + "/car_rental/down";//服务号获取旅行社的某个酒店下的房间类型
        public static final String upSaleCar = root + "/car_rental/up";//服务号获取某个酒店下的房间类型详情


    }

    public static class Image {
        public static final String uploadImage = root + "/image/uploadImage";//上传图片
    }

    public static class Scenic {
        public static final String getList = root + "/scenic_spot/getList";//旅行社获取自身景点列表
        public static final String getListForCustomer = root + "/scenic_spot/getListForCustomer";//服务号获取景点列表
        public static final String getDetail = root + "/scenic_spot/getDetail";//获取景点详情
    }

    // /order/getOrdersForTravelAgency
    public static class Order {
        public static final String getOrdersForTravelAgency1 = root + "/order/getOrdersForTravelAgency";//旅行社用户订单获取订单列表
        public static final String getOrdersForTravelAgency = root + "/order_of_travel_agency/getOrdersForTravelAgency";//旅行社获取订单列表
        public static final String applyForTravelAgency = root + "/order_refund/applyForTravelAgency";//订单取消
        public static final String commetForTravelAgency = root + "/comment/addHotelCommentForTravelAgency";//订单评价

        public static final String getHotelOrderList=root+"/hotel/order_of_travel_agency/getListForTravelAgency";
        public static final String getMeetingOrderList = root + "/conference_room/order_of_travel_agency/getListForTravelAgency";//旅行社用户订单获取订单列表
        public static final String getCarOrderList=root+ "/car_rental/order_of_travel_agency/getListForTravelAgency";
        public static final String getPathOrderList = root + "/route/order_of_travel_agency/getListForTravelAgency";//旅行社用户订单获取订单列表
        public static final String getTicketsOrderList=root+ "/ticket/order_of_travel_agency/getListForTravelAgency";

        public static final String getHotelOrderListDetail=root+ "/hotel/order_of_travel_agency/getForTravelAgency";
        public static final String getMeetingOrderListDetail = root + "/conference_room/order_of_travel_agency/getForTravelAgency";//旅行社用户订单获取订单列表
        public static final String getCarOrderListDetail=root+ "/car_rental/order_of_travel_agency/getForTravelAgency";
        public static final String getPathOrderListDetail = root + "/route/order_of_travel_agency/getForTravelAgency";//旅行社用户订单获取订单列表
        public static final String getTicketsOrderListDetail=root+  "/ticket/order_of_travel_agency/getForTravelAgency";

        public static final String applyCancel=root+"/order_refund/applyForTravelAgency";
        public static final String applyComment=root+"/order_refund/applyForTravelAgency";
    }
    public static class UserOrder{
        public static final String getUserHotelOrderList = root + "/hotel/order/getListForTravelAgency";// 酒店获取内景图片
        public static final String getUserMeetOrderList = root + "/conference_room/order/getListForTravelAgency";// 酒店获取外景图片
        public static final String getUserPathOrderList = root + "/route/order/getListForTravelAgency";// 酒店获取图片数量
        public static final String getUserTicketsOrderList = root + "/ticket/order/getListForTravelAgency";// 酒店获取图片数量
        public static final String getUserCarOrderList=root+ "/car_rental_order/order/getListForTravelAgency";

        public static final String getUserHotelOrderDetail = root + "/hotel/order/getForTravelAgency";// 酒店获取内景图片
        public static final String getUserMeetOrderDetail = root + "/conference_room/order/getForTravelAgency";// 酒店获取外景图片
        public static final String getUserCarOrderDetail = root + "/car_rental_order/order/getForTravelAgency";// 酒店获取图片数量
        public static final String getUserPathOrderDetail = root + "/route/order/getForTravelAgency";// 酒店获取图片数量
        public static final String getUserTicketsOrderDetail=root + "/ticket/order/getForTravelAgency";

        public static final String agreeCancel = root + "/order_refund/agreeOrderForTravelAgency";// 酒店获取图片数量
        public static final String refuseCancel=root + "/order_refund/refuseOrderForTravelAgency";

        public static final String Ticketscomplete=root + "/ticket/order/finishForTravelAgency";
        public static final String Pathcomplete=root + "/route/order/finishForTravelAgency";
        public static final String Carcomplete=root + "/car_rental_order/order/finishForTravelAgency";
    }

    public static class Hotel {





        public static final String getForTravelAgency0 = root + "/hotel/order/getForTravelAgency";//用户获取酒店订单详情
        public static final String getForTravelAgency = root + "/hotel/order_of_travel_agency/getForTravelAgency";//旅行社获取酒店订单详情
        public static final String add = root + "/hotel/order_of_travel_agency/add";//添加订单
        public static final String get = root + "/hotel/get";//获取详情

        public static final String getInnerImage = root + "/hotel/getInnerImageByHotelId";// 酒店获取内景图片
        public static final String getOuterImage = root + "/hotel/getOuterImageByHotelId";// 酒店获取外景图片
        public static final String getImageNum = root + "/hotel/getImagesNumberByHotelId";// 酒店获取图片数量
        public static final String getStarList = root + "/hotel_rating/getForGet";// 酒店获取图片数量
        public static final String getDateList=root+ "/roomtype/price_pre_day/getEmptyNumAndPriceThisMonth";
        public static final String getMeetingDate=root+ "/conference_room/price_pre_day/getEmptyNumAndPriceThisMonth";
        public static final String getCardDate=root+"/car_rental/price_pre_day/getEmptyNumAndPriceThisMonth";
        public static final String getDefaultDate=root+ "/roomnum/getEmptyNumofRoomtype";

    }

    public static class Route {
        public static final String getList = root + "/route/getList";//旅行社获取路线景点列表
        public static final String getDetail = root + "/route/getDetail";//旅行社Web获取路线详情
        public static final String getPricePerDay = root + "/route/getPricePerDay";//旅行社获取路线当月的价格
        public static final String updatePriceOneDay = root + "/route/updatePriceOneDay";//旅行社设置路线当月的价格
        public static final String getForTravelAgency0 = root + "/route/order/getForTravelAgency";//旅行社获取路线订单详情
        public static final String getForTravelAgency = root + "/route/order_of_travel_agency/getForTravelAgency";//旅行社获取路线订单详情

        public static final String getListForCustomer = root + "/route/getListForCustomer";//服务号获取路线景点列表
        public static final String getPricePerDayForCustomer = root + "/route/getPricePerDayForCustomer";//服务号获取路线当月的价格
        public static final String getDetailForCustomer = root + "/route/getDetailForCustomer";//服务号获取路线详情
        public static final String add = root + "/route/order_of_travel_agency/add";//添加订单
    }

    public static class Ticket {
        ///ticket/getList
        public static final String getList = root + "/ticket/getList";//获取某个景点下的门票信息
        public static final String getDetail = root + "/ticket/getDetail";//获取门票详情
        public static final String getForTravelAgency0 = root + "/ticket/order/getForTravelAgency";//旅行社获取门票订单详情
        public static final String getForTravelAgency = root + "/ticket/order_of_travel_agency/getForTravelAgency";//旅行社获取门票订单详情
        public static final String getPricePerDay = root + "/ticket/getPricePerDay";//旅行社获取门票当月的价格
        public static final String updatePriceOneDay = root + "/ticket/updatePriceOneDay";//旅行社设置门票当月的价格
        public static final String getPricePerDayForCustomer = root + "/ticket/getPricePerDayForCustomer";//服务号获取门票当月的价格
        public static final String add = root + "/ticket/order_of_travel_agency/add";//添加订单


        public static final String getSaleTicketsList = root + "/scenic_spot/getListForTravelAgencyApp";//添加订单  "/scenic_spot/getListForTravelAgencyApp"
        public static final String getSaleTicketsDetail = root + "/scenic_spot/getDetailForTravalAgencyApp";//添加订单  "/scenic_spot/getListForTravelAgencyApp"
        public static final String getSaleTicketsDetailList = root +  "/ticket/getListForTravalAgencyApp";
        public static final String getSaleTicketsSubDetail = root +"/ticket/getDetailForTravelAgency";
        public static final String downSaleTickets =root+ "/ticket/down";
        public static final String upSaleTickets =root+ "/ticket/up";


        public static final String getPlatTicketsList = root + "/travel_agency/ticket/getScenicSpots";//添加订单
        public static final String getPlatTicketsDetail = root + "/travel_agency/ticket/getScenicSpot";//添加订单  "/scenic_spot/getListForTravelAgencyApp"
        public static final String getPlatTicketsDetailList = root +  "/travel_agency/ticket/getTickets";
        public static final String getPlatTicketsSubDetail = root +"/travel_agency/ticket/getDetail";
        public static final String downPlatTickets =root+ "/travel_agency/ticket/remove";
        public static final String upPlatickets =root+ "/travel_agency/ticket/adopt";
        public static final String getTicketDate =root+ "/ticket/price_pre_day/getEmptyNumAndPriceThisMonth";
        public static final String addPlatTicketOrder = root+ "/ticket/order_of_travel_agency/add";
        public static final String updatePlatTicketsPrice = root+ "/travel_agency/ticket/updatePrice";
        public static final String getDefaultDate=root+ "/ticket/num/getEmptyNum";
    }

    public static class CarRental {
        //
        ///carRental/getCarRentalByTravelAgencyId
        public static final String getCarRentalByTravelAgencyId = root + "/carRental/getCarRentalByTravelAgencyId";//app根据旅行社id获取租车列表数据
        public static final String getCarRental = root + "/carRental/getCarRental";//后台获取租车列表数据
        public static final String getCarRentalById = root + "/carRental/getCarRentalById";//后台根据记录id获取租车数据
        public static final String updateCarRental = root + "/carRental/updateCarRental";//更新租车数据
        public static final String getForTravelAgency0 = root + "/car_rental_order/order/getForTravelAgency";//获取租车订单详情
        public static final String getForTravelAgency = root + "/car_rental/order_of_travel_agency/getForTravelAgency";//获取租车订单详情
        public static final String add = root + "/car_rental/order_of_travel_agency/add";//添加订单
        public static final String getCarRentalByIdForApp = root + "/carRental/getCarRentalByIdForApp";//服务号根据记录id获取租车数据
        public static final String addCarOrder = root+"/car_rental/order_of_travel_agency/add";
        public static final String getCarOrder = root+"/car_rental/order_of_travel_agency/getListForTravelAgency";
        public static final String getDefaultDate=root+ "/car_rental/num/getEmptyNum";
    }
    public static class Meeting{
        public static final String getMeetingList = root + "/travel_agency/conference_room/getConferenceRooms";//旅行社获取会议室列表
        public static final String getMeetingRoomTypeList = root + "/travel_agency/conference_room/getConferenceRoomtype";//旅行社获取会议室列表
        public static final String getAdoptMeetingList = root + "/travel_agency/conference_room/getConferenceRoomTypesAdded";//旅行社获取会议室列表
        public static final String getMeetingDetail = root + "/travel_agency/conference_room/getDetail";//旅行社获取会议室列表
        public static final String removeMeeting = root + "/travel_agency/conference_room/remove";//旅行社获取会议室列表
        public static final String adoptMeeting = root + "/travel_agency/conference_room/adopt";//旅行社获取会议室列表
        public static final String updateMeetingPrice = root + "/travel_agency/conference_room/updatePrice";//旅行社获取会议室列表
        public static final String getRoomDetail = root + "/travel_agency/conference_room/getConferenceRoom";//旅行社获取会议室列表
        public static final String addOrder=root+"/conference_room/order_of_travel_agency/add";
        public static final String getDefaultDate=root+ "/conference/room_num/getEmptyNumofRoomtype";

    }
    public static class Path{
        public static final String getPlatPath = root + "/travel_agency/route/getroutes";//旅行社获取会议室列表
        public static final String getPlatPathDetail = root + "/travel_agency/route/getroute";//旅行社获取会议室列表
        public static final String adapotPlatPath = root + "/travel_agency/route/adopt";//旅行社获取会议室列表
        public static final String removePlatPath = root + "/travel_agency/route/remove";//旅行社获取会议室列表
        public static final String updatePlatPathPrice = root + "/travel_agency/route/updatePrice";//旅行社获取会议室列表

        public static final String getSalePath = root + "/route/getRoutesForTravelAgencyApp";//旅行社获取会议室列表
        public static final String getSalePathDetail = root + "/route/getDetailForTravalAgencyApp";//旅行社获取会议室列表
        public static final String adapotSalePath = root + "/route/up";//旅行社获取会议室列表
        public static final String removeSalePath = root + "/route/down";//旅行社获取会议室列表
        public static final String getPathDate = root + "/route/price_pre_day/getEmptyNumAndPriceThisMonth";
        public static final String addPathOrder=root+"/route/order_of_travel_agency/add";
        public static final String getDefaultDate=root+ "/route/num/getEmptyNum";
    }
    public static class Service{
        public static final String getBackService=root+ "/hx_chat/getRandomManagerForTa";
    }

}
