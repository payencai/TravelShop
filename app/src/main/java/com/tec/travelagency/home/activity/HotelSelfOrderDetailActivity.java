package com.tec.travelagency.home.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.common.myviewpager.CommonViewPager;
import com.tec.travelagency.common.myviewpager.ViewPagerHolderCreator;
import com.tec.travelagency.home.adapter.ViewImageHolder;
import com.tec.travelagency.home.entity.DataEntry;
import com.tec.travelagency.home.entity.HotelSelfOrderBean;
import com.tec.travelagency.home.fragment.HotelSelfFragment;
import com.tec.travelagency.widget.CustomDatePicker;
import com.tec.travelagency.widget.CustomPopWindow;
import com.tec.travelagency.widget.StarLinearLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class HotelSelfOrderDetailActivity extends BaseActivity {


    @BindView(R.id.activity_common_view_pager)
    CommonViewPager mCommonViewPager;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.checkInTime)
    TextView checkInTime;
    @BindView(R.id.checkOutTime)
    TextView checkOutTime;
    @BindView(R.id.days_text)
    TextView diffDay;
    @BindView(R.id.Star)
    StarLinearLayout Star;
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.evaluate)
    TextView evaluate;
    @BindView(R.id.scale)
    TextView scale;

    private CustomDatePicker startTimePicker;
    private CustomDatePicker endTimePicker;

    /**
     * 住宿天数
     */
    public int mDifferenceTime;


    HotelSelfFragment mFragment;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private HotelSelfOrderBean mHotelSelfOrderBean;
    private List<DataEntry> mVPImagerUrlList;

    @Override
    protected int getContentId() {
        return R.layout.activity_hotel_self_order_detail;
    }

    @Override
    protected void initView() {

        Intent intent = getIntent();
        mHotelSelfOrderBean = ((HotelSelfOrderBean) intent.getSerializableExtra("HotelSelfOrderBean"));
        if (mHotelSelfOrderBean == null) {
            finish();
            return;
        }

        title.setText(mHotelSelfOrderBean.getName());
        initUI();

        initView2();
        initStartDatePicker();
        initFragment();


    }

    private void initUI() {
        Star.setScore((float) mHotelSelfOrderBean.getCommentData().getScore());
        score.setText(mHotelSelfOrderBean.getCommentData().getScore() + "分");
        evaluate.setText(mHotelSelfOrderBean.getCommentData().getNumber() + "条评论");
        double rate = mHotelSelfOrderBean.getCommentData().getRate();
        scale.setText("好评率: " + (rate * 100) + "%");
    }

    private void initFragment() {
        mFragment = new HotelSelfFragment(mHotelSelfOrderBean);
        getSupportFragmentManager().beginTransaction().add(R.id.oneself_order_container, mFragment).commit();
    }

    private void initView2() {
        // 设置数据
        mCommonViewPager.setPages(mockData(), new ViewPagerHolderCreator<ViewImageHolder>() {
            @Override
            public ViewImageHolder createViewHolder() {
                // 返回ViewPagerHolder
                return new ViewImageHolder();
            }
        });
        mCommonViewPager.setOpenCarousel(true);
    }


    private List mockData() {
        mVPImagerUrlList = new LinkedList<>();
        String image1 = mHotelSelfOrderBean.getImage1Url();
        String image2 = mHotelSelfOrderBean.getImage2Url();
        String image3 = mHotelSelfOrderBean.getImage3Url();
        String image4 = mHotelSelfOrderBean.getImage4Url();
        String image5 = mHotelSelfOrderBean.getImage5Url();
        String image6 = mHotelSelfOrderBean.getImage6Url();

        addToList(image1);
        addToList(image2);
        addToList(image3);
        addToList(image4);
        addToList(image5);
        addToList(image6);


        return mVPImagerUrlList;
    }


    private void addToList(String url) {
        if (!TextUtils.isEmpty(url)) {
            if (!url.equals("null")) {
                Log.d("addToList", "addToList: " + url);
                mVPImagerUrlList.add(new DataEntry(url));
            }
        }
    }


    //真实的时间格式
    public String startRealTime;
    public String endRealTime;

    //弹出选择时间
    private void initStartDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        String s = now.replace(" ", "_").split("_")[0];
        startRealTime = s;
        checkInTime.setText("入住 " + s);
        initEndDatePicker(now);
        startTimePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                initEndDatePicker(time);
                String s = time.replace(" ", "_").split("_")[0];
                startRealTime = s;
                checkInTime.setText("入住 " + s);
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
        checkOutTime.setText("离店 " + s);
        endTimePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {


            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                String s = time.replace(" ", "_").split("_")[0];
                endRealTime = s;
                checkOutTime.setText("离店 " + s);
                mDifferenceTime = getDifferenceTime();
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

    @OnClick({R.id.back, R.id.mine, R.id.checkInTime, R.id.checkOutTime,R.id.comment_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.mine:
                showCallPhoneView(view);
                break;
            case R.id.checkInTime:
                startTimePicker.show(startRealTime);
                break;
            case R.id.checkOutTime:
                endTimePicker.show(endRealTime);
                break;
            case R.id.comment_layout:
                CommentActivity.startCommentActivity(this, 1, mHotelSelfOrderBean.getId());
                break;
        }
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

//        TextView telView = (TextView) view.findViewById(R.id.tel);
//        telView.setText(mOrderBeanDetail.getHotel().getContactNumber());

        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customPopWindow != null) {
                    customPopWindow.dissmiss();
                }
            }
        });

        view.findViewById(R.id.tel).setOnClickListener(new View.OnClickListener() {
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
        Uri data = Uri.parse("tel:" + "10086");
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

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
