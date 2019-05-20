package com.tec.travelagency.home.activity.route;

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
import com.tec.travelagency.home.activity.hotel.MoreInfoCalendarActivity;
import com.tec.travelagency.home.activity.hotel.OneCalenderActivity;
import com.tec.travelagency.home.activity.meeting.TakeOrderActivity;
import com.tec.travelagency.home.activity.rent.CarOrderActivity;
import com.tec.travelagency.home.activity.rent.CarRental;
import com.tec.travelagency.home.activity.rent.TravelAgencyCarRental;
import com.tec.travelagency.home.activity.ticket.TicketOrderActivity;
import com.tec.travelagency.home.entity.MeetDelBean;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.ToaskUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class PathOrderActivity extends BaseActivity {
    @BindView(R.id.room_surplus)
    TextView room_surplus;
    @BindView(R.id.image)
    ImageView head;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.doorTicketCount)
    TextView count;
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
    @BindView(R.id.getime)
    RelativeLayout gettime;
    @BindView(R.id.today)
    TextView time;
    @BindView(R.id.back)
    ImageView back;
    String meetingName = "";
    int payType = 1;
    double totalprice = 0.0;
    Route mCarRental;
    TravelAgencyRoute mTravelAgencyCarRental;

    @Override
    protected int getContentId() {
        return R.layout.activity_path_order;
    }

    @Override
    protected void initView() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mCarRental = (Route) getIntent().getSerializableExtra("mCarRental");
        mTravelAgencyCarRental = (TravelAgencyRoute) getIntent().getSerializableExtra("mTravelAgencyCarRental");
        time.setText(getDate());
        title.setText("支付订单");
        initData();
        getDefaultData(mTravelAgencyCarRental.getRouteId(), getDate());
    }

    private void getDefaultData(String id, String date) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("routeId", id);//人数
        parame.put("date", date);//人数
        Log.e("parame", parame.toString());
        HttpProxy.obtain().get(PlatformContans.Path.getDefaultDate, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                Log.e("add", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        int data = object.getInt("data");
                        room_surplus.setText("剩余数量" + data);
                    } else {
                        ToaskUtil.showToast(PathOrderActivity.this, message);
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
                        ToaskUtil.showToast(PathOrderActivity.this,"下单成功");
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
                PayTask alipay = new PayTask(PathOrderActivity.this);
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
        parame.put("travelAgencyRouteId", mTravelAgencyCarRental.getId());//人数
        parame.put("useTime", mindate);//人数
        parame.put("spbillCreatIp", "127.0.0.1");//房间数量
        parame.put("contactName", name);//人数
        parame.put("contactNumber", phone);//号码
        parame.put("number", Integer.parseInt(count.getText().toString()));//逗号隔开  321，1231，12
        parame.put("modeOfPayment", payWay + "");//1微信,2现金
        Log.e("parame", parame.toString());
        HttpProxy.obtain().post(PlatformContans.Path.addPathOrder, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
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
                        ToaskUtil.showToast(PathOrderActivity.this, message);
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
        Glide.with(this).load(mCarRental.getImage1Url()).into(head);
        name.setText(mCarRental.getName());
        platPrice.setText("￥" + mTravelAgencyCarRental.getPlatformPrice());
        totalprice = Integer.parseInt(count.getText().toString()) * mTravelAgencyCarRental.getPlatformPrice();
        middlePrice.setText("￥" + totalprice);
        finalPrice.setText("￥" + totalprice);
        number_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newCount = Integer.parseInt(count.getText().toString()) + 1;
                count.setText(newCount + "");
                totalprice = Integer.parseInt(count.getText().toString()) * mTravelAgencyCarRental.getPlatformPrice();
                middlePrice.setText("￥" + totalprice);
                finalPrice.setText("￥" + totalprice);
            }
        });
        gettime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PathOrderActivity.this, OneCalenderActivity.class);
                intent.putExtra("route", mTravelAgencyCarRental.getRouteId());
                startActivityForResult(intent, 3);
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
                totalprice = Integer.parseInt(count.getText().toString()) * mTravelAgencyCarRental.getPlatformPrice();
                middlePrice.setText(totalprice + "");
                finalPrice.setText("￥" + totalprice);
            }
        });
//        timeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(PathOrderActivity.this, PathOneMonthActivity.class);
//                intent.putExtra("route", mTravelAgencyCarRental.getRouteId());
//                startActivityForResult(intent, 3);
//            }
//        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contactName = contact.getEditableText().toString();
                String phone = tel.getEditableText().toString();
                if (TextUtils.isEmpty(contactName) || TextUtils.isEmpty(phone)) {
                    Toast.makeText(PathOrderActivity.this, "输入不能为空！", Toast.LENGTH_LONG).show();
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
                payType = 1;
                selAl.setVisibility(View.GONE);
                selWe.setVisibility(View.VISIBLE);

            }
        });
        alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payType = 2;
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
                String start = data.getStringExtra("date");
                if (!TextUtils.isEmpty(start)) {
                    mindate = start;
                    time.setText(start);
                } else {
                    time.setText(getDate());
                    mindate = getDate();
                }
            }
        }
    }

    String mindate = getDate();
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


