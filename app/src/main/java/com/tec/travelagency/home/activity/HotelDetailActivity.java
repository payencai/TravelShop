package com.tec.travelagency.home.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
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
import com.tec.travelagency.home.activity.hotel.MoreInfoCalendarActivity;
import com.tec.travelagency.home.adapter.DetailsGvAdapter;
import com.tec.travelagency.home.adapter.HotelHeadImgAdapter;
import com.tec.travelagency.home.adapter.HotelRoomDetailAdapter;
import com.tec.travelagency.home.adapter.OnItemActionListener;
import com.tec.travelagency.home.adapter.ViewImageHolder;
import com.tec.travelagency.home.entity.DataEntry;
import com.tec.travelagency.home.entity.DetailsIntroduceBean;
import com.tec.travelagency.home.entity.HotelNew2Bean;
import com.tec.travelagency.home.entity.HotelNew3Bean;
import com.tec.travelagency.home.entity.HotelRoom;
import com.tec.travelagency.home.entity.HotelRoomDetail;
import com.tec.travelagency.home.entity.HotelRoomTypeDetail;
import com.tec.travelagency.home.entity.HotelSelfBean;
import com.tec.travelagency.home.entity.IHotelNewBean;
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
import com.tec.travelagency.widget.LeftSwipeMenuRecyclerView;
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

public class HotelDetailActivity extends BaseActivity implements RvItemActionListener {

    @BindView(R.id.hotel_viewpager)
    ViewPager hotelViewpager;
    @BindView(R.id.hotel_room_rv)
    RecyclerView hotelRoomRv;
    @BindView(R.id.timeLayout)
    RelativeLayout timeLayout;
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

    private CustomDatePicker startTimePicker;
    private CustomDatePicker endTimePicker;

    /**
     * 住宿天数
     */
    public int mDifferenceTime;


    private List<HotelRoomDetail> mList = new ArrayList<>();
    private KyLoadingBuilder mOpenUpdatePriceView;

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    //    private List<DetailsIntroduceBean> mDetailsIntroduceBeanlist;
    private IHotelNewBean mHotelNew2Or3Bean;
    private List<String> mVPImagerUrlList;
    private List<HotelRoom> mHotelRoomList;
    private HotelRoomDetailAdapter mRoomDetailAdapter;
    private KyLoadingBuilder mOpenAdoptView;
    private KyLoadingBuilder mOpenCancelView;

    private final int SDK_PERMISSION_REQUEST = 127;
    private String permissionInfo;

    private LocationService locationService;


    public static void startHotelDetail(Context context, IHotelNewBean bean) {
        Intent intent = new Intent(context, HotelDetailActivity.class);
        intent.putExtra("hotelNew2Bean", bean);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        locationService = App.getInstance().locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，
        // 然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(myListener);
        return R.layout.activity_hotel_detail;
    }

