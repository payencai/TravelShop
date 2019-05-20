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
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.home.adapter.DetailsGvAdapter;
import com.tec.travelagency.home.entity.AdultTickerBean;
import com.tec.travelagency.home.entity.DayInfo;
import com.tec.travelagency.home.entity.DetailsIntroduceBean;
import com.tec.travelagency.home.entity.HotelRoomDetail;
import com.tec.travelagency.home.entity.UpdateOrderFragment;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.widget.CustomPopWindow;
import com.tec.travelagency.widget.KyLoadingBuilder;
import com.tec.travelagency.widget.MonthMoneyDataFormView;
import com.tec.travelagency.widget.MonthMoneyDataFormView2;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WriteDoorTickerInfoActivity extends BaseActivity implements MonthMoneyDataFormView2.onIClickItem {


    @BindView(R.id.otherLayout)
    LinearLayout otherLayout;

    @BindView(R.id.todayLayout)
    LinearLayout todayLayout;

    @BindView(R.id.tomorrowLayout)
    LinearLayout tomorrowLayout;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.editName)
    EditText editName;
    @BindView(R.id.reserve_now)
    TextView reserveNow;
    @BindView(R.id.editTel)
    EditText editTel;
    @BindView(R.id.doorTicketCount)
    TextView doorTicketCount;
    @BindView(R.id.unitPrice)
    TextView unitPrice;
    @BindView(R.id.totalPrice)
    TextView totalPrice;
    @BindView(R.id.noteEdit)
    EditText noteEdit;


    @BindView(R.id.todayTime)
    TextView todayTime;
    @BindView(R.id.todayPrice)
    TextView todayPrice;

    @BindView(R.id.tomorrowTime)
    TextView tomorrowTime;
    @BindView(R.id.tomorrowPrice)
    TextView tomorrowPrice;
    @BindView(R.id.select_time_layout)
    LinearLayout selectTimeLayout;

    private KyLoadingBuilder mLoadView;

    /**
     * 门票数量 默认为 1
     */
    private int mDoorTicketNum = 1;
    private AdultTickerBean mAdultTickerBean;

    //当前选择的日期的信息 默认选择今天
    private DayInfo mSelectDay;

    private List<DayInfo> mDayInfos;
    private CustomPopWindow mCustomPopWindow;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;


    @Override
    protected int getContentId() {
        return R.layout.activity_write_door_ticker_info;
    }

    @Override
    protected void initView() {

        Intent intent = getIntent();
        mAdultTickerBean = ((AdultTickerBean) intent.getSerializableExtra("AdultTickerBean"));
        if (mAdultTickerBean == null) {
            finish();
            return;
        }
        requesDateData();
        title.setText(mAdultTickerBean.getName());
        name.setText(mAdultTickerBean.getName());
//        selectDateUI(0);
        doorTicketCount.setText(mDoorTicketNum + "");

    }


    @OnClick({R.id.back, R.id.otherLayout, R.id.todayLayout,
            R.id.tomorrowLayout, R.id.number_reduce, R.id.number_add,
            R.id.reserve_now, R.id.mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.todayLayout:
                selectDateUI(0);
                break;
            case R.id.mine:
                showCallPhoneView(view);
                break;
            case R.id.tomorrowLayout:
                selectDateUI(1);
                break;
            case R.id.otherLayout:
                showRoomDetailPw(this, view);
                break;
            case R.id.number_reduce:
                if (mDoorTicketNum <= 1) {
                    ToaskUtil.showToast(this, "数量不能小于1");
                    return;
                }
                mDoorTicketNum--;
                doorTicketCount.setText(mDoorTicketNum + "");
                setPriceUI();
                break;
            case R.id.number_add:
                if (mDoorTicketNum >= 20) {
                    ToaskUtil.showToast(this, "数量不能大于20");
                    return;
                }
                mDoorTicketNum++;
                doorTicketCount.setText(mDoorTicketNum + "");
                setPriceUI();
                break;
            case R.id.reserve_now:

                String editNameString = editName.getEditableText().toString().replace(" ", "");
                String editTelString = editTel.getEditableText().toString().replace(" ", "");

                if (check(editNameString, editTelString)) {
                    reserveNow.setEnabled(false);
                    mLoadView = openLoadView("");
                    requestOrder(editNameString, editTelString);
                }
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

    private boolean check(String editNameString, String editTelString) {
        if (TextUtils.isEmpty(editNameString)) {
            ToaskUtil.showToast(this, "请输入证件人姓名");
            return false;
        }
        if (TextUtils.isEmpty(editTelString) || editTelString.length() != 11) {
            ToaskUtil.showToast(this, "请输入正确手机号");
            return false;
        }

        return true;
    }

    private void requestOrder(String contactName, String contactNumber) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("contactName", contactName);
        parame.put("contactNumber", contactNumber);
        parame.put("note", noteEdit.getEditableText().toString().replace(" ", ""));

        parame.put("quantity", mDoorTicketNum);
        parame.put("ticketId", mAdultTickerBean.getId());
        parame.put("ticketDay", mSelectDay.getDay().replace(" ", "_").split("_")[0]);
        parame.put("modeOfPayment", 2);//1为微信，2为现金

        for (String s : parame.keySet()) {
            Log.d("requestOrder", "requestOrder: Key:" + s + "  Value:" + parame.get(s));
        }
        HttpProxy.obtain().post(PlatformContans.Ticket.add, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                closeLoadView(mLoadView);
                reserveNow.setEnabled(true);
                LogUtil.Log("addCarRental", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        ToaskUtil.showToast(WriteDoorTickerInfoActivity.this, "下单成功");
                        //更新订单界面
                        EventBus.getDefault().post(new UpdateOrderFragment());
                        finish();
                    } else {
                        ToaskUtil.showToast(WriteDoorTickerInfoActivity.this, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {
                if (reserveNow != null) {
                    reserveNow.setEnabled(true);
                }
                closeLoadView(mLoadView);
            }
        });


    }

    /**
     * 记录上次选择的UI 类型
     */
    private int preSelectUI = -1;

    private void selectDateUI(int type) {
        if (preSelectUI == type) {
            return;
        }
        preSelectUI = type;

        todayLayout.setBackgroundResource(R.drawable.shape_transparent_content_bg);
        tomorrowLayout.setBackgroundResource(R.drawable.shape_transparent_content_bg);
        otherLayout.setBackgroundResource(R.drawable.shape_transparent_content_bg);

        //shape_border_apptheme_bg.xml
        switch (type) {
            case 0:
                todayLayout.setBackgroundResource(R.drawable.shape_border_scenic_bg);
                mSelectDay = mDayInfos.get(0);
                break;
            case 1:
                tomorrowLayout.setBackgroundResource(R.drawable.shape_border_scenic_bg);
                mSelectDay = mDayInfos.get(1);
                break;
            case 2:
                otherLayout.setBackgroundResource(R.drawable.shape_border_scenic_bg);
                break;
        }


        unitPrice.setText("¥" + mSelectDay.getPrice());

        setPriceUI();


    }

    private void setPriceUI() {
        BigDecimal countBig = new BigDecimal(mDoorTicketNum);
        double price = mSelectDay.getPrice();
        BigDecimal priceBig = new BigDecimal(price + "");

        //  TODO
        BigDecimal multiply = countBig.multiply(priceBig);
        totalPrice.setText("¥" + multiply);
    }

    private void showRoomDetailPw(Context context, View view) {
        View otherView = LayoutInflater.from(context).inflate(R.layout.pw_door_ticket_date_select_layout, null);
        if (mCustomPopWindow == null) {
            mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(context)
                    .setView(otherView)
                    .sizeByPercentage(context, 1f, 0.7f)
                    .setOutsideTouchable(true)
                    .enableBackgroundDark(true)
                    .setAnimationStyle(R.style.CustomPopWindowStyleFromBottom)
                    .setBgDarkAlpha(0.5f)
                    .create();
        }

        mCustomPopWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        handleRoomDetailView(context, otherView, mCustomPopWindow);
    }

    private void requesDateData() {
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", mAdultTickerBean.getId());
        HttpProxy.obtain().post(PlatformContans.Ticket.getPricePerDayForCustomer, parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("DayForCustomer", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        Gson gson = new Gson();
                        mDayInfos = new LinkedList<>();
                        JSONArray data = object.getJSONArray("data");
                        int length = data.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject item = data.getJSONObject(i);
                            DayInfo bean = gson.fromJson(item.toString(), DayInfo.class);
                            mDayInfos.add(bean);
                        }
                        notifyUIUpdate(mDayInfos);
                    } else {
                        ToaskUtil.showToast(WriteDoorTickerInfoActivity.this, message);
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

    /**
     * @param list 最近一个月的门票价格数据
     */
    private void notifyUIUpdate(List<DayInfo> list) {
        selectTimeLayout.setVisibility(View.VISIBLE);
        DayInfo dayInfo1 = list.get(0);
        DayInfo dayInfo2 = list.get(1);
        String s = getDateShowString(dayInfo1, 0);
        String s1 = getDateShowString(dayInfo2, 1);
        todayTime.setText(s);
        tomorrowTime.setText(s1);

        todayPrice.setText("¥" + dayInfo1.getPrice());
        tomorrowPrice.setText("¥" + dayInfo2.getPrice());

        selectDateUI(0);
    }

    private String getDateShowString(DayInfo info, int type) {
        StringBuffer result = new StringBuffer();
        if (type == 0) {
            result.append("今天");
        } else {
            result.append("明天");
        }

        String[] split = info.getDay().replace(" ", "_").split("_");
        String s = split[0];
        String replace = s.substring(5).replace("-", "/");
        result.append(replace);
        return result.toString();
    }


    private void handleRoomDetailView(final Context context, View view, final CustomPopWindow customPopWindow) {
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customPopWindow != null) {
                    customPopWindow.dissmiss();
                }
            }
        });

        MonthMoneyDataFormView2 formView = (MonthMoneyDataFormView2) view.findViewById(R.id.monthSignFormView);
        formView.setList(mDayInfos);
        formView.setOnIClickItem(this);

    }

    @Override
    public void onClickItem(MonthMoneyDataFormView2.Point point) {
        String text = point.getText();
        double roomTotal = point.getRoomTotal();
        double singular = point.getSingular();
        int index = point.getIndex();
        DayInfo dayInfo = mDayInfos.get(index);
        mSelectDay = dayInfo;
        selectDateUI(2);
        if (mCustomPopWindow != null) {
            mCustomPopWindow.dissmiss();
        }
    }
}
