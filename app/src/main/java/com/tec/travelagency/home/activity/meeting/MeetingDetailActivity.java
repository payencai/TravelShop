package com.tec.travelagency.home.activity.meeting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.google.gson.Gson;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.baiduMap.LocationService;
import com.tec.travelagency.baiduMap.RoutePlanDemo;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.common.myviewpager.CommonViewPager;
import com.tec.travelagency.common.myviewpager.ViewPagerHolderCreator;
import com.tec.travelagency.eventBusBean.UpdateHotelList;
import com.tec.travelagency.home.activity.CommentActivity;

import com.tec.travelagency.home.activity.HotelImageActivity;

import com.tec.travelagency.home.adapter.DetailsGvAdapter;
import com.tec.travelagency.home.adapter.HotelHeadImgAdapter;
import com.tec.travelagency.home.adapter.HotelRoomDetailAdapter;
import com.tec.travelagency.home.adapter.MeetingAdapter;
import com.tec.travelagency.home.adapter.ViewImageHolder;
import com.tec.travelagency.home.entity.DataEntry;
import com.tec.travelagency.home.entity.DetailsIntroduceBean;
import com.tec.travelagency.home.entity.HotelNew2Bean;
import com.tec.travelagency.home.entity.HotelNew3Bean;
import com.tec.travelagency.home.entity.HotelRoom;
import com.tec.travelagency.home.entity.HotelRoomDetail;
import com.tec.travelagency.home.entity.HotelSelfBean;
import com.tec.travelagency.home.entity.IHotelNewBean;
import com.tec.travelagency.home.entity.MeetDelBean;
import com.tec.travelagency.home.entity.MeetingBean;
import com.tec.travelagency.home.entity.MeetingModel;
import com.tec.travelagency.home.entity.RoomTypeDesBean;
import com.tec.travelagency.home.rvItemCallBack.RvItemActionListener;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.LoginSharedUilt;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.widget.ButtomDialogView;
import com.tec.travelagency.widget.CustomDatePicker;
import com.tec.travelagency.widget.CustomPopWindow;
import com.tec.travelagency.widget.KyLoadingBuilder;
import com.tec.travelagency.widget.StarLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MeetingDetailActivity extends BaseActivity implements RvItemActionListener {

    @BindView(R.id.hotel_viewpager)
    ViewPager hotelViewpager;
    @BindView(R.id.hotel_room_rv)
    RecyclerView hotelRoomRv;

    @BindView(R.id.cur_viewpager_number)
    TextView curViewpagerNum;
    @BindView(R.id.hotelName)
    TextView hotelName;
    @BindView(R.id.hotelTel)
    TextView hotelTel;
    @BindView(R.id.HotelInfo)
    TextView HotelInfo;

    @BindView(R.id.Star)
    StarLinearLayout Star;
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.evaluate)
    TextView evaluate;
    @BindView(R.id.scale)
    TextView scale;
    @BindView(R.id.callImg)
    ImageView callImg;

    //    @BindView(R.id.instr)
//    TextView instr;
    @BindView(R.id.addressDetail)
    TextView addressDetail;

    @BindView(R.id.checkInTime)
    TextView checkInTime;
    @BindView(R.id.checkOutTime)
    TextView checkOutTime;
    @BindView(R.id.days_text)
    TextView diffDay;
    @BindView(R.id.abstract_des)
    TextView abstractDes;
    @BindView(R.id.timeLayout)
    RelativeLayout time;
    private CustomDatePicker startTimePicker;
    private CustomDatePicker endTimePicker;

    /**
     * 住宿天数
     */
    public int mDifferenceTime;


    private List<MeetingBean> mList = new ArrayList<>();
    private KyLoadingBuilder mOpenUpdatePriceView;

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    //    private List<DetailsIntroduceBean> mDetailsIntroduceBeanlist;
    private MeetingModel mMeetingModel;
    private List<String> mVPImagerUrlList;
    private List<HotelRoom> mHotelRoomList;
    private MeetingAdapter mRoomDetailAdapter;
    private KyLoadingBuilder mOpenAdoptView;
    private KyLoadingBuilder mOpenCancelView;

    private final int SDK_PERMISSION_REQUEST = 127;
    private String permissionInfo;

    private LocationService locationService;


