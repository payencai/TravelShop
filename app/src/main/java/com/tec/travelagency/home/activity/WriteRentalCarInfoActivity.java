package com.tec.travelagency.home.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.home.entity.RentalSelfOrderBean;
import com.tec.travelagency.home.entity.UpdateOrderFragment;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.widget.CustomDatePicker;
import com.tec.travelagency.widget.KyLoadingBuilder;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class WriteRentalCarInfoActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.checkInTime)
    TextView checkInTime;
    @BindView(R.id.checkOutTime)
    TextView checkOutTime;
    @BindView(R.id.diff_day)
    TextView diffDay;

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.price)
    TextView totalPrice;
    @BindView(R.id.reserve_now)
    TextView reserveNow;
    @BindView(R.id.editName)
    EditText editName;
    @BindView(R.id.editTel)
    EditText editTel;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.noteEdit)
    EditText noteEdit;

    private CustomDatePicker startTimePicker;
    private CustomDatePicker endTimePicker;
    private RentalSelfOrderBean mRentalSelfOrderBean;
    private int mDifferenceTime;
    private KyLoadingBuilder mLoadView;

    /**
     * 选择的天数 默认为 0
     */
    private int mSelectNumDay = 0;

    @Override
    protected int getContentId() {
        return R.layout.activity_write_rental_car_info;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        mRentalSelfOrderBean = ((RentalSelfOrderBean) intent.getSerializableExtra("RentalSelfOrderBean"));
        if (mRentalSelfOrderBean == null) {
            finish();
            return;
        }
        initUI(intent);
        title.setText("信息填写");
        initStartDatePicker();
    }

    private void initUI(Intent intent) {
        String getSeatNumber = intent.getStringExtra("getSeatNumber");
        name.setText(mRentalSelfOrderBean.getName() + "\t\t\t\t" + getSeatNumber);
        totalPrice.setText("¥" + mRentalSelfOrderBean.getPrice() + " /日");
    }


    @OnClick({R.id.back, R.id.checkInTime, R.id.checkOutTime, R.id.reserve_now})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.checkInTime:
                startTimePicker.show(startRealTime);
                break;
            case R.id.checkOutTime:
                endTimePicker.show(endRealTime);
                break;
            case R.id.reserve_now:

                String editNameString = editName.getEditableText().toString().replace(" ", "");
                String editTelString = editTel.getEditableText().toString().replace(" ", "");
                String addressString = address.getEditableText().toString().replace(" ", "");

                if (check(editNameString, editTelString, addressString)) {
                    reserveNow.setEnabled(false);
                    mLoadView = openLoadView("");
                    requestOrder(editNameString, editTelString, addressString);
                }
                break;
        }
    }

    private void requestOrder(String contactName, String contactNumber, String contactLocation) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("carRentalId", mRentalSelfOrderBean.getId());
        parame.put("startTime", checkInTime.getText().toString());
        parame.put("endTime", checkOutTime.getText().toString());
        parame.put("contactName", contactName);
        parame.put("contactNumber", contactNumber);
        parame.put("contactLocation", contactLocation);
        parame.put("note", noteEdit.getEditableText().toString().replace(" ", ""));
        parame.put("modeOfPayment", 2);//1为微信，2为现金

        for (String s : parame.keySet()) {
            Log.d("requestOrder", "requestOrder: Key:" + s + "  Value:" + parame.get(s));
        }
        HttpProxy.obtain().post(PlatformContans.CarRental.add, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
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
                        ToaskUtil.showToast(WriteRentalCarInfoActivity.this, "下单成功");
                        //更新订单界面
                        EventBus.getDefault().post(new UpdateOrderFragment());
                        finish();
                    } else {
                        ToaskUtil.showToast(WriteRentalCarInfoActivity.this, message);
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

    private boolean check(String editNameString, String editTelString, String addressString) {
        if (mDifferenceTime <= 0) {
            ToaskUtil.showToast(this, "请选择日期");
            return false;
        }
        if (TextUtils.isEmpty(editNameString)) {
            ToaskUtil.showToast(this, "请输入证件人姓名");
            return false;
        }
        if (TextUtils.isEmpty(editTelString) || editTelString.length() != 11) {
            ToaskUtil.showToast(this, "请输入正确手机号");
            return false;
        }
        if (TextUtils.isEmpty(addressString)) {
            ToaskUtil.showToast(this, "请输入地址");
            return false;
        }
        return true;
    }


    //真实的时间格式
    private String startRealTime;
    private String endRealTime;

    //弹出选择时间
    private void initStartDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        String s = now.replace(" ", "_").split("_")[0];
        startRealTime = s;
        checkInTime.setText(s + " ");
        initEndDatePicker(now);
        startTimePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                initEndDatePicker(time);
                String s = time.replace(" ", "_").split("_")[0];
                startRealTime = s;
                checkInTime.setText(s + " ");

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
        String s = startTime.replace(" ", "_").split("_")[0];
        endRealTime = s;
        checkOutTime.setText(s + " ");
        endTimePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                String s = time.replace(" ", "_").split("_")[0];
                endRealTime = s;
                checkOutTime.setText(s + " ");
                mDifferenceTime = getDifferenceTime();
                diffDay.setText("共 " + mDifferenceTime + " 天");
                mSelectNumDay = mDifferenceTime;
                setPriceUI();
            }
        }, startTime, "2100-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        endTimePicker.showSpecificTime(false); // 显示时和分
        endTimePicker.setIsLoop(false); // 允许循环滚动

    }

    private void setPriceUI() {
        BigDecimal countBig = new BigDecimal(mSelectNumDay);
        double price = mRentalSelfOrderBean.getPrice();
        BigDecimal priceBig = new BigDecimal(price + "");

        //  TODO
        BigDecimal multiply = countBig.multiply(priceBig);
        totalPrice.setText("¥" + multiply);
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
}
