package com.tec.travelagency.orderManager.entity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tec.travelagency.R;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseCell;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.home.activity.LxsHotelOrderDetailActivity;
import com.tec.travelagency.home.activity.UserHotelOrderDetailActivity;
import com.tec.travelagency.orderManager.activity.DoorTicketOrderDetailActivity;
import com.tec.travelagency.orderManager.activity.HotelOrderDetailActivity;
import com.tec.travelagency.orderManager.activity.PathOrderDetailActivity;
import com.tec.travelagency.orderManager.activity.RentCarOrderDetailActivity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 作者：凌涛 on 2018/8/6 11:06
 * 邮箱：771548229@qq..com
 */
public class AccountPaidOrderInfo2 extends RVBaseCell implements Serializable {

    private String id;
    private String orderNum;
    private String paymentTime;
    private String productName;
    private String categoryId;
    private String categoryName;
    private String contactName;
    private String contactNumber;
    private String userId;
    private double amount;
    private String nickname;
    private String status;
    private String isInner;
    private String isDeleted;
    private String travelAgencyId;
    private String travelAgencyName;
    private String startDate;
    private String endDate ;
    private String hotelId;
    private String hotelName;
    private String breakfast;
    private String isCanCancel;
    private String bedType;
    private int days;
    private int orderType;//订单类型 1为用户订单，2为旅行社订单

    public AccountPaidOrderInfo2() {
        super(null);
    }

    @Override
    public int getItemType() {
        //1酒店，2门票3租车
//        if (categoryId.equals("1")) {
//            return 0;
//        } else if (categoryId.equals("2")) {
//            return 1;
//
//        } else if (categoryId.equals("3")) {
//            return 2;
//        } else {
//            return 3;
//        }
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
//        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_paid_order_rv_layout2, parent, false);
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_order_list_layout2, parent, false);
        return new RVBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position, final Context context, RVBaseAdapter adapter) {
//        ImageView imgTag = holder.getImageView(R.id.img_tag);
//
//        if (categoryId.equals("1")) {//酒店
//            Glide.with(context).load(R.mipmap.hotel_order_tag).into(imgTag);
//
//        } else if (categoryId.equals("2")) {
//            //门票
//            Glide.with(context).load(R.mipmap.ticketsforthe_order_tag).into(imgTag);
//
//        } else if (categoryId.equals("3")) {
//            //租车
//            Glide.with(context).load(R.mipmap.carrental_order_tag).into(imgTag);
//        } else {
//            //路线
//            Glide.with(context).load(R.mipmap.line_order_tag).into(imgTag);
//        }
//
//
//        holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LxsHotelOrderDetailActivity.startHotelOrderDetailActivity(context,AccountPaidOrderInfo2.this);
//            }
//        });
//
//        TextView orderIdText = holder.getTextView(R.id.orderId);
//        orderIdText.setText("订单编号:" + orderNum);
//        holder.getTextView(R.id.placeTime).setText("下单时间:" + paymentTime);
//        holder.getTextView(R.id.price).setText("￥ " + amount);
//
//        TextView stateTextView = holder.getTextView(R.id.state);
//        if (status.equals("1")) {
//            stateTextView.setText("待消费");
//        } else if (status.equals("2")) {
//            stateTextView.setText("申请中");
//
//        } else if (status.equals("3")) {
//            stateTextView.setText("已退款");
//
//        } else if (status.equals("4")) {
//            stateTextView.setText("已完成");
//
//        }


        holder.getView(R.id.bookingLayout).setVisibility(View.GONE);
        ImageView imgTag = holder.getImageView(R.id.img_tag);

        if (categoryId.equals("1")) {//酒店
            Glide.with(context).load(R.mipmap.hotel_order_tag).into(imgTag);

        } else if (categoryId.equals("2")) {
            //门票
            Glide.with(context).load(R.mipmap.ticketsforthe_order_tag).into(imgTag);

        } else if (categoryId.equals("3")) {
            //租车
            Glide.with(context).load(R.mipmap.carrental_order_tag).into(imgTag);
        } else {
            //路线
            Glide.with(context).load(R.mipmap.line_order_tag).into(imgTag);
        }

        holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LxsHotelOrderDetailActivity.startHotelOrderDetailActivity(context, AccountPaidOrderInfo2.this);
            }
        });

        TextView orderIdText = holder.getTextView(R.id.orderId);
        orderIdText.setText("订单编号:" + orderNum);
        holder.getTextView(R.id.placeTime).setText("下单时间:" + paymentTime);
        holder.getTextView(R.id.price).setText("￥ " + amount);

        TextView stateTextView = holder.getTextView(R.id.state);
        if (status.equals("1")) {
            stateTextView.setText("待消费");
        } else if (status.equals("2")) {
            stateTextView.setText("申请中");

        } else if (status.equals("3")) {
            stateTextView.setText("已退款");

        } else if (status.equals("4")) {
            stateTextView.setText("已完成");
        }

        TextView bookingTel = holder.getTextView(R.id.bookingTel);
        bookingTel.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        holder.getTextView(R.id.hotelName).setText(hotelName);
        holder.getTextView(R.id.roomName).setText(productName);