//    public static void startMeetingDetail(Context context, IHotelNewBean bean) {
//        Intent intent = new Intent(context, MeetingDetailActivity.class);
//        intent.putExtra("hotelNew2Bean", bean);
//        context.startActivity(intent);
//    }
    public static void startDetail(Context context, MeetingModel bean) {
        Intent intent = new Intent(context, MeetingDetailActivity.class);
        intent.putExtra("MeetingModel", bean);

        context.startActivity(intent);
    }
    @Override
    protected int getContentId() {
        locationService = App.getInstance().locationService;
        locationService.registerListener(myListener);
        return R.layout.activity_hotel_detail;
    }

    @Override
    protected void initView() {
        final Intent intent = getIntent();
        mMeetingModel = (MeetingModel) intent.getSerializableExtra("MeetingModel");

        mVPImagerUrlList = new ArrayList<>();
        if (mMeetingModel instanceof MeetingModel) {
            MeetingModel hotelNew2Or3Bean = (MeetingModel) mMeetingModel;
            initUI(hotelNew2Or3Bean);
        }

        if (mMeetingModel instanceof MeetingModel) {
            MeetingModel hotelNew2Or3Bean = (MeetingModel) mMeetingModel;
            initUI(hotelNew2Or3Bean);
        }

        abstractDes.setText(mMeetingModel.getOneWord());
        time.setVisibility(View.GONE);
        initStartDatePicker();

        initRv();
        requestHotelRoomData();
        getVPImagerUrlList();

        HotelHeadImgAdapter hotelHeadImgAdapter = new HotelHeadImgAdapter(this, mVPImagerUrlList);
        hotelHeadImgAdapter.setOnClickImageListener(new HotelHeadImgAdapter.OnClickImageListener() {
            @Override
            public void onClick(Context context) {

            }
        });
        hotelViewpager.setAdapter(hotelHeadImgAdapter);
        hotelViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                curViewpagerNum.setText((position + 1) + "/" + mVPImagerUrlList.size());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        hotelName.setText(mMeetingModel.getName());
        hotelTel.setText("电话:" + mMeetingModel.getContactNumber());
        HotelInfo.setText("酒店信息");
        addressDetail.setText(mMeetingModel.getProvince() + mMeetingModel.getCity() + mMeetingModel.getDistrict() + mMeetingModel.getAddressDetail());

        initData();
    }

    //真实的时间格式
    public String startRealTime;
    public String endRealTime;

    //弹出选择时间
    private void initStartDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        //年月日
        String s = now.replace(" ", "_").split("_")[0];
        startRealTime = s;//2018-10-08
        checkInTime.setText("入住 " + s);
        initEndDatePicker(now);

        startTimePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                String s = time.replace(" ", "_").split("_")[0];
                startRealTime = s;
                checkInTime.setText("入住 " + s);
                initEndDatePicker(time);

