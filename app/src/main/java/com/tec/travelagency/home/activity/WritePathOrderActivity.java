package com.tec.travelagency.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.home.entity.DayInfo;
import com.tec.travelagency.home.entity.InputRoomPeopleNameBean;
import com.tec.travelagency.home.entity.PathSelfDetailBean;
import com.tec.travelagency.home.entity.Traveller;
import com.tec.travelagency.home.entity.UpdateOrderFragment;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.widget.KyLoadingBuilder;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WritePathOrderActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.dayInfo)
    TextView dayInfo;
    @BindView(R.id.number_reduce)
    ImageView numberReduce;
    @BindView(R.id.doorTicketCount)
    TextView doorTicketCount;
    @BindView(R.id.number_add)
    ImageView numberAdd;
    @BindView(R.id.editName)
    EditText editName;
    @BindView(R.id.editTel)
    EditText editTel;
    @BindView(R.id.noteEdit)
    EditText noteEdit;
    @BindView(R.id.totalPrice)
    TextView totalPrice;
    @BindView(R.id.reserve_now)
    TextView reserveNow;
    @BindView(R.id.travellerList)
    RecyclerView travellerList;


    private List<Traveller> mTravellers = new LinkedList<>();


    private DayInfo mDayInfo;
    private PathSelfDetailBean mPathSelfDetailBean;
    private String mRouteId;

    /**
     * 选择的人数数量 默认为 1
     */
    private int mPeopleNumber = 1;

    private static final int MAX_SELECT_PEOPLE_NUMBER = 20;
    private RVBaseAdapter<Traveller> mRVBaseAdapter;
    private KyLoadingBuilder mLoadView;

    @Override
    protected int getContentId() {
        return R.layout.activity_write_path_order;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        mPathSelfDetailBean = ((PathSelfDetailBean) intent.getSerializableExtra("PathSelfDetailBean"));
        mDayInfo = ((DayInfo) intent.getSerializableExtra("DayInfo"));
        mRouteId = intent.getStringExtra("routeId");
        if (mDayInfo == null || TextUtils.isEmpty(mRouteId) || mPathSelfDetailBean == null) {
            finish();
            return;
        }
        title.setText("订单填写");
        name.setText(mPathSelfDetailBean.getName());
        String day = mDayInfo.getDay();
        String s = day.split(" ")[0];
        dayInfo.setText(s + " " + mPathSelfDetailBean.getCityBegin() + "出发");
        setPriceUI();
        mTravellers.add(new Traveller());
        initRecyclerView();

    }

    private void initRecyclerView() {
        mRVBaseAdapter = new RVBaseAdapter<Traveller>() {
            @Override
            protected void onViewHolderBound(RVBaseViewHolder holder, int position) {

            }
        };
        travellerList.setLayoutManager(new LinearLayoutManager(this));
        travellerList.setAdapter(mRVBaseAdapter);
        setTravellerListNumber(true);
    }

    /**
     * 设置旅行者的个数
     */
    private void setTravellerListNumber(boolean isAdd) {
//        if (isFirst) {
//            list.add(new Traveller());
//            mRVBaseAdapter.setData(list);
////            for (int i = 0; i < mPeopleNumber; i++) {
////            }
//        } else {
//
//        }

        if (isAdd) {//加
            mRVBaseAdapter.add(new Traveller());
        } else {//减
            int itemCount = mRVBaseAdapter.getItemCount();
            mRVBaseAdapter.remove(itemCount - 1);
        }
    }

    @OnClick({R.id.back, R.id.number_reduce, R.id.number_add, R.id.reserve_now})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.number_reduce:
                if (mPeopleNumber <= 1) {
                    ToaskUtil.showToast(this, "数量不能小于1");
                    return;
                }
                mPeopleNumber--;
                doorTicketCount.setText(mPeopleNumber + "");
                setPriceUI();
                setTravellerListNumber(false);
                break;
            case R.id.number_add:
                if (mPeopleNumber >= MAX_SELECT_PEOPLE_NUMBER) {
                    ToaskUtil.showToast(this, "数量不能大于20");
                    return;
                }
                mPeopleNumber++;
                doorTicketCount.setText(mPeopleNumber + "");
                setPriceUI();
                setTravellerListNumber(true);
                break;
            case R.id.reserve_now:
                String contactName = editName.getEditableText().toString().replace(" ", "");//姓名
                String contactNumber = editTel.getEditableText().toString().replace(" ", "");//号码
                String note = noteEdit.getEditableText().toString().replace(" ", "");
                if (check(contactName, contactNumber)) {
                    reserveNow.setEnabled(false);
                    mLoadView = openLoadView("");
                    String json = getJSON(contactName, contactNumber, note, 2);
                    LogUtil.Log("onViewClicked", "onViewClicked: " + json);
                    requestPlaceOrder(contactName, contactNumber, note, json);
                }
                break;
        }
    }

    private void requestPlaceOrder(String contactName, String contactNumber, String note, String json) {
        HttpProxy.obtain().post(PlatformContans.Route.add, App.getInstance().getUserEntity().getToken(), json, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("pathAddOrder", result);
                reserveNow.setEnabled(true);
                closeLoadView(mLoadView);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        ToaskUtil.showToast(WritePathOrderActivity.this, "下单成功");
                        //更新订单界面
                        EventBus.getDefault().post(new UpdateOrderFragment());
                        finish();
                    } else {
                        ToaskUtil.showToast(WritePathOrderActivity.this, message);
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

    private boolean check(String contactName, String contactNumber) {
        if (TextUtils.isEmpty(contactName)) {
            ToaskUtil.showToast(this, "请填写联系人姓名");
            return false;
        }
        if (TextUtils.isEmpty(contactNumber) || contactNumber.length() != 11) {
            ToaskUtil.showToast(this, "请填写完整联系人号码");
            return false;
        }
        List<Traveller> data = mRVBaseAdapter.getData();
        for (Traveller datum : data) {
            String idNumber = datum.getIdNumber();
            String name = datum.getName();
            String phoneNumber = datum.getPhoneNumber();
            if (TextUtils.isEmpty(idNumber) || TextUtils.isEmpty(name) || TextUtils.isEmpty(phoneNumber)) {
                ToaskUtil.showToast(this, "出行人信息不能为空");
                return false;
            }
            if (phoneNumber.length() != 11) {
                ToaskUtil.showToast(this, "请输入正确的号码");
                return false;
            }
            Log.d("check", "check: " + datum.toString());
        }

        return true;
    }

    private void setPriceUI() {
        BigDecimal countBig = new BigDecimal(mPeopleNumber);
        double price = mDayInfo.getPrice();
        BigDecimal priceBig = new BigDecimal(price + "");

        //  TODO
        BigDecimal multiply = countBig.multiply(priceBig);
        totalPrice.setText("¥" + multiply);
    }

    public String getJSON(String contactName, String contactNumber, String note, int pay) {
        StringBuffer result = new StringBuffer();
        result.append("{\"contactName\":\"" + contactName + "\",");
        result.append("\"contactNumber\":\"" + contactNumber + "\",");
        if (!TextUtils.isEmpty(note)) {
            result.append("\"note\":\"" + note + "\",");
        }
        result.append("\"modeOfPayment\":" + pay + ",");
        List<Traveller> data = mRVBaseAdapter.getData();
        int itemCount = data.size();
        result.append("\"quantity\":" + itemCount + ",");

        String day = mDayInfo.getDay();
        String s = day.split(" ")[0];
        result.append("\"routeDay\":\"" + s + "\",");
        result.append("\"routeId\":\"" + mPathSelfDetailBean.getId() + "\",");
        result.append("\"infoList\":[");
        for (int i = 0; i < data.size(); i++) {
            Traveller traveller = data.get(i);
            String itemJSON = getItemJSON(traveller);
            result.append(itemJSON);
            if (i != data.size() - 1) {
                result.append(",");
            }
        }
        result.append("]}");


        return result.toString();
    }

    private String getItemJSON(Traveller traveller) {
        StringBuffer result = new StringBuffer();
        result.append("{\"idNumber\":\"" + traveller.getIdNumber() + "\",");
        result.append("\"name\":\"" + traveller.getName() + "\",");
        result.append("\"phoneNumber\":\"" + traveller.getPhoneNumber() + "\"}");
        return result.toString();
    }

}
