package com.tec.travelagency.home.activity.meeting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.alipay.PayResult;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.home.activity.HotelDetailActivity;
import com.tec.travelagency.home.activity.ReserveHotelActivity;
import com.tec.travelagency.home.activity.hotel.MoreInfoCalendarActivity;
import com.tec.travelagency.home.activity.rent.CarOrderActivity;
import com.tec.travelagency.home.activity.route.PathOrderActivity;
import com.tec.travelagency.home.entity.IHotelNewBean;
import com.tec.travelagency.home.entity.MeetDelBean;
import com.tec.travelagency.home.entity.UpdateOrderFragment;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class TakeOrderActivity extends BaseActivity {
    @BindView(R.id.room_surplus)
    TextView room_surplus;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.image)
    ImageView head;
    @BindView(R.id.doorTicketCount)
    TextView count;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.number_add)
    ImageView number_add;
    @BindView(R.id.number_reduce)
    ImageView number_reduce;
    @BindView(R.id.plat_price)
    TextView platPrice;
    @BindView(R.id.finalPrice)
    TextView finalPrice;
    @BindView(R.id.price)
    TextView middlePrice;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.des)
    TextView des;
    @BindView(R.id.contact)
    EditText contact;
    @BindView(R.id.telephone)
    EditText tel;
    @BindView(R.id.bottom_column)
    RelativeLayout submit;
    @BindView(R.id.rl_wechat)
    RelativeLayout wechat;
    @BindView(R.id.rl_alipay)
    RelativeLayout alipay;
    @BindView(R.id.sel_we)
    ImageView selWe;
    @BindView(R.id.sel_al)
    ImageView selAl;
    @BindView(R.id.timeLayout)
    RelativeLayout timeLayout;
    @BindView(R.id.checkOutTime)
    TextView checkOutTime;
    @BindView(R.id.checkInTime)
    TextView checkInTime;
    @BindView(R.id.days_text)
    TextView days;
    @BindView(R.id.week)
    TextView week;
    @BindView(R.id.week1)
    TextView week1;
    MeetDelBean mMeetDelBean;
    String meetingName = "";
    int payType = 1;
    double totalprice = 0.0;
    int num;
    @Override
    protected int getContentId() {
        return R.layout.activity_take_order;
    }

    @Override
    protected void initView() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mMeetDelBean = (MeetDelBean) getIntent().getSerializableExtra("meet");
        meetingName = getIntent().getStringExtra("name");
        getDefaultData(mMeetDelBean.getConferenceRoomType().getId(),getDate(),getTomorrow(tomorrow()));
        initData();
    }
    public  String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    private String getTomorrow(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    public Date tomorrow() {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        return calendar.getTime();
    }

    private void getDefaultData(String id, String beginTime ,String endTime) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("roomtypeId", id);//人数
        parame.put("beginTime", beginTime);//人数
        parame.put("endTime", endTime);//人数
        Log.e("parame", parame.toString());
        HttpProxy.obtain().get(PlatformContans.Meeting.getDefaultDate, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                Log.e("add", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        num=data.getInt("emptyNum");
                        room_surplus.setText("剩余数量" + --num);
                    } else {
                        ToaskUtil.showToast(TakeOrderActivity.this, message);
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

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Log.e("code",resultStatus);
                        ToaskUtil.showToast(TakeOrderActivity.this,"下单成功");
                        finish();
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        //showAlert(PayDemoActivity.this, getString(R.string.pay_success) + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        //showAlert(PayDemoActivity.this, getString(R.string.pay_failed) + payResult);
                    }
                    break;
                }
            }
        };
    };

    private static final int SDK_PAY_FLAG = 1;
    private void alipay(final String orderInfo){
        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(TakeOrderActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    private void submitOrder(String phone, String name, final int payWay) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("travelAgencyConferenceRoomId", mMeetDelBean.getTravelAgencyConferenceRoom().getId());//房型id
        parame.put("spbillCreatIp", "127.0.0.1");//房间数量
        parame.put("orderRoomNum", Integer.parseInt(count.getText().toString()));//房间数量
        parame.put("contactName", name);//人数
        parame.put("contactNumber", phone);//号码
        parame.put("amount", totalprice);//逗号隔开  321，1231，12
        parame.put("modeOfPayment", payWay + "");//1微信,2现金
        parame.put("firstDate", mindate);//人数
        parame.put("lastDate", maxdate);//号码
        Log.e("id", parame.toString());
        HttpProxy.obtain().post(PlatformContans.Meeting.addOrder, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                Log.e("add", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        if (payWay == 1) {
                            PayReq payReq = new PayReq();
                            payReq.appId = data.getString("appid");
                            payReq.partnerId = data.getString("partnerid");
                            payReq.prepayId = data.getString("prepayid");
                            payReq.packageValue = data.getString("package");
                            payReq.nonceStr = data.getString("noncestr");
                            payReq.timeStamp = data.getString("timestamp");
                            payReq.sign = data.getString("sign");
                            Log.e("data", data.toString());
                            App.mWxApi.sendReq(payReq);
                        }else{
                            alipay(data.getString("orderString"));
                        }

                    } else {
                        ToaskUtil.showToast(TakeOrderActivity.this, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {
//                if (submit != null && mLoadView != null) {
//                    submit.setEnabled(true);
//                    mLoadView.dismiss();
//                }
            }
        });
    }


    private void initData() {
        Glide.with(this).load(mMeetDelBean.getConferenceRoomType().getImage1Url()).into(head);
        des.setText(mMeetDelBean.getConferenceRoomType().getTypeName());
        name.setText(meetingName);
        title.setText("支付订单");
        days.setText("共" + 1 + "晚");
        mindate=getDate();
        maxdate=getTomorrow(tomorrow());
        checkInTime.setText(getDate());
        checkOutTime.setText(getTomorrow(tomorrow()));
        week.setText(dateToWeek(checkOutTime.getText().toString()));
        platPrice.setText("￥" + mMeetDelBean.getTravelAgencyConferenceRoom().getPlatformPrice());
        totalprice = Integer.parseInt(count.getText().toString()) * mMeetDelBean.getTravelAgencyConferenceRoom().getPlatformPrice();
        middlePrice.setText("￥" + totalprice);
        finalPrice.setText("￥" + totalprice);
        number_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newCount = Integer.parseInt(count.getText().toString()) + 1;
                count.setText(newCount + "");
                room_surplus.setText("剩余数量"+--num);
                totalprice = Integer.parseInt(count.getText().toString()) * mMeetDelBean.getTravelAgencyConferenceRoom().getPlatformPrice();
                middlePrice.setText("￥" + totalprice);
                finalPrice.setText("￥" + totalprice);
            }
        });
        number_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(count.getText().toString()) == 1) {
                    return;
                }
                int newCount = Integer.parseInt(count.getText().toString()) - 1;
                count.setText(newCount + "");
                room_surplus.setText("剩余数量"+(++num));
                totalprice = Integer.parseInt(count.getText().toString()) * mMeetDelBean.getTravelAgencyConferenceRoom().getPlatformPrice();
                middlePrice.setText( "￥"+totalprice+"");
                finalPrice.setText("￥" + totalprice);
            }
        });
        timeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TakeOrderActivity.this, MoreInfoCalendarActivity.class);
                intent.putExtra("meeting", mMeetDelBean.getTravelAgencyConferenceRoom().getConferenceRoomTypeId());
                startActivityForResult(intent, 3);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contactName = contact.getEditableText().toString();
                String phone = tel.getEditableText().toString();
                if (TextUtils.isEmpty(contactName) || TextUtils.isEmpty(phone)) {
                    Toast.makeText(TakeOrderActivity.this, "输入不能为空！", Toast.LENGTH_LONG).show();
                    return;
                }
                //if(payType==1)
                  submitOrder(phone, contactName, payType);
