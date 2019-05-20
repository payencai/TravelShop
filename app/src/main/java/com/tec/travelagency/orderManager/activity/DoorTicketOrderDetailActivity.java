package com.tec.travelagency.orderManager.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.tec.travelagency.orderManager.entity.DoorTicketDetailBean;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DoorTicketOrderDetailActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.orderId)
    TextView orderId;
    @BindView(R.id.placeTime)
    TextView placeTime;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.playTime)
    TextView playTime;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.tel)
    TextView tel;
    @BindView(R.id.quantity)
    TextView quantity;
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.amount2)
    TextView amount2;
    private AccountPaidOrderInfo mOrderInfo;

    @BindView(R.id.reasonLayout)
    LinearLayout reasonLayout;
    @BindView(R.id.reason_text)
    TextView reasonText;

    @Override
    protected int getContentId() {
        return R.layout.activity_door_ticket_order_detail;
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
            url = PlatformContans.Ticket.getForTravelAgency0;
        } else {
            url = PlatformContans.Ticket.getForTravelAgency;
        }
        requestOrderDetail(url);
    }

    private void requestOrderDetail(String url) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", mOrderInfo.getId());
        String token = App.getInstance().getUserEntity().getToken();
        Log.d("requestOrderDetail", "requestOrderDetail: " + token);
        HttpProxy.obtain().get(url, token, parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("orderDetail", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        DoorTicketDetailBean bean = new Gson().fromJson(object.getJSONObject("data").toString(), DoorTicketDetailBean.class);
                        setUpdateUI(bean);
                    } else {
                        ToaskUtil.showToast(DoorTicketOrderDetailActivity.this, message);
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

    private void setUpdateUI(DoorTicketDetailBean bean) {
        orderId.setText("订单编号:" + bean.getOrderNum());
        placeTime.setText("下单时间:" + bean.getPaymentTime());
        content.setText(bean.getName());
        playTime.setText("游玩时间:" + bean.getTicketDay());
        price.setText("¥ " + bean.getPrice());
        String contactNumber = bean.getContactNumber();
        String s1 = contactNumber.substring(0, 3);
        String s2 = contactNumber.substring(contactNumber.length() - 5, contactNumber.length() - 1);
        Log.d("setUpdateUI", "setUpdateUI: " + s1);
        Log.d("setUpdateUI", "setUpdateUI: " + s2);
        String showStr = s1 + "****" + s2;
        tel.setText(bean.getContactName() + showStr);
        quantity.setText("订购数量:" + bean.getQuantity() + "张");
        amount.setText("商品合计：¥" + bean.getAmount());
        amount2.setText("实付：¥ " + bean.getAmount() + "元");

    }


    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