//                int differenceTime = getDifferenceTime();
//                if (differenceTime <= 0) {
//                    diffDay.setText("时间选择错误");
//                } else {
//                    diffDay.setText("共" + differenceTime + "晚");
//                    requestData();
//                }
            }
        }, now, "2100-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        startTimePicker.showSpecificTime(false); // 显示时和分
        startTimePicker.setIsLoop(false); // 允许循环滚动
    }

    //弹出选择时间
    private void initEndDatePicker(String startTime) {
        if (endTimePicker != null) {
            endTimePicker = null;
        }
        String s = startTime.replace(" ", "_").split("_")[0];
        endRealTime = s;
        checkOutTime.setText("离店 " + s);
        mDifferenceTime = getDifferenceTime();
        if (mDifferenceTime <= 0) {
            diffDay.setText("共" + 0 + "晚");
        } else {
            diffDay.setText("共" + mDifferenceTime + "晚");
        }

        endTimePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                String s = time.replace(" ", "_").split("_")[0];
                endRealTime = s;
                checkOutTime.setText("离店 " + s);
                mDifferenceTime = getDifferenceTime();
                Log.d("handle", "handle: " + mDifferenceTime);
                if (mDifferenceTime <= 0) {
                    diffDay.setText("共" + 0 + "晚");
                } else {
                    diffDay.setText("共" + mDifferenceTime + "晚");
                }

            }
        }, startTime, "2100-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        endTimePicker.showSpecificTime(false); // 显示时和分
        endTimePicker.setIsLoop(false); // 允许循环滚动

    }

    private int getDifferenceTime() {
        String s1 = startRealTime + " 00:00:00";
        String s2 = endRealTime + " 00:00:00";
        int i = 0;
        try {
            i = daysBetween(s1, s2);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 字符串的日期格式的计算
     */
    public static int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    private void initUI(MeetingModel hotelNew2Or3Bean) {
        Star.setScore((float) hotelNew2Or3Bean.getCommentData().getScore());
        score.setText(hotelNew2Or3Bean.getCommentData().getScore() + "分");
        evaluate.setText(hotelNew2Or3Bean.getCommentData().getNumber() + "条评论");
        double rate = hotelNew2Or3Bean.getCommentData().getRate();
        scale.setText("好评率: " + (rate * 100) + "%");
    }



    private void requestHotelRoomData() {
        Map<String, Object> parame = new HashMap<>();
        parame.put("conferenceRoomId", mMeetingModel.getId());
        HttpProxy.obtain().get(PlatformContans.Meeting.getMeetingRoomTypeList, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {


            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("geDetailtHotels", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {

                        JSONArray data = object.getJSONArray("data");
                        Gson gson = new Gson();
                        int length = data.length();
                        mList.clear();
                        for (int i = 0; i < length; i++) {
                            JSONObject item = data.getJSONObject(i);
                            MeetingBean bean = gson.fromJson(item.toString(), MeetingBean.class);
                            mList.add(bean);
                        }
                        mRoomDetailAdapter.notifyDataSetChanged();

                    } else {
                        ToaskUtil.showToast(MeetingDetailActivity.this, message);
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

    private void initData() {
//        mDetailsIntroduceBeanlist = new ArrayList<>();
//        mDetailsIntroduceBeanlist.add(new DetailsIntroduceBean("可住", "2人"));
//        mDetailsIntroduceBeanlist.add(new DetailsIntroduceBean("楼层", "8楼"));
//        mDetailsIntroduceBeanlist.add(new DetailsIntroduceBean("床型", "双人1.8米"));
//        mDetailsIntroduceBeanlist.add(new DetailsIntroduceBean("窗户", "有"));
//        mDetailsIntroduceBeanlist.add(new DetailsIntroduceBean("卫浴", "独立"));
//        mDetailsIntroduceBeanlist.add(new DetailsIntroduceBean("上网", "wifi"));
//        mDetailsIntroduceBeanlist.add(new DetailsIntroduceBean("早餐", "有"));

    }

    private void initRv() {
        hotelRoomRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        hotelRoomRv.setLayoutManager(new LinearLayoutManager(this));
        mRoomDetailAdapter = new MeetingAdapter(this, mList);
        mRoomDetailAdapter.setOnItemActionListener(this);
        hotelRoomRv.setAdapter(mRoomDetailAdapter);
    }


    /**
     * 请求房间类型详情接口
     *
     * @param id
     * @param roomDetail
     */
    private void getRoomTypeDetail(String id, final MeetingBean roomDetail) {
        Map<String, Object> map = new HashMap<>();

        map.put("id", id);
        HttpProxy.obtain().post(PlatformContans.Meeting.getMeetingDetail, App.getInstance().getUserEntity().getToken(), map, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("getRoomtypeDetail", result);
                Log.e("detail", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        MeetDelBean bean = new Gson().fromJson(data.toString(), MeetDelBean.class);
                        showRoomDetailDialog(roomDetail, bean);
                        // showRoomDetailPw(hotelRoomRv, roomDetail, bean);

                    } else {

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

    private void showRoomDetailDialog(MeetingBean roomDetail, MeetDelBean meetDelBean) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_meeting, null);
        ButtomDialogView dialogView = new ButtomDialogView(this, view, Gravity.BOTTOM);
        dialogView.show();
        setDialogData(view, roomDetail, meetDelBean, dialogView);

    }

    private void setDialogData(View view, final MeetingBean meetingBean, final MeetDelBean meetDelBean, final Dialog dialog) {
        CommonViewPager pager = (CommonViewPager) view.findViewById(R.id.room_type_banner);
        List<DataEntry> bannerListUrls = getBannerListUrl(meetDelBean);
        pager.setPages(bannerListUrls, new ViewPagerHolderCreator<ViewImageHolder>() {
            @Override
            public ViewImageHolder createViewHolder() {
                // 返回ViewPagerHolder
                return new ViewImageHolder();
            }
        });
        //开启轮播
        pager.setOpenCarousel(true);
        TextView roomname = view.findViewById(R.id.roomName);
        TextView periodOfValidity = view.findViewById(R.id.periodOfValidity);
        TextView regulation = view.findViewById(R.id.regulation);
        TextView warmPrompt = view.findViewById(R.id.warmPrompt);

        TextView platform_price = view.findViewById(R.id.platform_price);
        TextView selling_price = view.findViewById(R.id.selling_price);
        TextView adapot = view.findViewById(R.id.sold_out);
        TextView buy = view.findViewById(R.id.oneselfBuy);
        TextView updatePrice = view.findViewById(R.id.updatePrice);
        final ImageView cancel = view.findViewById(R.id.cancel);
        platform_price.setText("平台价: ￥" + meetDelBean.getTravelAgencyConferenceRoom().getPlatformPrice());
        roomname.setText(meetDelBean.getConferenceRoomType().getTypeName());
        periodOfValidity.setText(meetDelBean.getConferenceRoomType().getPeriodOfValidity());
        regulation.setText(meetDelBean.getConferenceRoomType().getRegulation());
        warmPrompt.setText(meetDelBean.getConferenceRoomType().getWarmPrompt());
        if (meetDelBean.getTravelAgencyConferenceRoom().getIsAdopted().equals("1")) {
            selling_price.setText("售价: ￥" + meetDelBean.getTravelAgencyConferenceRoom().getTravelAgencyPrice());
            selling_price.setVisibility(View.VISIBLE);
            adapot.setText("下架");
            updatePrice.setVisibility(View.VISIBLE);
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        updatePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(meetingBean, meetDelBean, 1);
            }
        });
        adapot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(meetDelBean.getTravelAgencyConferenceRoom().getIsAdopted().equals("2"))
                   showDialog(meetingBean, meetDelBean, 0);
                else if(meetDelBean.getTravelAgencyConferenceRoom().getIsAdopted().equals("1")){
                   cancel(meetDelBean);
                }
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog.dismiss();
               TakeOrderActivity.startHotelDetail(dialog.getContext(),meetDelBean, meetingBean.getName());
            }
        });

    }

    private void showDialog(final MeetingBean roomDetail, final MeetDelBean hotelRoomTypeDetail, final int type) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_updateprice, null);
        final ButtomDialogView dialogView = new ButtomDialogView(this, view, Gravity.CENTER);
        dialogView.show();
        Window window = dialogView.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.CENTER);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        //window.setWindowAnimations(R.style.mypopwindow_anim_style);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        Display display = getWindowManager().getDefaultDisplay();
        //设置窗口宽度为充满全屏
        lp.width = (int) (display.getWidth() * 0.8);
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        TextView cancel = view.findViewById(R.id.cancel);
        TextView confrim = view.findViewById(R.id.confirm);
        final EditText et_price = view.findViewById(R.id.et_price);
        TextView helpprice = view.findViewById(R.id.helpprice);

        helpprice.setText("￥" + hotelRoomTypeDetail.getConferenceRoomType().getManagePriceFloor() + "-" + "￥" + hotelRoomTypeDetail.getConferenceRoomType().getManagePriceCeiling());
        TextView platprice = view.findViewById(R.id.platprice);
        platprice.setText("￥" + hotelRoomTypeDetail.getTravelAgencyConferenceRoom().getPlatformPrice());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
            }
        });
        confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
                price = Double.parseDouble(et_price.getEditableText().toString());
                if (type == 0){
                    mOpenAdoptView = openLoadView("");
                    adopt(hotelRoomTypeDetail);}
                else if (type == 1) {
                    mOpenUpdatePriceView=openLoadView("");
                    updateRoomPrice(hotelRoomTypeDetail,price);
                }
            }
        });


    }



    private List<DataEntry> getBannerListUrl(MeetDelBean hotelRoomTypeDetail) {
        String image1Url = hotelRoomTypeDetail.getConferenceRoomType().getImage1Url();
        String image2Url = hotelRoomTypeDetail.getConferenceRoomType().getImage2Url();
        String image3Url = hotelRoomTypeDetail.getConferenceRoomType().getImage3Url();
        String image4Url = hotelRoomTypeDetail.getConferenceRoomType().getImage4Url();
        String image5Url = hotelRoomTypeDetail.getConferenceRoomType().getImage5Url();
        List<DataEntry> list = new ArrayList<>();
        if (isCanAdd(image1Url)) {
            list.add(new DataEntry(image1Url));
        }
        if (isCanAdd(image2Url)) {
            list.add(new DataEntry(image2Url));
        }
        if (isCanAdd(image3Url)) {
            list.add(new DataEntry(image3Url));
        }
        if (isCanAdd(image4Url)) {
            list.add(new DataEntry(image4Url));
        }
        if (isCanAdd(image5Url)) {
            list.add(new DataEntry(image5Url));
        }

        for (DataEntry dataEntry : list) {

            Log.d("getBannerListUrl", "getBannerListUrl: " + dataEntry.imageUrl);
        }

        return list;
    }

    private boolean isCanAdd(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        if (url.equals("null")) {
            return false;
        }
        if (url.equals("NULL")) {
            return false;
        }
        return true;
    }

    private List<DetailsIntroduceBean> getGvList(RoomTypeDesBean roomDetail) {
        List<DetailsIntroduceBean> list = new ArrayList<>();
        list.add(new DetailsIntroduceBean("可住", roomDetail.getLiveNum() + "人"));
        list.add(new DetailsIntroduceBean("楼层", roomDetail.getFloor()));
        list.add(new DetailsIntroduceBean("床型", roomDetail.getBedType()));
        list.add(new DetailsIntroduceBean("窗户", "有"));
        list.add(new DetailsIntroduceBean("卫浴", roomDetail.getBathroom()));
        list.add(new DetailsIntroduceBean("上网", roomDetail.getIntnet()));
        list.add(new DetailsIntroduceBean("早餐", roomDetail.getBreakfast()));

        return list;
    }



    @OnClick({R.id.back, R.id.HotelInfo, R.id.relative1, R.id.callImg, R.id.checkInTime, R.id.checkOutTime
            , R.id.addressLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.HotelInfo:
                break;
            case R.id.relative1:
                CommentActivity.startCommentActivity(this, 5, mMeetingModel.getId());
                break;
            case R.id.callImg:
                String contactNumber = mMeetingModel.getContactNumber();
                if (TextUtils.isEmpty(contactNumber)) {
                    ToaskUtil.showToast(this, "号码为空");
                    return;
                }
                if (contactNumber.length() != 11) {
                    ToaskUtil.showToast(this, "号码格式错误");
                    return;
                }
                showCallPhoneView(view);
                break;
            case R.id.checkInTime:
                startTimePicker.show(startRealTime);
                break;
            case R.id.checkOutTime:
                endTimePicker.show(endRealTime);
                break;
            case R.id.addressLayout:

                getPersimmions();
                break;
        }
    }

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            } else {
                startNavigation();
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(permission)) {
                Log.d("addPermission", "addPermission: 111111");
                return true;
            } else {
                Log.d("addPermission", "addPermission: 22222");
                permissionsList.add(permission);
                return false;
            }

        } else {
            Log.d("addPermission", "addPermission: 333333");
            return true;
        }
    }


    private void startNavigation() {
        LoginSharedUilt intance = LoginSharedUilt.getIntance(this);
        double lat = intance.getLat();
        double lon = intance.getLon();
        if (lat == 0 || lon == 0) {
            ToaskUtil.showToast(this, "正在定位中...");
            return;
        }

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        intent.putExtra("bundle", bundle);

        double latValue = Double.parseDouble(mMeetingModel.getLatitude());
        double lonValue = Double.parseDouble(mMeetingModel.getLongitude());
        Log.d("onViewClicked", "目标维度: " + latValue);
        Log.d("onViewClicked", "目标经度: " + lonValue);

        Log.d("onViewClicked", "开始维度: " + lat);
        Log.d("onViewClicked", "开始经度: " + lon);

        bundle.putDouble("latNumber", latValue);//目标维度
        bundle.putDouble("lonNumber", lonValue);//目标经度
        bundle.putString("currentCity", mMeetingModel.getCity());
        bundle.putString("startNodeStr", "");
        bundle.putDouble("startLat", lat);//开始维度
        bundle.putDouble("startLon", lon);//开始经度
        intent.setClass(this, RoutePlanDemo.class);
        startActivity(intent);
    }

    public void getVPImagerUrlList() {

        String image1 = mMeetingModel.getImage1Url();
        String image2 = mMeetingModel.getImage2Url();
        String image3 = mMeetingModel.getImage3Url();
        String image4 = mMeetingModel.getImage4Url();
        String image5 = mMeetingModel.getImage5Url();
        String image6 = mMeetingModel.getImage6Url();

        addToList(image1);
        addToList(image2);
        addToList(image3);
        addToList(image4);
        addToList(image5);
        addToList(image6);


    }

    private void addToList(String url) {
        if (!TextUtils.isEmpty(url)) {
            if (!url.equals("null")) {
                mVPImagerUrlList.add(url);
            }
        }
    }

    /**
     * 修改房间类型价格
     *
     * @param roomDetail
     * @param price
     */
    private void updateRoomPrice(MeetDelBean roomDetail, double price) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", roomDetail.getTravelAgencyConferenceRoom().getId());
        parame.put("travelAgencyPrice", price);
        Log.e("param",parame.toString());
        HttpProxy.obtain().post(PlatformContans.Meeting.updateMeetingPrice, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                closeLoadView(mOpenUpdatePriceView);
                LogUtil.Log("updatePrice", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        ToaskUtil.showToast(MeetingDetailActivity.this, "修改成功");
                        requestHotelRoomData();
                    } else {
                        ToaskUtil.showToast(MeetingDetailActivity.this, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {
                closeLoadView(mOpenUpdatePriceView);

            }
        });

    }

    private double price;

    /**
     * 添加房型
     *
     * @param roomDetail
     */
    private void adopt(MeetDelBean roomDetail) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", roomDetail.getTravelAgencyConferenceRoom().getId());
        parame.put("price", price);
        HttpProxy.obtain().post(PlatformContans.Meeting.adoptMeeting, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("adopt", result);
                closeLoadView(mOpenAdoptView);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        ToaskUtil.showToast(MeetingDetailActivity.this, "上架成功");
                        requestHotelRoomData();
                        EventBus.getDefault().post(new UpdateHotelList());
                    } else {
                        ToaskUtil.showToast(MeetingDetailActivity.this, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {
                closeLoadView(mOpenAdoptView);

            }
        });

    }

    /**
     * 添加房型
     *
     * @param roomDetail
     */
    private void cancel(MeetDelBean roomDetail) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", roomDetail.getTravelAgencyConferenceRoom().getId());
        HttpProxy.obtain().post(PlatformContans.Meeting.removeMeeting, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("cancel", result);
                closeLoadView(mOpenCancelView);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        ToaskUtil.showToast(MeetingDetailActivity.this, "下架成功");
                        requestHotelRoomData();
                        EventBus.getDefault().post(new UpdateHotelList());
                    } else {
                        ToaskUtil.showToast(MeetingDetailActivity.this, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {
                closeLoadView(mOpenCancelView);

            }
        });

    }