//        holder.getTextView(R.id.breakfast).setText(breakfast);
        holder.getTextView(R.id.breakfast).setText(breakfast+" | "+bedType);

        holder.getTextView(R.id.bookingName).setText(contactName);
        holder.getTextView(R.id.bookingTel).setText(contactNumber);

        String firstDate = startDate;
        String lastDate = endDate;

//        if (TextUtils.isEmpty(lastDate)) {
//            lastDate = "2018-09-21 00:00:00";
//            Log.d("endtimeData", "onBindViewHolder: " + lastDate);
//        }
            Log.d("endtimeData", "onBindViewHolder: " + lastDate);

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

    }

    private void setPathUI(RVBaseViewHolder holder, int position, final Context context, RVBaseAdapter adapter) {
        holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PathOrderDetailActivity.class);
                intent.putExtra("accountPaidOrderInfo", AccountPaidOrderInfo2.this);
                context.startActivity(intent);
            }
        });

        holder.setText(R.id.orderId, "订单编号:" + orderNum);
        holder.setText(R.id.pathContent, productName);
        holder.setText(R.id.price, "应付:" + amount + "元");
        holder.setText(R.id.order_item_state, "下单时间:" + paymentTime);


    }

    private void setDoorTicketUI(RVBaseViewHolder holder, int position, final Context context, RVBaseAdapter adapter) {

        holder.setText(R.id.orderId, "订单编号:" + orderNum);
        holder.setText(R.id.content, productName);
        holder.setText(R.id.playTime, "游玩时间:" + paymentTime);
        holder.setText(R.id.price, "应付:" + amount + "元");
        holder.setText(R.id.order_item_state, "下单时间:" + paymentTime);
        holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DoorTicketOrderDetailActivity.class);
                intent.putExtra("accountPaidOrderInfo", AccountPaidOrderInfo2.this);
                context.startActivity(intent);
            }
        });

    }

    private void setHotelUI(RVBaseViewHolder holder, int position, final Context context, RVBaseAdapter adapter) {
        holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HotelOrderDetailActivity.class);
                intent.putExtra("accountPaidOrderInfo", AccountPaidOrderInfo2.this);
                context.startActivity(intent);
            }
        });

        holder.setText(R.id.orderId, "订单编号:" + orderNum);
        holder.setText(R.id.content, hotelName);
        holder.setText(R.id.bedType, productName);


        try {
            int i = dayForWeek(startDate);
            int j = dayForWeek(endDate);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String curDayTime = sdf.format(new Date());
            int cur = dayForWeek(curDayTime);
            String showInString = "";
            String showOutString = "";

            showInString = "入住\n" + getWeekString(i);
            showOutString = "离店\n" + getWeekString(j);

            if (i == cur) {
                showInString = context.getString(R.string.checkin_show_text);
            }

            holder.setText(R.id.checkInShowTime, showInString);
            holder.setText(R.id.checkOutShowTime, showOutString);
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.setText(R.id.checkInTime, getDate2(startDate));
        holder.setText(R.id.checkOutTime, getDate2(endDate));

        long start = getDate(startDate).getTime();
        long end = getDate(endDate).getTime();
        int days = (int) ((end / 1000 - start / 1000) / (60 * 60 * 24)) + 1;
        holder.setText(R.id.days_text, "共" + days + "晚");


        holder.setText(R.id.breakfast, breakfast.equals("1") ? "有早餐" : "无早餐");
        holder.setText(R.id.isCanCancel, isCanCancel.equals("1") ? "可取消" : "不可取消");
        holder.setText(R.id.price, "应付:" + amount + "元");
        holder.setText(R.id.order_item_state, "下单时间:" + paymentTime);


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

    private void setRentCar(RVBaseViewHolder holder, int position, final Context context, RVBaseAdapter adapter) {
        holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RentCarOrderDetailActivity.class);
                intent.putExtra("accountPaidOrderInfo", AccountPaidOrderInfo2.this);
                context.startActivity(intent);
            }
        });

        holder.setText(R.id.orderId, "订单编号:" + orderNum);
        holder.setText(R.id.content, productName);
        String s1 = startDate.split(" ")[0];
        String s2 = endDate.split(" ")[0];
        holder.setText(R.id.rentCarTime, "租车时间:" + s1 + "——" + s2);
        holder.setText(R.id.amount, "应付: " + amount + "元");
        holder.setText(R.id.order_item_state, "下单时间:" + paymentTime);


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsInner() {
        return isInner;
    }

    public void setIsInner(String isInner) {
        this.isInner = isInner;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getTravelAgencyId() {
        return travelAgencyId;
    }

    public void setTravelAgencyId(String travelAgencyId) {
        this.travelAgencyId = travelAgencyId;
    }

    public String getTravelAgencyName() {
        return travelAgencyName;
    }

    public void setTravelAgencyName(String travelAgencyName) {
        this.travelAgencyName = travelAgencyName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
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

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }
}