//                else{
//                    Toast.makeText(TakeOrderActivity.this,"暂时不支持支付宝！",Toast.LENGTH_LONG).show();
//                }
            }
        });
        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payType=1;
                selAl.setVisibility(View.GONE);
                selWe.setVisibility(View.VISIBLE);

            }
        });
        alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payType=2;
                selAl.setVisibility(View.VISIBLE);
                selWe.setVisibility(View.GONE);
            }
        });
    }

    public static void startHotelDetail(Context context, MeetDelBean bean, String name) {
        Intent intent = new Intent(context, TakeOrderActivity.class);
        intent.putExtra("meet", bean);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3) {
            if (data != null) {
                String start = data.getStringExtra("date1");
                String end = data.getStringExtra("date2");
                if(TextUtils.isEmpty(start)){
                    return;
                }
                if(TextUtils.isEmpty(end)){
                    mindate=getDate();
                    maxdate=start;
                }else{
                    if (!compareTime(start, end)) {
                        mindate = start;
                        maxdate = end;
                    } else {
                        mindate = end;
                        maxdate = start;
                    }
                }
                checkOutTime.setText(maxdate.substring(5, maxdate.length()));
                checkInTime.setText(mindate.substring(5, maxdate.length()));
                days.setText("共" + getDays(mindate, maxdate) + "晚");
                week1.setText(dateToWeek(mindate));
                week.setText(dateToWeek(maxdate));
            }
        }
    }
    String mindate;
    String maxdate;
    private boolean compareTime(String curtime, String maxdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date bt = null;
        try {
            bt = sdf.parse(curtime);
            Date et = sdf.parse(maxdate);
            if (bt.before(et)) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return true;

    }
    private String getDate() {
        String time = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date();
        time = df.format(date1);
        return time;
    }

    // 计算两个日期相隔的天数
    public int getDays(String firstString, String secondString) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date firstDate = null;
        Date secondDate = null;
        try {
            firstDate = df.parse(firstString);
            secondDate = df.parse(secondString);
        } catch (Exception e) {
            // 日期型字符串格式错误
            System.out.println("日期型字符串格式错误");
        }
        int nDay = (int) ((secondDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000));
        return nDay;
    }
}
