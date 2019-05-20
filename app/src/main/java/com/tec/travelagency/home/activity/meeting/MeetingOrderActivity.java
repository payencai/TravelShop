package com.tec.travelagency.home.activity.meeting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.home.adapter.RoomNumSelectAdapter;
import com.tec.travelagency.home.entity.HotelSelfBean;
import com.tec.travelagency.home.entity.InputRoomPeopleNameBean;
import com.tec.travelagency.home.entity.RoomNumBean;
import com.tec.travelagency.home.entity.RoomTypeDesBean;
import com.tec.travelagency.home.entity.UpdateOrderFragment;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.widget.CustomPopWindow;
import com.tec.travelagency.widget.FButton;
import com.tec.travelagency.widget.KyLoadingBuilder;
import com.tencent.mm.opensdk.modelpay.PayReq;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MeetingOrderActivity extends BaseActivity implements RoomNumSelectAdapter.ISelectRoomNum {

    @BindView(R.id.title)
    TextView title;


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
    @BindView(R.id.image)
    ImageView image;
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
        return R.layout.activity_meeting_order;
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
        Glide.with(this).load(mHotelSelfBean.getImage1Url()).into(image);
        Log.e("hotel", mHotelSelfBean.toString());
        mStartRealTime = intent.getStringExtra("startRealTime");
        mEndRealTime = intent.getStringExtra("endRealTime");
        mDifferenceTime = intent.getIntExtra("mDifferenceTime", 0);
        mHotelName = intent.getStringExtra("hotelName");
        platPrice.setText("￥"+mRoomTypeDesBean.getPlatformPrice());
        name.setText(mRoomTypeDesBean.getName());
        des.setText(getDesString());
        //7月25日--7月28日  共3晚
        diffTime.setText(mStartRealTime + "——" + mEndRealTime + "\t\t共" + mDifferenceTime + "晚");
        Glide.with(this).load(mHotelSelfBean.getImage1Url()).into(image);
        title.setText("支付订单");
        mRoomNumBeans = new LinkedList<>();
        initRoomNumBeans();

        mNumSelectAdapter = new RoomNumSelectAdapter(this, mRoomNumBeans, this);

        mList = new LinkedList<>();
        mList.add(new InputRoomPeopleNameBean());

        requestEmptyRoomNumber(mHotelSelfBean.getRoomtypeId(), mStartRealTime, mEndRealTime);

    }




    @OnClick({R.id.back, R.id.submit, R.id.number_reduce, R.id.number_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                String tel = telephone.getEditableText().toString();
                break;
            case R.id.number_reduce:

                if (mPeopleNumber <= 1) {
                    ToaskUtil.showToast(this, "数量不能小于1");
                    return;
                }
                mPeopleNumber--;
                doorTicketCount.setText(mPeopleNumber + "");
                roomSurplus.setText("房间剩余 " + (++mEmptyRoomNum));

                setPriceUI();
                break;
            case R.id.number_add:

                if (mPeopleNumber >= mMaxSelectNumber) {
                    ToaskUtil.showToast(this, "亲，房间数量不够哦");
                    return;
                }
                mPeopleNumber++;
                doorTicketCount.setText(mPeopleNumber + "");
                roomSurplus.setText("房间剩余 " + (--mEmptyRoomNum));
                setPriceUI();
                break;

        }
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

    private void submitOrder(String tel, String peopleNum, final int payWay) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("travelAgencyRoomtypeId", mRoomTypeDesBean.getId());//房型id
        parame.put("firstDate", mStartRealTime);//2018-05-20
        parame.put("lastDate", mEndRealTime);
        parame.put("spbillCreatIp", "127.0.0.1");//房间数量
        parame.put("orderRoomNum", mPeopleNumber);//房间数量
        parame.put("peopleNum", peopleNum);//人数
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
                LogUtil.Log("Hotel.add", result);
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
                            ToaskUtil.showToast(MeetingOrderActivity.this, "订单提交成功");
                            EventBus.getDefault().post(new UpdateOrderFragment());
                            finish();
                        }

                    } else {
                        ToaskUtil.showToast(MeetingOrderActivity.this, message);
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

    private void showSelectPayWay(Context context, View view, String price, String tel, String checkInNumberString) {
        View otherView = LayoutInflater.from(context).inflate(R.layout.pw_payway_select_layout, null);
        CustomPopWindow customPopWindow = new CustomPopWindow.PopupWindowBuilder(context)
                .setView(otherView)
                .sizeByPercentage(context, 1f, 0)
                .setOutsideTouchable(true)
                .enableBackgroundDark(true)
                .setAnimationStyle(R.style.CustomPopWindowStyleFromBottom)
                .setBgDarkAlpha(0.5f)
                .create();
        customPopWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        handlePaywaySelectView(context, otherView, customPopWindow, price, tel, checkInNumberString);
    }

    private int payWay = 1;//支付方式，1为微信，2为现金，默认微信

    private void handlePaywaySelectView(Context context, View otherView, CustomPopWindow customPopWindow, String price
            , final String tel, final String checkInNumberString) {
        final ImageView wxImg = (ImageView) otherView.findViewById(R.id.wx_pay_img);
        final ImageView moneyImg = (ImageView) otherView.findViewById(R.id.money_pay_img);
        payWay = 1;
        wxImg.setSelected(true);
        moneyImg.setSelected(false);

        wxImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payWay == 1) {
                    return;
                }
                wxImg.setSelected(true);
                moneyImg.setSelected(false);
                payWay = 1;
            }
        });
        moneyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payWay == 2) {
                    return;
                }
                wxImg.setSelected(false);
                moneyImg.setSelected(true);
                payWay = 2;
            }
        });
        TextView priceText = (TextView) otherView.findViewById(R.id.price);
        priceText.setText(price);
        FButton fButton = (FButton) otherView.findViewById(R.id.confirm);
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submit.setEnabled(false);
//                if (payWay == 1) {
//                    ToaskUtil.showToast(ReserveHotelActivity.this,"微信支付");
//
//                } else if (payWay == 2) {
//                    ToaskUtil.showToast(ReserveHotelActivity.this,"现金支付");
//
//                }
                submitOrder(tel, checkInNumberString, payWay);

            }
        });
    }

    @Override
    public void selectNum(int number) {
        mPeopleNumber = number;
        setPriceUI();

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

    private void setPriceUI() {
        BigDecimal countBig = new BigDecimal(mPeopleNumber);
        BigDecimal dayNumber = new BigDecimal(mDifferenceTime);
        double price = mRoomTypeDesBean.getPlatformPrice();
        BigDecimal priceBig = new BigDecimal(price + "");

        //  TODO
        BigDecimal multiply = countBig.multiply(priceBig);

        BigDecimal total = dayNumber.multiply(multiply);

        totalPrice.setText("¥" + total);
        finalPrice.setText("¥" + total);
    }

    private void requestEmptyRoomNumber(String roomtypeId, String beginTime, String endTime) {

        Map<String, Object> parames = new HashMap<>();
        parames.put("roomtypeId", roomtypeId);
        parames.put("beginTime", beginTime);
        parames.put("endTime", endTime);
        Log.e("pay", roomtypeId + "," + beginTime + "," + endTime);
        HttpProxy.obtain().get(PlatformContans.Roomnum.getEmptyNumofRoomtype, parames, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("getEmptyNum", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");

                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        if (data != null) {
                            mEmptyRoomNum = data.getInt("emptyNum");
                            mMaxSelectNumber = mEmptyRoomNum;
                            //这里的  --mEmptyRoomNum 是默认选择的数量为 1  ，所以减去1
                            roomSurplus.setText("房间剩余 " + (--mEmptyRoomNum));
                            setPriceUI();
                        }
                    } else {
                        ToaskUtil.showToast(MeetingOrderActivity.this, message);
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

}
