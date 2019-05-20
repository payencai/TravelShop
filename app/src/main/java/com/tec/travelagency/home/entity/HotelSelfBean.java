package com.tec.travelagency.home.entity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.common.myviewpager.CommonViewPager;
import com.tec.travelagency.common.myviewpager.ViewPagerHolderCreator;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseCell;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.home.activity.HotelSelfOrderDetailActivity;
import com.tec.travelagency.home.activity.ReserveHotelActivity;
import com.tec.travelagency.home.adapter.DetailsGvAdapter;
import com.tec.travelagency.home.adapter.ViewImageHolder;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.widget.CustomPopWindow;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 作者：凌涛 on 2018/9/5 15:03
 * 邮箱：771548229@qq..com
 */
public class HotelSelfBean extends RVBaseCell implements Serializable {

    private String id;
    private String roomtypeId;
    private String hotelId;
    private String travelAgencyId;
    private String isAdopted;
    private double travelAgencyPrice;
    private double managePrice;
    private String name;
    private String floor;
    private String bedType;
    private String image1;
    private String image1Url;
    private String createTime;
    private String liveNum;
    private String bathroom;
    private String breakfast;
    private String isCanCancel;
    private boolean isLoading = false;
    private String hotelName;
    private List<DataEntry> mVPImagerUrlList;

