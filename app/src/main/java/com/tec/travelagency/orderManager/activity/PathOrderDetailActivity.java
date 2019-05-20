package com.tec.travelagency.orderManager.activity;

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
import com.tec.travelagency.orderManager.entity.PathDetailBean;
import com.tec.travelagency.orderManager.entity.RentCarDetailBean;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class PathOrderDetailActivity extends BaseActivity {


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
        return R.layout.activity_path_order_detail;
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
            url = PlatformContans.Route.getForTravelAgency0;
        } else {
            url = PlatformContans.Route.getForTravelAgency;
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
                        PathDetailBean bean = new Gson().fromJson(object.getJSONObject("data").toString(), PathDetailBean.class);
                        setUpdateUI(bean);
                    } else {
                        ToaskUtil.showToast(PathOrderDetailActivity.this, message);
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

    private void setUpdateUI(PathDetailBean bean) {
        orderId.setText("订单编号:" + bean.getOrderNum());
        placeTime.setText("下单时间:" + bean.getPaymentTime());
        content.setText(bean.getName());

        playTime.setText("游玩时间:" + bean.getRouteDay());

        price.setText("¥ " + bean.getPrice());
        String contactNumber = bean.getContactNumber();
        String s11 = contactNumber.substring(0, 3);
        String s22 = contactNumber.substring(contactNumber.length() - 5, contactNumber.length() - 1);
        String showStr = s11 + "****" + s22;
        tel.setText(bean.getContactName() + showStr);

        quantity.setText("出行人数:" + bean.getQuantity() + "人");
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
