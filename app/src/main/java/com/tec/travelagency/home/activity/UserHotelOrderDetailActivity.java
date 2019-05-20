package com.tec.travelagency.home.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.orderManager.entity.UserHotelOrderDetailBean;
import com.tec.travelagency.orderManager.entity.UserOrderListBean;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.widget.CustomPopWindow;
import com.tec.travelagency.widget.KyLoadingBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserHotelOrderDetailActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.hotel_name)
    TextView hotelName;
    @BindView(R.id.addressDetail)
    TextView addressDetail;
    @BindView(R.id.productNum)
    TextView productNum;
    @BindView(R.id.timeInfo)
    TextView timeInfo;

    @BindView(R.id.bedTypeAndOther)
    TextView bedTypeAndOther;

    //平台价
    @BindView(R.id.platformPrice)
    TextView platformPrice;
    //售价
    @BindView(R.id.sellingPrice)
    TextView sellingPrice;
    //利润
    @BindView(R.id.profitPrice)
    TextView profitPrice;


    @BindView(R.id.checkInPerson)
    TextView checkInPerson;
    @BindView(R.id.telephoneNum)
    TextView telephoneNum;
    @BindView(R.id.peopleNum)
    TextView peopleNum;
    @BindView(R.id.orderId)
    TextView orderId;
    @BindView(R.id.paymentTime)
    TextView paymentTime;
    @BindView(R.id.modeOfPayment)
    TextView modeOfPayment;
    @BindView(R.id.liveNum)
    TextView liveNum;

    //申请取消
    @BindView(R.id.applyCanceTime)
    TextView applyCanceTime;
    @BindView(R.id.applyCancelLayout)
    LinearLayout applyCancelLayout;

    //申请取消成功
    @BindView(R.id.applyCancelSucceedTime)
    TextView applyCancelSucceedTime;
    @BindView(R.id.applyCancelSucceedLayout)
    LinearLayout applyCancelSucceedLayout;

    //取消原因
    @BindView(R.id.cancelReasonText)
    TextView cancelReasonText;
    @BindView(R.id.cancelReasonLayout)
    LinearLayout cancelReasonLayout;


    @BindView(R.id.commentLayout)
    LinearLayout commentLayout;
    @BindView(R.id.commentTime)
    TextView commentTime;
    @BindView(R.id.commentScore)
    TextView commentScore;
    @BindView(R.id.commentContent)
    TextView commentContent;


    private UserOrderListBean mUserOrderListBean;
    private UserHotelOrderDetailBean mOrderBeanDetail;
    private KyLoadingBuilder mApplyOrderCancelView;

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;


    public static final void startHotelOrderDetailActivity(Context activity, UserOrderListBean userOrderListBean) {
        Intent intent = new Intent(activity, UserHotelOrderDetailActivity.class);
        intent.putExtra("userOrderListBean", userOrderListBean);
        activity.startActivity(intent);

    }

    @Override
    protected int getContentId() {
        return R.layout.activity_user_hotel_order_detail;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        mUserOrderListBean = ((UserOrderListBean) intent.getSerializableExtra("userOrderListBean"));
        if (mUserOrderListBean == null) {
            return;
        }

        String url = "";
        url = PlatformContans.Hotel.getForTravelAgency0;
        requestOrderDetail(url);
    }

    private void requestOrderDetail(String url) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", mUserOrderListBean.getId());
        HttpProxy.obtain().get(url, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("userOrderDetail", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        mOrderBeanDetail = new Gson().fromJson(object.getJSONObject("data")
                                .toString(), UserHotelOrderDetailBean.class);
                        setUpdateUI(mOrderBeanDetail);
                    } else {
                        ToaskUtil.showToast(UserHotelOrderDetailActivity.this, message);
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

    private void setUpdateUI(UserHotelOrderDetailBean bean) {

        UserHotelOrderDetailBean.HotelOrderBean hotelOrder = bean.getHotelOrder();
        UserHotelOrderDetailBean.HotelBean hotel = bean.getHotel();
        hotelName.setText(hotel.getName());

        addressDetail.setText(hotel.getAddressDetail());
        productNum.setText(hotelOrder.getRoomtypeName());

        String firstDate = hotelOrder.getFirstDate();
        String lastDate = hotelOrder.getLastDate();

        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date firstParse = null;
        Date endParse = null;
        try {
            firstParse = format0.parse(firstDate);
            endParse = format0.parse(lastDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        String start = format.format(firstParse);
        String end = format.format(endParse);


        timeInfo.setText(start + "--" + end + "\t\t" + hotelOrder.getDays() + "晚");
        bedTypeAndOther.setText(hotelOrder.getBedType() + " | " + hotelOrder.getBreakfast());

        platformPrice.setText(hotelOrder.getHotelAmount() + "");
        sellingPrice.setText(hotelOrder.getAmount() + "");
        profitPrice.setText((hotelOrder.getAmount() - hotelOrder.getHotelAmount()) + "");


        checkInPerson.setText(hotelOrder.getCheckInPerson());
        telephoneNum.setText(hotelOrder.getTelephoneNum());
        peopleNum.setText(hotelOrder.getPeopleNum() + "人");
        orderId.setText("订单编号:" + hotelOrder.getOrderNum());
        paymentTime.setText(hotelOrder.getPaymentTime());

        String modeOfPayment = hotelOrder.getModeOfPayment();
        if (modeOfPayment.equals("1")) {
            this.modeOfPayment.setText("线上支付");
        } else {
            this.modeOfPayment.setText("线下支付");
        }
        liveNum.setText(hotelOrder.getOrderRoomNum() + "间");

        setMainUI();


//        bedType.setText(bean.getProductNum());
//        name.setText(bean.getHotelName());
//
//        String startDate = bean.getFirstDate();
//        String endDate = bean.getLastDate();
//
//        try {
//            int i = dayForWeek(startDate);
//            int j = dayForWeek(endDate);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String curDayTime = sdf.format(new Date());
//            int cur = dayForWeek(curDayTime);
//            String showInString = "";
//            String showOutString = "";
//
//            showInString = "入住\n" + getWeekString(i);
//            showOutString = "离店\n" + getWeekString(j);
//
//            if (i == cur) {
//                showInString = getString(R.string.checkin_show_text);
//            }
//
//            checkInShowTime.setText(showInString);
//            checkOutShowTime.setText(showOutString);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        checkInTime.setText(getDate2(startDate));
//        checkOutTime.setText(getDate2(endDate));
//
//        long start = getDate(startDate).getTime();
//        long end = getDate(endDate).getTime();
//        int days = (int) ((end / 1000 - start / 1000) / (60 * 60 * 24)) + 1;
//        daysText.setText("共" + days + "晚");
//
//
//        breakfast.setText(breakfast.equals("1") ? "有早餐" : "无早餐");
//        isCanCancel.setText(isCanCancel.equals("1") ? "可取消" : "不可取消");
//
//        price.setText("¥ " + bean.getPrice() + "元");
//
//        String contactNumber = bean.getTelephoneNum();
//        String s11 = contactNumber.substring(0, 3);
//        String s22 = contactNumber.substring(contactNumber.length() - 5, contactNumber.length() - 1);
//        String showStr = s11 + "****" + s22;
//        tel.setText(bean.getNickname() + " " + showStr);
//
//        amount.setText("商品合计：¥" + bean.getAmount());
//        amount2.setText("实付：¥ " + bean.getAmount() + "元");

    }

    @SuppressLint("ResourceAsColor")
    private void setMainUI() {
        String status = mUserOrderListBean.getStatus();
        if (status.equals("1")) {
            title.setText("待消费");
        } else if (status.equals("2")) {
            title.setText("申请中");

            applyCancelLayout.setVisibility(View.VISIBLE);
            cancelReasonLayout.setVisibility(View.VISIBLE);

            List<UserHotelOrderDetailBean.RefundListBean> refundList = mOrderBeanDetail.getRefundList();
            UserHotelOrderDetailBean.RefundListBean bean = refundList.get(refundList.size() - 1);
            applyCanceTime.setText(bean.getCreateTime());
            cancelReasonText.setText(bean.getRefundReason());

        } else if (status.equals("3")) {
            title.setText("已退款");

            applyCancelLayout.setVisibility(View.VISIBLE);
            applyCancelSucceedLayout.setVisibility(View.VISIBLE);

            List<UserHotelOrderDetailBean.RefundListBean> refundList = mOrderBeanDetail.getRefundList();

            try {
                UserHotelOrderDetailBean.RefundListBean bean = refundList.get(refundList.size() - 1);
                applyCanceTime.setText(bean.getCreateTime());
                applyCancelSucceedTime.setText(bean.getDisposeTime() == null ? "" : bean.getDisposeTime());
            } catch (IndexOutOfBoundsException e) {

            }

        } else if (status.equals("4")) {
            title.setText("已完成");
            commentLayout.setVisibility(View.VISIBLE);
            commentTime.setText(mOrderBeanDetail.getHotelComment().getCreateTime());
            commentScore.setText(mOrderBeanDetail.getHotelComment().getScore() + "分");
            commentContent.setText(mOrderBeanDetail.getHotelComment().getContent());
        }

    }

    public static int dayForWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date parse = format.parse(pTime);
        c.setTime(parse);
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }

        return dayForWeek;
    }

    public static String getWeekString(int dayForWeek) {

        String weekString = "";
        if (dayForWeek == 1) {
            weekString = "周一";
        } else if (dayForWeek == 2) {
            weekString = "周二";

        } else if (dayForWeek == 3) {
            weekString = "周三";

        } else if (dayForWeek == 4) {
            weekString = "周四";

        } else if (dayForWeek == 5) {
            weekString = "周五";

        } else if (dayForWeek == 6) {
            weekString = "周六";

        } else if (dayForWeek == 7) {
            weekString = "周日";
        }
        return weekString;
    }


    private Date getDate(String times) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(times);
            return date;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private String getDate2(String times) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("MM月dd日");
            Date date = sdf.parse(times);
            String format = sdf2.format(date);
            return format;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @OnClick({R.id.telephone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.telephone:
                if (mOrderBeanDetail == null) {
                    ToaskUtil.showToast(this, "数据请求中...");
                    return;
                }
                showCallView(view);
                break;
        }
    }

    private void showCallView(View view) {
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
        handleCallView(otherView, customPopWindow);
    }

    private void handleCallView(View view, final CustomPopWindow customPopWindow) {
        TextView telView = (TextView) view.findViewById(R.id.tel);
        telView.setText(mOrderBeanDetail.getHotel().getContactNumber());
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
        Uri data = Uri.parse("tel:" + mOrderBeanDetail.getHotel().getContactNumber());
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
