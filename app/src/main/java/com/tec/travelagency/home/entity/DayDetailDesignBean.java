package com.tec.travelagency.home.entity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseCell;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.home.activity.HotelDetailActivity;
import com.tec.travelagency.home.activity.RentalSelfOrderDetailActivity;
import com.tec.travelagency.home.activity.ScenicSelfOrderDetailActivity;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 作者：凌涛 on 2018/9/6 15:00
 * 邮箱：771548229@qq..com
 */
public class DayDetailDesignBean extends RVBaseCell {

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
    private HotelNew2Bean mHotelNew2Bean;

    public DayDetailDesignBean() {
        super(null);
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //item_day_detail_design_rv_layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day_detail_design_rv_layout, parent, false);
        return new RVBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position, final Context context, RVBaseAdapter adapter) {

        holder.setText(R.id.title, title);
        int count = position + 1;
        holder.setText(R.id.days_text, "day" + count);

        holder.setText(R.id.list_address_text, "集合地点:" + assemblingPlace);
        holder.setText(R.id.list_time_text, "时间:" + timeAgreement);
        holder.setText(R.id.hotel_room_type, roomtypeName);
        holder.setText(R.id.transportation, transportation);
        holder.setText(R.id.traffic_des, "行驶时间:" + runningTime);
        holder.setText(R.id.runningPath, runningPath);

        TextView localText = holder.getTextView(R.id.local);
        RecyclerView rentalCarRv = (RecyclerView) holder.getView(R.id.rental_car_rv);
        if (TextUtils.isEmpty(carRentalId) || carRentalId.equals("null") || carRentalId.equals("NULL")) {
            localText.setVisibility(View.GONE);
            rentalCarRv.setVisibility(View.GONE);
        } else {
            localText.setVisibility(View.VISIBLE);
            rentalCarRv.setVisibility(View.VISIBLE);
        }
        ImageView hotelImg = holder.getImageView(R.id.hotel_img);
        TextView holderTextView = holder.getTextView(R.id.hotel_name);
        //请求酒店数据
        //  TODO
        RelativeLayout hotelItem = (RelativeLayout) holder.getView(R.id.hotelItem);
        requestHotelDate(holder, hotelImg, holderTextView,hotelItem);

        hotelItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHotelNew2Bean == null) {
                    ToaskUtil.showToast(context, "数据加载中,请稍等...");
                    return;
                }
                HotelDetailActivity.startHotelDetail(context, mHotelNew2Bean);
            }
        });


        RecyclerView scenicRv = (RecyclerView) holder.getView(R.id.scenic_rv);

        List<ScenicDoorTicker> list = new LinkedList<>();
        RVBaseAdapter<ScenicDoorTicker> baseAdapter = new RVBaseAdapter<ScenicDoorTicker>() {
            @Override
            protected void onViewHolderBound(RVBaseViewHolder holder, int position) {

            }
        };
        baseAdapter.setDataByRemove(list);
        scenicRv.setLayoutManager(new LinearLayoutManager(context));
        scenicRv.setAdapter(baseAdapter);
        String[] split = scenicSpotId.split(",");
        for (String id : split) {
            requestScenicList(baseAdapter, id);
        }


        List<RentalCarBean> carList = new LinkedList<>();
        RVBaseAdapter<RentalCarBean> carBaseAdapter = new RVBaseAdapter<RentalCarBean>() {
            @Override
            protected void onViewHolderBound(RVBaseViewHolder holder, int position) {

            }
        };
        carBaseAdapter.setDataByRemove(carList);
        rentalCarRv.setLayoutManager(new LinearLayoutManager(context));
        rentalCarRv.setAdapter(carBaseAdapter);
        if (TextUtils.isEmpty(carRentalId)) {
            return;
        }
        String[] carSplit = carRentalId.split(",");
        for (String id : carSplit) {
            requestRentalCarList(carBaseAdapter, id);
        }

    }

    /**
     * 请求景区列表
     * @param baseAdapter
     * @param id
     */
    private void requestScenicList(final RVBaseAdapter<ScenicDoorTicker> baseAdapter, String id) {
        if (TextUtils.isEmpty(id)) {
            return;
        }
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", id);
        HttpProxy.obtain().post(PlatformContans.Scenic.getDetail, parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("getDetailLingtao", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        Gson gson = new Gson();
                        ScenicDoorTicker bean = gson.fromJson(data.toString(), ScenicDoorTicker.class);
                        ScenicSelfOrderBean selfOrderBean = gson.fromJson(data.toString(), ScenicSelfOrderBean.class);
                        //这里设置的是为了进入景点详细页面需要的bean
                        bean.setScenicSelfOrderBean(selfOrderBean);
                        List<ScenicDoorTicker> list = new LinkedList<>();
                        list.add(bean);
                        baseAdapter.setData(list);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {

            }
        });

    }

    private void requestRentalCarList(final RVBaseAdapter<RentalCarBean> baseAdapter, String id) {
        if (TextUtils.isEmpty(id)) {
            return;
        }
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", id);
        HttpProxy.obtain().get(PlatformContans.CarRental.getCarRentalByIdForApp, parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("RentalCarBean", result);

                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        Gson gson = new Gson();
                        RentalSelfOrderBean selfOrderBean = gson.fromJson(data.toString(), RentalSelfOrderBean.class);
                        RentalCarBean bean = gson.fromJson(data.toString(), RentalCarBean.class);
                        bean.setRentalSelfOrderBean(selfOrderBean);
                        List<RentalCarBean> list = new LinkedList<>();
                        list.add(bean);
                        baseAdapter.setData(list);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {

            }
        });

    }


    /**
     * 请求酒店数据
     */
    private void requestHotelDate(RVBaseViewHolder holder, final ImageView hotelImg, final TextView holderTextView, final RelativeLayout hotelItem) {
        final Context context = holder.getItemView().getContext();
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", hotelId);
        HttpProxy.obtain().get(PlatformContans.Hotel.get, parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("lingtaoshiwo", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONObject item = object.getJSONObject("data").getJSONObject("hotel");
                        mHotelNew2Bean = new Gson().fromJson(item.toString(), HotelNew2Bean.class);
                        holderTextView.setText(mHotelNew2Bean.getName());
                        Glide.with(context).load(mHotelNew2Bean.getImage1Url()).into(hotelImg);
                    } else {
                        hotelItem.setVisibility(View.GONE);
//                        ToaskUtil.showToast(context, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {

            }
        });

    }

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

    public static class ScenicDoorTicker extends RVBaseCell {




        private String id;
        private String travelAgencyId;
        private String name;
        private String introduce;
        private String address;
        private String longitude;
        private String latitude;
        private String addressDetail;
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
        private String createTime;
        private String isDeleted;
        private CommentDataBean commentData;
        private ScenicSelfOrderBean mScenicSelfOrderBean;


        public ScenicDoorTicker() {
            super(null);
        }

        @Override
        public int getItemType() {
            return 0;
        }

        @Override
        public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scenic_door_ticker_rv_layout, parent, false);
            return new RVBaseViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RVBaseViewHolder holder, int position, final Context context, RVBaseAdapter adapter) {
            holder.setText(R.id.name, name);
            holder.setText(R.id.des, introduce);
            ImageView imageView1 = holder.getImageView(R.id.car_img1);
            ImageView imageView2 = holder.getImageView(R.id.car_img2);
            Glide.with(context).load(image1Url).into(imageView1);
            Glide.with(context).load(image2Url).into(imageView2);

            holder.getView(R.id.rental_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mScenicSelfOrderBean == null) {
                        ToaskUtil.showToast(context, "未知错误");
                        return;
                    }
                    Intent intent = new Intent(context, ScenicSelfOrderDetailActivity.class);
                    intent.putExtra("ScenicSelfOrderBean", mScenicSelfOrderBean);
                    context.startActivity(intent);
                }
            });

        }


        public ScenicSelfOrderBean getScenicSelfOrderBean() {
            return mScenicSelfOrderBean;
        }

        public void setScenicSelfOrderBean(ScenicSelfOrderBean scenicSelfOrderBean) {
            mScenicSelfOrderBean = scenicSelfOrderBean;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTravelAgencyId() {
            return travelAgencyId;
        }

        public void setTravelAgencyId(String travelAgencyId) {
            this.travelAgencyId = travelAgencyId;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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


        public static class CommentDataBean implements Serializable{
            /**
             * score : 5
             * number : 0
             * rate : 1
             */

            private double score;
            private int number;
            private double rate;

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
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

    public static class RentalCarBean extends RVBaseCell {

        private String id;
        private String name;
        private String address;
        private String telephone;
        private String longitude;
        private String latitude;
        private String addressDetail;
        private double price;
        private int seatNumber;
        private String specification;
        private String introduce;
        private String images;
        private String coverImage;
        private String carType;
        private String travelAgencyId;
        private String isCanCancel;
        private Object sequence;
        private String isDeleted;
        private String createTime;
        private CommentDataBean commentData;

        private RentalSelfOrderBean mRentalSelfOrderBean;


        public RentalCarBean() {
            super(null);
        }

        @Override
        public int getItemType() {
            return 0;
        }

        @Override
        public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daydetail_rantalcar_rv_layout, parent, false);
            return new RVBaseViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RVBaseViewHolder holder, int position, final Context context, RVBaseAdapter adapter) {
            holder.setText(R.id.local_des, name + ":" + introduce);
            ImageView imageView1 = holder.getImageView(R.id.car_img1);
            ImageView imageView2 = holder.getImageView(R.id.car_img2);

            String[] split = images.split(",");
            String image1Url = "";
            String image2Url = "";
            try {
                image1Url = split[0];
            } catch (IndexOutOfBoundsException e) {

            }

            try {
                image1Url = split[1];
            } catch (IndexOutOfBoundsException e) {

            }

            Glide.with(context).load(image1Url).into(imageView1);
            Glide.with(context).load(image2Url).into(imageView2);

            holder.getView(R.id.rental_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRentalSelfOrderBean == null) {
                        ToaskUtil.showToast(context, "未知错误");
                        return;
                    }
                    Intent intent = new Intent(context, RentalSelfOrderDetailActivity.class);
                    intent.putExtra("RentalSelfOrderBean", mRentalSelfOrderBean);
                    context.startActivity(intent);
                }
            });
        }


        public RentalSelfOrderBean getRentalSelfOrderBean() {
            return mRentalSelfOrderBean;
        }

        public void setRentalSelfOrderBean(RentalSelfOrderBean rentalSelfOrderBean) {
            mRentalSelfOrderBean = rentalSelfOrderBean;
        }

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

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
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

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
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

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
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

        public String getIsCanCancel() {
            return isCanCancel;
        }

        public void setIsCanCancel(String isCanCancel) {
            this.isCanCancel = isCanCancel;
        }

        public Object getSequence() {
            return sequence;
        }

        public void setSequence(Object sequence) {
            this.sequence = sequence;
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

        public static class CommentDataBean implements Serializable {

            private double score;
            private int number;
            private double rate;

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
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
