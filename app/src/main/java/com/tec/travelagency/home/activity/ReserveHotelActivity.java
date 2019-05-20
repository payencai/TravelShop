package com.tec.travelagency.home.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.home.activity.hotel.MoreInfoCalendarActivity;
import com.tec.travelagency.home.activity.meeting.TakeOrderActivity;
import com.tec.travelagency.home.adapter.RoomNumSelectAdapter;
import com.tec.travelagency.home.entity.HotelSelfBean;
import com.tec.travelagency.home.entity.InputRoomPeopleNameBean;
import com.tec.travelagency.home.entity.RoomNumBean;
import com.tec.travelagency.home.entity.RoomTypeDesBean;
import com.tec.travelagency.home.entity.UpdateOrderFragment;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.OrderInfoUtil2_0;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.widget.CustomPopWindow;
import com.tec.travelagency.widget.FButton;
import com.tec.travelagency.widget.KyLoadingBuilder;
import com.tencent.mm.opensdk.modelpay.PayReq;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

public class ReserveHotelActivity extends BaseActivity implements RoomNumSelectAdapter.ISelectRoomNum {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.prople_number_gv)
    GridView propleNumberBv;
    @BindView(R.id.sel_al)
    ImageView sel_alipay;
    @BindView(R.id.sel_we)
    ImageView sel_we;
    @BindView(R.id.rl_wechat)
    RelativeLayout rl_wechat;
    @BindView(R.id.bottom_column)
    RelativeLayout submit;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.des)
    TextView des;
    @BindView(R.id.diffTime)
    TextView diffTime;
    @BindView(R.id.telephone)
    TextView telephone;
    @BindView(R.id.checkInNumber)
    TextView checkInNumber;
    @BindView(R.id.price)
    TextView totalPrice;
    @BindView(R.id.contact)
    EditText contact;
    @BindView(R.id.doorTicketCount)
    TextView doorTicketCount;
    @BindView(R.id.room_surplus)
    TextView roomSurplus;
    @BindView(R.id.plat_price)
    TextView platPrice;
    @BindView(R.id.finalPrice)
    TextView finalPrice;
    @BindView(R.id.timeLayout)
    RelativeLayout timeLayout;
    @BindView(R.id.checkOutTime)
    TextView checkOutTime;
    @BindView(R.id.checkInTime)
    TextView checkInTime;
    @BindView(R.id.days_text)
    TextView days;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.week)
    TextView week;
    @BindView(R.id.week1)
    TextView week1;
    @BindView(R.id.rl_alipay)
    RelativeLayout rl_alipay;
    private List<RoomNumBean> mRoomNumBeans;
    private List<InputRoomPeopleNameBean> mList;
    private RoomNumSelectAdapter mNumSelectAdapter;
    //    private RVBaseAdapter<InputRoomPeopleNameBean> mRvBaseAdapter;
    private RoomTypeDesBean mRoomTypeDesBean;
    private HotelSelfBean mHotelSelfBean;
    private String mStartRealTime;
    private String mEndRealTime;
    private int mDifferenceTime;
    private KyLoadingBuilder mLoadView;
    String mindate;
    String maxdate;
    private String mHotelName;
    /**
     * 选择的数量 默认为 1
     */
    private int mPeopleNumber = 1;

    //空房数量，这是网络请求的，如果为-1 则网络未请求成功
    private int mEmptyRoomNum = -1;

    //最大选择数量
    private int mMaxSelectNumber = 0;

    @Override
    protected int getContentId() {
//        return R.layout.activity_reserve_hotel;
        return R.layout.activity_reserve_hotel2;
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
    /**
     * 日期转星期
     *
     * @param datetime
     * @return
     */
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

    private void getDefaultData(String id, String beginTime ,String endTime) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("roomtypeId", id);//人数
        parame.put("beginTime", beginTime);//人数
        parame.put("endTime", endTime);//人数
        Log.e("parame", parame.toString());
        HttpProxy.obtain().get(PlatformContans.Hotel.getDefaultDate, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                Log.e("add", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        int num=data.getInt("emptyNum");
                        mMaxSelectNumber=--num;
                        mEmptyRoomNum=num;
                        roomSurplus.setText("剩余数量" + num);
                    } else {
                        ToaskUtil.showToast(ReserveHotelActivity.this, message);
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

    @Override
    protected void initView() {
        Intent intent = getIntent();
        mRoomTypeDesBean = ((RoomTypeDesBean) intent.getSerializableExtra("RoomTypeDesBean"));
        mHotelSelfBean = ((HotelSelfBean) intent.getSerializableExtra("HotelSelfBean"));
        if (mRoomTypeDesBean == null || mHotelSelfBean == null) {
            finish();
            return;
        }
        mindate=getDate();
        maxdate=getTomorrow(tomorrow());
        getDefaultData(mRoomTypeDesBean.getTravelAgencyRoomtypeId(),getDate(),getTomorrow(tomorrow()));
        Log.e("hotel", mHotelSelfBean.toString());
        mStartRealTime = intent.getStringExtra("startRealTime");
        mEndRealTime = intent.getStringExtra("endRealTime");
        mDifferenceTime = intent.getIntExtra("mDifferenceTime", 0);
        mHotelName = intent.getStringExtra("hotelName");
        days.setText("共" + 1 + "晚");
        checkInTime.setText(getDate());
        checkOutTime.setText(getTomorrow(tomorrow()));
        week.setText(dateToWeek(checkOutTime.getText().toString()));
        platPrice.setText("￥" + mRoomTypeDesBean.getPlatformPrice());
        totalPrice.setText("￥" + mRoomTypeDesBean.getPlatformPrice());
        name.setText(mRoomTypeDesBean.getName());
        //des.setText(getDesString());
        Glide.with(this).load(mRoomTypeDesBean.getImage1Url()).into(image);
        //7月25日--7月28日  共3晚
        diffTime.setText(mStartRealTime + "——" + mEndRealTime + "\t\t共" + mDifferenceTime + "晚");

        //title.setText(mHotelName);
        title.setText("支付订单");
        mRoomNumBeans = new LinkedList<>();
        initRoomNumBeans();

        mNumSelectAdapter = new RoomNumSelectAdapter(this, mRoomNumBeans, this);
        propleNumberBv.setAdapter(mNumSelectAdapter);
        mList = new LinkedList<>();
        mList.add(new InputRoomPeopleNameBean());
        timeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReserveHotelActivity.this, MoreInfoCalendarActivity.class);
                intent.putExtra("id", mRoomTypeDesBean.getTravelAgencyRoomtypeId());
                startActivityForResult(intent, 3);
            }
        });
        initRecyeview();

       // requestEmptyRoomNumber(mHotelSelfBean.getRoomtypeId(), mStartRealTime, mEndRealTime);

    }

    private void initRecyeview() {



    }


    @OnClick({R.id.back, R.id.submit, R.id.number_reduce, R.id.number_add,R.id.rl_alipay,R.id.rl_wechat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                String tel = telephone.getEditableText().toString();
                String checkInNumberString = checkInNumber.getEditableText().toString();

                if (check(tel, checkInNumberString)) {
                    submitOrder(tel, checkInNumberString, payWay);
                    //showSelectPayWay(this, view, totalPrice.getText().toString(), tel, checkInNumberString);
                }
                break;
            case R.id.number_reduce:
                if (mPeopleNumber <= 1) {
                    ToaskUtil.showToast(this, "数量不能小于1");
                    return;
                }
                mPeopleNumber--;
                doorTicketCount.setText(mPeopleNumber + "");
                roomSurplus.setText("房间剩余 " + (++mEmptyRoomNum));
                totalPrice.setText("￥"+mRoomTypeDesBean.getPlatformPrice()*mPeopleNumber+"");
                finalPrice.setText("￥"+mRoomTypeDesBean.getPlatformPrice()*mPeopleNumber+"");
                //setPriceUI();
                break;
            case R.id.number_add:
                if (mPeopleNumber >= mMaxSelectNumber) {
                    ToaskUtil.showToast(this, "亲，房间数量不够哦");
                    return;
                }
                mPeopleNumber++;
                doorTicketCount.setText(mPeopleNumber + "");
                roomSurplus.setText("房间剩余 " + (--mEmptyRoomNum));
                totalPrice.setText("￥"+mRoomTypeDesBean.getPlatformPrice()*mPeopleNumber+"");
                finalPrice.setText("¥" + mRoomTypeDesBean.getPlatformPrice()*mPeopleNumber+"");
                //setPriceUI();
                break;
            case R.id.rl_alipay:
                sel_alipay.setVisibility(View.VISIBLE);
                sel_we.setVisibility(View.GONE);
                payWay=2;
                break;
            case R.id.rl_wechat:
                sel_alipay.setVisibility(View.GONE);
                sel_we.setVisibility(View.VISIBLE);
                payWay=1;
                break;

        }
    }

    private boolean check(String tel, String checkInNumberString) {
        if (TextUtils.isEmpty(contact.getEditableText().toString())) {
            ToaskUtil.showToast(this, "请输入联系人");
            return false;
        }
        if (TextUtils.isEmpty(tel) || tel.length() != 11) {
            ToaskUtil.showToast(this, "请输入正确的手机号");
            return false;
        }


        return true;
    }

    public void initRoomNumBeans() {
        for (int i = 1; i <= 10; i++) {
            if (i == 1) {
                mRoomNumBeans.add(new RoomNumBean(true, i));
            } else {
                mRoomNumBeans.add(new RoomNumBean(false, i));
            }

        }
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
                Log.e("date",dateToWeek(mindate)+","+dateToWeek(maxdate));
                checkOutTime.setText(maxdate.substring(5, maxdate.length()));
                checkInTime.setText(mindate.substring(5, maxdate.length()));
                week1.setText(dateToWeek(mindate));
                week.setText(dateToWeek(maxdate));
                days.setText("共" + getDays(mindate, maxdate) + "晚");
            }
        }
    }

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
                        ToaskUtil.showToast(ReserveHotelActivity.this,"下单成功");
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
                PayTask alipay = new PayTask(ReserveHotelActivity.this);
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

    private void submitOrder(String tel, String peopleNum, final int payWay) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("travelAgencyRoomtypeId", mRoomTypeDesBean.getId());//房型id
        parame.put("firstDate", mindate);//2018-05-20
        parame.put("lastDate", maxdate);
        parame.put("spbillCreatIp", "127.0.0.1");//房间数量
        parame.put("orderRoomNum", mPeopleNumber);//房间数量
        parame.put("telephoneNum", tel);//号码
        parame.put("checkInPerson", contact.getEditableText().toString());//逗号隔开  321，1231，12
        parame.put("modeOfPayment", payWay);//1微信,2现金
        Log.e("id", parame.toString());
        HttpProxy.obtain().post(PlatformContans.Hotel.add, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                submit.setEnabled(true);
                if (mLoadView != null) {
                    mLoadView.dismiss();
                }
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
                        } else {
                            alipay(data.getString("orderString"));
                            //ToaskUtil.showToast(ReserveHotelActivity.this, "订单提交成功");
                            //EventBus.getDefault().post(new UpdateOrderFragment());
                          //  finish();
                        }

                    } else {
                        ToaskUtil.showToast(ReserveHotelActivity.this, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {
                if (submit != null && mLoadView != null) {
                    submit.setEnabled(true);
                    mLoadView.dismiss();
                }
            }
        });
    }

    private int payWay = 1;//支付方式，1为微信，2为现金，默认微信


    @Override
    public void selectNum(int number) {
        mPeopleNumber = number;
       // setPriceUI();

    }
    public String getDesString() {
        String bedType = mRoomTypeDesBean.getBedType();
        String liveNum = mRoomTypeDesBean.getLiveNum();
        String intnet = mRoomTypeDesBean.getIntnet();
        String breakfast = mRoomTypeDesBean.getBreakfast();

        StringBuffer result = new StringBuffer();
        result.append(" " + bedType + "\t\t|");
        result.append("\t\t可住" + liveNum + "人\t\t|");
        result.append("\t\t" + intnet + "\t\t|");
        result.append("\t\t" + breakfast + "");

        return result.toString();
    }

}