    public HotelSelfBean() {
        super(null);
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotel_self_rv_layout, parent, false);
        return new RVBaseViewHolder(view);
    }

    @Override
    public String toString() {
        return "HotelSelfBean{" +
                "id='" + id + '\'' +
                ", roomtypeId='" + roomtypeId + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", travelAgencyId='" + travelAgencyId + '\'' +
                ", isAdopted='" + isAdopted + '\'' +
                ", travelAgencyPrice=" + travelAgencyPrice +
                ", managePrice=" + managePrice +
                ", name='" + name + '\'' +
                ", floor='" + floor + '\'' +
                ", bedType='" + bedType + '\'' +
                ", image1='" + image1 + '\'' +
                ", image1Url='" + image1Url + '\'' +
                ", createTime='" + createTime + '\'' +
                ", liveNum='" + liveNum + '\'' +
                ", bathroom='" + bathroom + '\'' +
                ", breakfast='" + breakfast + '\'' +
                ", isCanCancel='" + isCanCancel + '\'' +
                ", isLoading=" + isLoading +
                ", hotelName='" + hotelName + '\'' +
                ", mVPImagerUrlList=" + mVPImagerUrlList +
                '}';
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position, final Context context, RVBaseAdapter adapter) {

        holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoading) {
                    return;
                }
                isLoading = true;
                mockData(context, v);
            }
        });

        holder.setText(R.id.name, name);
        holder.setText(R.id.scenicDes, bedType + "\t" + floor);
        holder.setText(R.id.breakfast, breakfast);
        holder.setText(R.id.cancel, isCanCancel);
        holder.setText(R.id.price, "¥" + travelAgencyPrice + "起");
    }


    private void showRoomDetailPw(Context context, View view, RoomTypeDesBean bean) {
        isLoading = false;
        View otherView = LayoutInflater.from(context).inflate(R.layout.pw_hotel_room_detail_layout2, null);
        CustomPopWindow customPopWindow = new CustomPopWindow.PopupWindowBuilder(context)
                .setView(otherView)
                .sizeByPercentage(context, 1f, 0)
                .setOutsideTouchable(true)
                .enableBackgroundDark(true)
                .setAnimationStyle(R.style.CustomPopWindowStyleFromBottom)
                .setBgDarkAlpha(0.5f)
                .create();
        customPopWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        handleRoomDetailView(context, otherView, customPopWindow, bean);
    }

    private void handleRoomDetailView(final Context context, View view, final CustomPopWindow customPopWindow, final RoomTypeDesBean bean) {
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customPopWindow != null) {
                    customPopWindow.dissmiss();
                }
            }
        });

        view.findViewById(R.id.reserve_now).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HotelSelfOrderDetailActivity activity = (HotelSelfOrderDetailActivity) context;

                Intent intent = new Intent(context, ReserveHotelActivity.class);
                int differenceTime = activity.mDifferenceTime;

                if (differenceTime <= 0) {
                    ToaskUtil.showToast(context, "请选择日期");
                    return;
                }
                intent.putExtra("hotelName", hotelName);
                intent.putExtra("RoomTypeDesBean", bean);
                intent.putExtra("HotelSelfBean", HotelSelfBean.this);
                intent.putExtra("startRealTime", activity.startRealTime);
                intent.putExtra("endRealTime", activity.endRealTime);
                intent.putExtra("mDifferenceTime", differenceTime);
                context.startActivity(intent);
                if (customPopWindow != null) {
                    customPopWindow.dissmiss();
                }

            }
        });
        ((TextView) view.findViewById(R.id.roomName)).setText(bean.getName());
        ((TextView) view.findViewById(R.id.isCanCancel)).setText(bean.getIsCanCancel());
        ((TextView) view.findViewById(R.id.price)).setText("¥" + bean.getTravelAgencyPrice());


        CommonViewPager viewpager = (CommonViewPager) view.findViewById(R.id.activity_common_view_pager);

        initPV(bean);
        initViewPager(viewpager);


        GridView detailsGv = view.findViewById(R.id.detailsGv);
        List<DetailsIntroduceBean> list = getGvList(bean);
        detailsGv.setAdapter(new DetailsGvAdapter(context, list));

    }

    private void initPV(RoomTypeDesBean bean) {
        mVPImagerUrlList = new LinkedList<>();
        String image1 = bean.getImage1Url();
        String image2 = bean.getImage2Url();
        String image3 = bean.getImage3Url();
        String image4 = bean.getImage4Url();
        String image5 = bean.getImage5Url();

        addToList(image1);
        addToList(image2);
        addToList(image3);
        addToList(image4);
        addToList(image5);
    }

    private void addToList(String url) {
        if (!TextUtils.isEmpty(url)) {
            if (!url.equals("null")) {
                mVPImagerUrlList.add(new DataEntry(url));
            }
        }
    }

    private void initViewPager(CommonViewPager mCommonViewPager) {

        // 设置数据
        mCommonViewPager.setPages(mVPImagerUrlList, new ViewPagerHolderCreator<ViewImageHolder>() {
            @Override
            public ViewImageHolder createViewHolder() {
                // 返回ViewPagerHolder
                return new ViewImageHolder();
            }
        });
    }

    private void mockData(final Context context, final View view) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", id);
        String travelAgencyId = App.getInstance().getUserEntity().getTravelAgencyId();
        Log.d("mockData", "mockData: " + travelAgencyId);
        parame.put("travelAgencyId", travelAgencyId);
        HttpProxy.obtain().get(PlatformContans.TravelAgency.getRoomtypeDetailForCustomer, parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("mockData", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        JSONObject item0 = data.getJSONObject("travelAgencyRoomtype");

                        double travelAgencyPrice = item0.getDouble("travelAgencyPrice");
                        String id = item0.getString("id");
                        JSONObject item = data.getJSONObject("roomtype");

                        RoomTypeDesBean bean = new Gson().fromJson(item.toString(), RoomTypeDesBean.class);
                        bean.setTravelAgencyRoomtypeId(id);
                        bean.setTravelAgencyPrice(travelAgencyPrice);
                        showRoomDetailPw(context, view, bean);
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

    private List<DetailsIntroduceBean> getGvList(RoomTypeDesBean roomDetail) {
        List<DetailsIntroduceBean> list = new ArrayList<>();
        list.add(new DetailsIntroduceBean("可住", roomDetail.getLiveNum() + "人"));
        list.add(new DetailsIntroduceBean("楼层", roomDetail.getFloor()));
        list.add(new DetailsIntroduceBean("床型", roomDetail.getBedType()));
        list.add(new DetailsIntroduceBean("窗户", roomDetail.getWindow()));
        list.add(new DetailsIntroduceBean("卫浴", roomDetail.getBathroom()));
        list.add(new DetailsIntroduceBean("上网", roomDetail.getIntnet()));
        list.add(new DetailsIntroduceBean("早餐", roomDetail.getBreakfast()));

        return list;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomtypeId() {
        return roomtypeId;
    }

    public void setRoomtypeId(String roomtypeId) {
        this.roomtypeId = roomtypeId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getTravelAgencyId() {
        return travelAgencyId;
    }

    public void setTravelAgencyId(String travelAgencyId) {
        this.travelAgencyId = travelAgencyId;
    }

    public String getIsAdopted() {
        return isAdopted;
    }

    public void setIsAdopted(String isAdopted) {
        this.isAdopted = isAdopted;
    }

    public double getTravelAgencyPrice() {
        return travelAgencyPrice;
    }

    public void setTravelAgencyPrice(double travelAgencyPrice) {
        this.travelAgencyPrice = travelAgencyPrice;
    }

    public double getManagePrice() {
        return managePrice;
    }

    public void setManagePrice(double managePrice) {
        this.managePrice = managePrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLiveNum() {
        return liveNum;
    }

    public void setLiveNum(String liveNum) {
        this.liveNum = liveNum;
    }

    public String getBathroom() {
        return bathroom;
    }

    public void setBathroom(String bathroom) {
        this.bathroom = bathroom;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getIsCanCancel() {
        return isCanCancel;
    }

    public void setIsCanCancel(String isCanCancel) {
        this.isCanCancel = isCanCancel;
    }
}