//    public static class DetailsGvAdapter extends BaseAdapter {
//
//        private LayoutInflater inflater;
//        private List<DetailsIntroduceBean> list;
//
//        public DetailsGvAdapter(Context context, List<DetailsIntroduceBean> list) {
//            this.list = list;
//            inflater = LayoutInflater.from(context);
//        }
//
//        @Override
//        public int getCount() {
//            return list == null ? 0 : list.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return list.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder;
//            if (convertView == null) {
//                convertView = inflater.inflate(R.layout.item_gv_details, parent, false);
//                holder = new ViewHolder();
//                holder.key = (TextView) convertView.findViewById(R.id.key);
//                holder.value = (TextView) convertView.findViewById(R.id.value);
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//
//            holder.key.setText(list.get(position).key);
//            holder.value.setText(list.get(position).value);
//            return convertView;
//        }
//
//        class ViewHolder {
//            TextView key;
//            TextView value;
//        }
//    }

    private void showCallPhoneView(View view) {
        View otherView = LayoutInflater.from(this).inflate(R.layout.pw_call_tel_layout, null);
        CustomPopWindow customPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(otherView)
                .sizeByPercentage(this, 1f, 0)
                .setOutsideTouchable(true)
                .enableBackgroundDark(true)
                .setAnimationStyle(R.style.CustomPopWindowStyleFromBottom)
                .setBgDarkAlpha(0.5f)
                .create();
        customPopWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        handleCallPhoneView(otherView, customPopWindow);
    }

    private void handleCallPhoneView(View view, final CustomPopWindow customPopWindow) {
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customPopWindow != null) {
                    customPopWindow.dissmiss();
                }
            }
        });

        TextView callText = (TextView) view.findViewById(R.id.tel);
        callText.setText(mMeetingModel.getContactNumber());
        callText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customPopWindow != null) {
                    customPopWindow.dissmiss();
                }

                checkPower();

            }
        });

    }

    private void checkPower() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            callPhone();
        }
    }

    @SuppressLint("MissingPermission")
    public void callPhone() {

        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + mMeetingModel.getContactNumber());
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhone();
            } else {
                // Permission Denied
                Toast.makeText(this, "权限拒绝", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        if (requestCode == SDK_PERMISSION_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationService.start();
            } else {
                // Permission Denied
                Toast.makeText(this, "使用导航定位权限为必须权限", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void OnItemClick(int position) {
        MeetingBean roomDetail = mList.get(position);
        roomDetail.setName(mMeetingModel.getName());
        String isAdopted = roomDetail.getIsAdopted();
        getRoomTypeDetail(roomDetail.getId(), roomDetail);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        locationService.unregisterListener(myListener); //注销掉监听
        locationService.stop(); //停止定位服务
    }

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDAbstractLocationListener myListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            int LocType = location.getLocType();    //返回码


            lat = location.getLatitude();
            lon = location.getLongitude();

            Log.d("onReceiveLocation", "onReceiveLocation: " + lat);
            Log.d("onReceiveLocation", "onReceiveLocation: " + lon);

            saveLocation(city, addr);
            locationService.setLocationOption(locationService.getSingleLocationClientOption());
        }

    };

    private double lat;
    private double lon;

    public void saveLocation(String city, String addr) {
        LoginSharedUilt intance = LoginSharedUilt.getIntance(this);
        intance.saveLat(lat);
        intance.saveLon(lon);
        intance.saveCity(city);
        intance.saveAddr(addr);

        startNavigation();

    }

}

