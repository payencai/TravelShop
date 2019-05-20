package com.tec.travelagency.orderManager.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.orderManager.entity.AccountPaidOrderInfo;
import com.tec.travelagency.orderManager.entity.AccountPaidOrderInfo2;
import com.tec.travelagency.orderManager.entity.LxsHotelOrderDetailBean;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.widget.CustomPopWindow;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class HotelOrderDetailActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.orderId)
    TextView orderId;
    @BindView(R.id.placeTime)
    TextView placeTime;

    @BindView(R.id.checkInTime)
    TextView checkInTime;
    @BindView(R.id.checkInShowTime)
    TextView checkInShowTime;
    @BindView(R.id.checkOutShowTime)
    TextView checkOutShowTime;
    @BindView(R.id.checkOutTime)
    TextView checkOutTime;
    @BindView(R.id.days_text)
    TextView daysText;

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.bedType)
    TextView bedType;
    @BindView(R.id.breakfast)
    TextView breakfast;
    @BindView(R.id.isCanCancel)
    TextView isCanCancel;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.tel)
    TextView tel;
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.amount2)
    TextView amount2;

    @BindView(R.id.reasonLayout)
    LinearLayout reasonLayout;
    @BindView(R.id.reason_text)
    TextView reasonText;
    @BindView(R.id.submit)
    TextView submit;

    public static final void startHotelOrderDetailActivity(Context activity, AccountPaidOrderInfo2 accountPaidOrderInfo2) {
        Intent intent = new Intent(activity, HotelOrderDetailActivity.class);
        intent.putExtra("accountPaidOrderInfo", accountPaidOrderInfo2);
        activity.startActivity(intent);
    }


    private AccountPaidOrderInfo mOrderInfo;

    @Override
    protected int getContentId() {
        return R.layout.activity_hotel_order_detail;
    }

    @Override
    protected void initView() {
        mOrderInfo = ((AccountPaidOrderInfo) getIntent().getSerializableExtra("accountPaidOrderInfo"));
        if (mOrderInfo == null) {
            return;
        }
        title.setText("订单详情");
        String url = "";
        if (mOrderInfo.getOrderType() == 1) {
            url = PlatformContans.Hotel.getForTravelAgency0;
        } else {
            url = PlatformContans.Hotel.getForTravelAgency;
        }
        requestOrderDetail(url);
    }

    private void requestOrderDetail(String url) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", mOrderInfo.getId());
        HttpProxy.obtain().get(url, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("orderDetail", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        LxsHotelOrderDetailBean bean = new Gson().fromJson(object.getJSONObject("data").toString(), LxsHotelOrderDetailBean.class);
                        setUpdateUI(bean);
                    } else {
                        ToaskUtil.showToast(HotelOrderDetailActivity.this, message);
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


    private void setUpdateUI(LxsHotelOrderDetailBean bean) {
        orderId.setText("订单编号:" + bean.getHotelOrderOfTravelAgency().getOrderNum());
        placeTime.setText("下单时间:" + bean.getHotelOrderOfTravelAgency().getPaymentTime());
        bedType.setText(bean.getHotelOrderOfTravelAgency().getProductNum());
        name.setText(bean.getHotel().getName());

        String startDate = bean.getHotelOrderOfTravelAgency().getFirstDate();
        String endDate = bean.getHotelOrderOfTravelAgency().getLastDate();

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
                showInString = getString(R.string.checkin_show_text);
            }

            checkInShowTime.setText(showInString);
            checkOutShowTime.setText(showOutString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        checkInTime.setText(getDate2(startDate));
        checkOutTime.setText(getDate2(endDate));

        long start = getDate(startDate).getTime();
        long end = getDate(endDate).getTime();
        int days = (int) ((end / 1000 - start / 1000) / (60 * 60 * 24)) + 1;
        daysText.setText("共" + days + "晚");


        breakfast.setText(breakfast.equals("1") ? "有早餐" : "无早餐");
        isCanCancel.setText(isCanCancel.equals("1") ? "可取消" : "不可取消");

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

    @OnClick({R.id.back, R.id.submit, R.id.cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                openReasonAgreeWindowsView(view);
                break;
            case R.id.cancel:
                openReasonCancelWindowsView(view);
                break;
        }
    }

    private void openReasonAgreeWindowsView(View view) {
        View brandView = LayoutInflater.from(this).inflate(R.layout.pw_reason_agree_option_layout, null);
        CustomPopWindow customPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(brandView)
                .sizeByPercentage(this, 0.8f, 0f)
                .setOutsideTouchable(true)
                .enableBackgroundDark(true)
                .setAnimationStyle(R.style.CustomPopWindowStyle)
                .setBgDarkAlpha(0.5f)
                .create();
        handleReasonOptionView(brandView, customPopWindow);
        customPopWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


    private void handleReasonOptionView(View view, final CustomPopWindow customPopWindow) {

        view.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customPopWindow != null) {
                    customPopWindow.dissmiss();
                }
                ToaskUtil.showToast(HotelOrderDetailActivity.this, "确定退款");
            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customPopWindow != null) {
                    customPopWindow.dissmiss();
                }
            }
        });
    }


    private void openReasonCancelWindowsView(View view) {
        View brandView = LayoutInflater.from(this).inflate(R.layout.pw_reason_reject_option_layout, null);
        CustomPopWindow customPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(brandView)
                .sizeByPercentage(this, 0.8f, 0f)
                .setOutsideTouchable(true)
                .enableBackgroundDark(true)
                .setAnimationStyle(R.style.CustomPopWindowStyle)
                .setBgDarkAlpha(0.5f)
                .create();
        handleReasonRejectOptionView(brandView, customPopWindow);
        customPopWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    //记录拒绝理由
    private String recordReason = "";

    private void handleReasonRejectOptionView(View view, final CustomPopWindow customPopWindow) {
        EditText reasonEdit = (EditText) view.findViewById(R.id.reject_reason_edit);
        reasonEdit.setText(recordReason);
        reasonEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                recordReason = s.toString();
            }
        });
        final String reasonString = reasonEdit.getEditableText().toString();


        view.findViewById(R.id.reject_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(reasonString)) {
                    ToaskUtil.showToast(HotelOrderDetailActivity.this, "请输入拒绝理由");
                    return;
                }
                ToaskUtil.showToast(HotelOrderDetailActivity.this, "拒绝退款提交");
                if (customPopWindow != null) {
                    customPopWindow.dissmiss();
                }
            }
        });
        view.findViewById(R.id.reject_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customPopWindow != null) {
                    customPopWindow.dissmiss();
                }
            }
        });
    }


}