    @Override
    protected void initView() {
        final Intent intent = getIntent();
        mHotelNew2Or3Bean = (IHotelNewBean) intent.getSerializableExtra("hotelNew2Bean");
        if (mHotelNew2Or3Bean == null) {
            finish();
            return;
        }
        mVPImagerUrlList = new ArrayList<>();
        if (mHotelNew2Or3Bean instanceof HotelNew2Bean) {
            HotelNew2Bean hotelNew2Or3Bean = (HotelNew2Bean) mHotelNew2Or3Bean;
            initUI(hotelNew2Or3Bean);
        }

        if (mHotelNew2Or3Bean instanceof HotelNew3Bean) {
            HotelNew3Bean hotelNew2Or3Bean = (HotelNew3Bean) mHotelNew2Or3Bean;
            initUI(hotelNew2Or3Bean);
        }

        abstractDes.setText( mHotelNew2Or3Bean.getOneWord());

        initStartDatePicker();

        initRv();
        requestHotelRoomData();
        getVPImagerUrlList();
        hotelViewpager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HotelDetailActivity.this,HotelImageActivity.class);
                intent.putExtra("id",mHotelNew2Or3Bean.getId());
                startActivity(intent);
            }
        });
        HotelHeadImgAdapter hotelHeadImgAdapter = new HotelHeadImgAdapter(this, mVPImagerUrlList);
        hotelHeadImgAdapter.setOnClickImageListener(new HotelHeadImgAdapter.OnClickImageListener() {
            @Override
            public void onClick(Context context) {
                Intent intent=new Intent(context,HotelImageActivity.class);
                intent.putExtra("id",mHotelNew2Or3Bean.getId());
                startActivity(intent);
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

        hotelName.setText(mHotelNew2Or3Bean.getName());
        hotelTel.setText("电话:" + mHotelNew2Or3Bean.getContactNumber());
        HotelInfo.setText("酒店信息");
//        instr.setText(mHotelNew2Or3Bean.getInstr());
//        addressDetail.setText(mHotelNew2Or3Bean.getAddressDetail());
        addressDetail.setText(mHotelNew2Or3Bean.getProvince() + mHotelNew2Or3Bean.getCity() + mHotelNew2Or3Bean.getDistrict() + mHotelNew2Or3Bean.getAddressDetail());
        timeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
        checkInTime.setText(s.substring(5,s.length()));
        initEndDatePicker(now);

        startTimePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                String s = time.replace(" ", "_").split("_")[0];
                startRealTime = s;
                checkInTime.setText(s.substring(5,s.length()));
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
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
//        String now = sdf.format(new Date());

        String s = startTime.replace(" ", "_").split("_")[0];
        endRealTime = s;
        checkOutTime.setText(s.substring(5,s.length()));
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
                checkOutTime.setText( s.substring(5,s.length()));
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

    public static String getWeekday(String date) {// 必须yyyy-MM-dd
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdw = new SimpleDateFormat("E");
        Date d = null;
        try {
            d = sd.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdw.format(d);
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

    private void initUI(HotelNew2Bean hotelNew2Or3Bean) {
        Star.setScore((float) hotelNew2Or3Bean.getCommentData().getScore());
        score.setText(hotelNew2Or3Bean.getCommentData().getScore() + "分");
        evaluate.setText(hotelNew2Or3Bean.getCommentData().getNumber() + "条评论");
        double rate = hotelNew2Or3Bean.getCommentData().getRate();
        String ra=rate*100+"";
        scale.setText("好评率: " + ra.substring(0,4)+ "%");
    }

    private void initUI(HotelNew3Bean hotelNew2Or3Bean) {
        Star.setScore((float) hotelNew2Or3Bean.getCommentData().getScore());
        score.setText(hotelNew2Or3Bean.getCommentData().getScore() + "分");
        evaluate.setText(hotelNew2Or3Bean.getCommentData().getNumber() + "条评论");
        double rate = hotelNew2Or3Bean.getCommentData().getRate();
        scale.setText("好评率: " + (rate * 100) + "%");
    }

    private void requestHotelRoomData() {
        Map<String, Object> parame = new HashMap<>();
        parame.put("hotelId", mHotelNew2Or3Bean.getId());
        HttpProxy.obtain().get(PlatformContans.TravelAgency.getRoomtype, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {


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
                            HotelRoomDetail bean = gson.fromJson(item.toString(), HotelRoomDetail.class);
                            mList.add(bean);
                        }
                        mRoomDetailAdapter.notifyDataSetChanged();

                    } else {
                        ToaskUtil.showToast(HotelDetailActivity.this, message);
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

    }

    private void initRv() {
        hotelRoomRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        hotelRoomRv.setLayoutManager(new LinearLayoutManager(this));
        mRoomDetailAdapter = new HotelRoomDetailAdapter(this, mList);
        mRoomDetailAdapter.setOnItemActionListener(this);
        hotelRoomRv.setAdapter(mRoomDetailAdapter);
    }


    /**
     * 请求房间类型详情接口
     *
     * @param id
     * @param roomDetail
     */
    private void getRoomTypeDetail(final View view, final Dialog dialog, String id, final HotelRoomDetail roomDetail) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        HttpProxy.obtain().get(PlatformContans.TravelAgency.getRoomtypeDetail, App.getInstance().getUserEntity().getToken(), map, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("getRoomtypeDetail", result);
                Log.e("detail",result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        JSONObject roomtype = data.getJSONObject("roomtype");
//                        HotelRoomTypeDetail hotelRoomTypeDetail = new Gson().fromJson(roomtype.toString(), HotelRoomTypeDetail.class);
                        RoomTypeDesBean bean = new Gson().fromJson(roomtype.toString(), RoomTypeDesBean.class);
                        JSONObject item0 = data.getJSONObject("travelAgencyRoomtype");
                       // double travelAgencyPrice = item0.getDouble("travelAgencyPrice");
                        String roomtypeId = item0.getString("roomtypeId");
                        String id=item0.getString("id");
                        double price=item0.getDouble("platformPrice");
                        bean.setTravelAgencyRoomtypeId(roomtypeId);
                        bean.setId(id);
                        bean.setPlatformPrice(price);
                        bean.setTravelAgencyPrice(0.01);
                        showRoomDetailDialog(view,dialog,roomDetail,bean);
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
    private void showRoomDetailDialog(View view,Dialog dialogView, HotelRoomDetail roomDetail, RoomTypeDesBean hotelRoomTypeDetail){

        handleRoomDetailView(view, dialogView, roomDetail, hotelRoomTypeDetail);

    }

    private void showDialog(final HotelRoomDetail roomDetail, final  RoomTypeDesBean hotelRoomTypeDetail){
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_updateprice, null);
        final ButtomDialogView dialogView=new ButtomDialogView(this,view,Gravity.CENTER);
        dialogView.show();
        TextView cancel=view.findViewById(R.id.cancel);
        TextView confrim=view.findViewById(R.id.confirm);
        final EditText et_price=view.findViewById(R.id.et_price);
        TextView helpprice=view.findViewById(R.id.helpprice);

        helpprice.setText("￥"+hotelRoomTypeDetail.getManagePrice()+"-"+"￥"+hotelRoomTypeDetail.getManagePriceCeiling());
        TextView platprice=view.findViewById(R.id.platprice);
        platprice.setText("￥"+hotelRoomTypeDetail.getPlatformPrice());
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
                price= Double.parseDouble(et_price.getEditableText().toString());
                mOpenAdoptView = openLoadView("");
                adopt(roomDetail);
            }
        });
        Window window=dialogView.getWindow();
        WindowManager windowManager=getWindowManager();
        Display display=windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp=window.getAttributes();
        lp.width= (int) (display.getWidth()*0.8);
        window.setAttributes(lp);


    }
    private void showRoomDetailPw(View view, HotelRoomDetail roomDetail, RoomTypeDesBean hotelRoomTypeDetail) {
//        View otherView = LayoutInflater.from(this).inflate(R.layout.pw_hotel_room_detail_layout, null);
//        CustomPopWindow customPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
//                .setView(otherView)
//                .sizeByPercentage(this, 1f, 0)
//                .setOutsideTouchable(true)
//                .enableBackgroundDark(true)
//                .setAnimationStyle(R.style.CustomPopWindowStyleFromBottom)
//                .setBgDarkAlpha(0.5f)
//                .create();
//        customPopWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
//        handleRoomDetailView(otherView, customPopWindow, roomDetail, hotelRoomTypeDetail);
    }

    private void handleRoomDetailView(View view, final Dialog customPopWindow,
                                      final HotelRoomDetail roomDetail, final RoomTypeDesBean hotelRoomTypeDetail) {
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customPopWindow != null) {
                    customPopWindow.dismiss();
                }
            }
        });

        view.findViewById(R.id.addRoomType).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        CommonViewPager pager = (CommonViewPager) view.findViewById(R.id.room_type_banner);
        List<DataEntry> bannerListUrls = getBannerListUrl(hotelRoomTypeDetail);
        pager.setPages(bannerListUrls, new ViewPagerHolderCreator<ViewImageHolder>() {
            @Override
            public ViewImageHolder createViewHolder() {
                // 返回ViewPagerHolder
                return new ViewImageHolder();
            }
        });
        //开启轮播
        pager.setOpenCarousel(true);

        TextView oneselfBuyTV = ((TextView) view.findViewById(R.id.oneselfBuy));
        TextView updatePriceTV = ((TextView) view.findViewById(R.id.updatePrice));
        TextView soldOutTV = ((TextView) view.findViewById(R.id.sold_out));

        TextView platform_price = ((TextView) view.findViewById(R.id.platform_price));
        TextView selling_price = ((TextView) view.findViewById(R.id.selling_price));
        TextView describeText = ((TextView) view.findViewById(R.id.describe));

        describeText.setText(hotelRoomTypeDetail.getDescribe());

        SpannableString platformPrice = new SpannableString("平台价: ￥" + roomDetail.getManagePrice());
        int color = getResources().getColor(R.color.price_text_color);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        platformPrice.setSpan(span, 4, platformPrice.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        platform_price.setText(platformPrice);

        SpannableString sellPriceStr = new SpannableString("售价: ￥" + roomDetail.getTravelAgencyPrice());
        sellPriceStr.setSpan(span, 4, sellPriceStr.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        selling_price.setText(sellPriceStr);


        final String isAdopted = roomDetail.getIsAdopted();
        if (isAdopted.equals("1")) {
            //显示房间详情  修改价格显示
            updatePriceTV.setVisibility(View.VISIBLE);
            selling_price.setVisibility(View.VISIBLE);
            //此时已经上架了
            soldOutTV.setText("下架");
        } else {
            //显示房间详情  修改价格隐藏
            updatePriceTV.setVisibility(View.GONE);
            //此时已经下架了
            selling_price.setVisibility(View.GONE);
            soldOutTV.setText("上架");
        }

        oneselfBuyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HotelDetailActivity.this, ReserveHotelActivity.class);
                int differenceTime = mDifferenceTime;
                intent.putExtra("hotelName", mHotelNew2Or3Bean.getName());
                intent.putExtra("RoomTypeDesBean", hotelRoomTypeDetail);

                HotelSelfBean hotelSelfBean = new HotelSelfBean();
                hotelSelfBean.setTravelAgencyPrice(hotelRoomTypeDetail.getTravelAgencyPrice());
                hotelSelfBean.setRoomtypeId(hotelRoomTypeDetail.getTravelAgencyRoomtypeId());
                intent.putExtra("HotelSelfBean", hotelSelfBean);
                intent.putExtra("startRealTime", startRealTime);
                intent.putExtra("endRealTime", endRealTime);
                intent.putExtra("mDifferenceTime", differenceTime);
                startActivity(intent);
                if (customPopWindow != null) {
                    customPopWindow.dismiss();
                }

            }
        });
        updatePriceTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改价格界面，能修改价格表示已上架
                if (customPopWindow != null) {
                    customPopWindow.dismiss();
                }
                showSellingEditWindow(hotelRoomRv, roomDetail);
            }
        });
        soldOutTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (customPopWindow != null) {
                    customPopWindow.dismiss();
                }

                if (isAdopted.equals("1")) {//此时已经上架状态，点击下架，则删除
                    mOpenCancelView = openLoadView("");
                    cancel(roomDetail);

                } else {//此时已经下架状态，点击上架，则添加


                    showDialog(roomDetail,hotelRoomTypeDetail);
                    //adopt(roomDetail);
                }
            }
        });

        GridView detailsGv = view.findViewById(R.id.detailsGv);
        List<DetailsIntroduceBean> list = getGvList(hotelRoomTypeDetail);
        detailsGv.setAdapter(new DetailsGvAdapter(this, list));

    }

    private List<DataEntry> getBannerListUrl(RoomTypeDesBean hotelRoomTypeDetail) {
        String image1Url = hotelRoomTypeDetail.getImage1Url();
        String image2Url = hotelRoomTypeDetail.getImage2Url();
        String image3Url = hotelRoomTypeDetail.getImage3Url();
        String image4Url = hotelRoomTypeDetail.getImage4Url();
        String image5Url = hotelRoomTypeDetail.getImage5Url();


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

    private void showSellingEditWindow(View view, HotelRoomDetail roomDetail) {
        View brandView = LayoutInflater.from(this).inflate(R.layout.pw_updata_room_price_edit, null);
        CustomPopWindow customPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(brandView)
                .sizeByPercentage(this, 0.8f, 0f)
                .setOutsideTouchable(true)
                .enableBackgroundDark(true)
                .setAnimationStyle(R.style.CustomPopWindowStyle)
                .setBgDarkAlpha(0.5f)
                .create();
        handlerSynopsisEditWindow(brandView, customPopWindow, roomDetail);
        customPopWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    private void handlerSynopsisEditWindow(View view, final CustomPopWindow customPopWindow, final HotelRoomDetail roomDetail) {
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customPopWindow != null) {
                    customPopWindow.dissmiss();
                }
            }
        });
        final EditText sellEditText = (EditText) view.findViewById(R.id.synopsis_edit);
        sellEditText.setText(roomDetail.getTravelAgencyPrice() == null ? "0" : roomDetail.getTravelAgencyPrice());
        view.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (customPopWindow != null) {
                    customPopWindow.dissmiss();
                }
                String s = sellEditText.getEditableText().toString();
                mOpenUpdatePriceView = openLoadView("");
                updateRoomPrice(roomDetail, s);
            }


        });


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
                CommentActivity.startCommentActivity(this, 1, mHotelNew2Or3Bean.getId());
                break;
            case R.id.callImg:
                String contactNumber = mHotelNew2Or3Bean.getContactNumber();
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

        double latValue = Double.parseDouble(mHotelNew2Or3Bean.getLatitude());
        double lonValue = Double.parseDouble(mHotelNew2Or3Bean.getLongitude());
        Log.d("onViewClicked", "目标维度: " + latValue);
        Log.d("onViewClicked", "目标经度: " + lonValue);

        Log.d("onViewClicked", "开始维度: " + lat);
        Log.d("onViewClicked", "开始经度: " + lon);

        bundle.putDouble("latNumber", latValue);//目标维度
        bundle.putDouble("lonNumber", lonValue);//目标经度
        bundle.putString("currentCity", mHotelNew2Or3Bean.getCity());
        bundle.putString("startNodeStr", "");
        bundle.putDouble("startLat", lat);//开始维度
        bundle.putDouble("startLon", lon);//开始经度
        intent.setClass(this, RoutePlanDemo.class);
        startActivity(intent);
    }

    public void getVPImagerUrlList() {

        String image1 = mHotelNew2Or3Bean.getImage1Url();
        String image2 = mHotelNew2Or3Bean.getImage2Url();
        String image3 = mHotelNew2Or3Bean.getImage3Url();
        String image4 = mHotelNew2Or3Bean.getImage4Url();
        String image5 = mHotelNew2Or3Bean.getImage5Url();
        String image6 = mHotelNew2Or3Bean.getImage6Url();

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
    private void updateRoomPrice(HotelRoomDetail roomDetail, String price) {

        Map<String, Object> parame = new HashMap<>();
        parame.put("id", roomDetail.getId());
        parame.put("travelAgencyPrice", price);
        HttpProxy.obtain().post(PlatformContans.TravelAgency.updatePrice, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                closeLoadView(mOpenUpdatePriceView);
                LogUtil.Log("updatePrice", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        ToaskUtil.showToast(HotelDetailActivity.this, "修改成功");
                        requestHotelRoomData();
                    } else {
                        ToaskUtil.showToast(HotelDetailActivity.this, message);
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
    private void adopt(HotelRoomDetail roomDetail) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", roomDetail.getId());
        parame.put("price",price);
        HttpProxy.obtain().post(PlatformContans.TravelAgency.adopt, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("adopt", result);
                closeLoadView(mOpenAdoptView);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        ToaskUtil.showToast(HotelDetailActivity.this, "上架成功");
                        requestHotelRoomData();
                        EventBus.getDefault().post(new UpdateHotelList());
                    } else {
                        ToaskUtil.showToast(HotelDetailActivity.this, message);
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
    private void cancel(HotelRoomDetail roomDetail) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", roomDetail.getId());
        HttpProxy.obtain().post(PlatformContans.TravelAgency.cancel, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("cancel", result);
                closeLoadView(mOpenCancelView);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        ToaskUtil.showToast(HotelDetailActivity.this, "下架成功");
                        requestHotelRoomData();
                        EventBus.getDefault().post(new UpdateHotelList());
                    } else {
                        ToaskUtil.showToast(HotelDetailActivity.this, message);
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
        callText.setText(mHotelNew2Or3Bean.getContactNumber());
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
        Uri data = Uri.parse("tel:" + mHotelNew2Or3Bean.getContactNumber());
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
        HotelRoomDetail roomDetail = mList.get(position);
        String isAdopted = roomDetail.getIsAdopted();
        View view = LayoutInflater.from(this).inflate(R.layout.pw_hotel_room_detail_layout, null);
        ButtomDialogView dialogView=new ButtomDialogView(this,view,Gravity.BOTTOM);
        dialogView.show();
       // showRoomDetailDialog();
        getRoomTypeDetail(view,dialogView,roomDetail.getId(), roomDetail);
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
