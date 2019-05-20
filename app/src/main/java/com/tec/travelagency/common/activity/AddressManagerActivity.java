package com.tec.travelagency.common.activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.google.gson.Gson;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.common.bean.AddressComponent;
import com.tec.travelagency.common.bean.WebViewAddressBean;
import com.tec.travelagency.eventBusBean.UpdateMineUI;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.widget.KyLoadingBuilder;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AddressManagerActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.save)
    Button save;
    @BindView(R.id.address)
    TextView addressText;
    @BindView(R.id.doorNumString)
    EditText doorNumString;

    private static int ADDRESS_REQUEST_CODE = 1 << 1;
    private WebViewAddressBean mAddressBean;
    private KyLoadingBuilder mSaveAddressView;

    @Override
    protected int getContentId() {
        return R.layout.activity_address_manager;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        String addressDetail = intent.getStringExtra("addressDetail");
        try {
            addressText.setText(address);
            doorNumString.setText(addressDetail);
        } catch (Exception e) {

        }

        title.setText("选择地址");

    }

    @OnClick({R.id.back, R.id.save, R.id.selectAddressLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.save:
                if (mAddressBean == null) {
                    ToaskUtil.showToast(this, "请选择地址");
                    return;
                }
                mSaveAddressView = openLoadView("");
                updataAddress();
                break;
            case R.id.selectAddressLayout:
                startActivityForResult(new Intent(this,X5WebviewActivity.class),3);
//                SelectAddressActivity.startSelectAddressActivity(this, "address", RQUEST_ADDRESS_CODE, consignSite.getText().toString());
                //AddressWebViewActivity.startWebView(this, "http://120.79.176.228:8080/gaote-web/map/index.html", ADDRESS_REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 3) {
                String address = data.getStringExtra("address");
                mAddressBean = new Gson().fromJson(address, WebViewAddressBean.class);
                getAddress(mAddressBean.getLatlng().getLat(), mAddressBean.getLatlng().getLng());
            }
        }
    }


    //放入经纬度就可以了
    public void getAddress(double latitude, double longitude) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("ak", "s3aRos5HZ1okvMZ7Vg4UIukq2BgrclIm");
        parame.put("callback", "renderReverse");
        parame.put("location", "" + latitude + "," + longitude);
        parame.put("output", "json");
        parame.put("mcode", "5A:4B:3E:4D:6A:BD:C7:00:11:C3:D2:53:F6:08:1C:CB:91:B2:7E:14;com.tec.travelagency");
        parame.put("pois", 1);
        HttpProxy.obtain().get("http://api.map.baidu.com/geocoder/v2/", parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("addressString", result);
                try {
                    String replace = result.replace("renderReverse&&renderReverse(", "");
                    JSONObject object = new JSONObject(replace);
                    int status = object.getInt("status");
                    if (status == 0) {
                        JSONObject bean = object.getJSONObject("result").getJSONObject("addressComponent");
                        AddressComponent addressComponent = new Gson().fromJson(bean.toString(), AddressComponent.class);
                        Log.d("OnLtSuccess", "OnLtSuccess: " + addressComponent.toString());
                        mAddressBean.setAddressComponent(addressComponent);
                        addressText.setText(mAddressBean.getPoiaddress());
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


    private void updataAddress() {

        Map<String, Object> parame = new HashMap<>();
        WebViewAddressBean.LatlngBean latlng = mAddressBean.getLatlng();
        parame.put("longitude", latlng.getLng());
        parame.put("latitude", latlng.getLat());
        parame.put("address", mAddressBean.getPoiaddress());
        String detailString = doorNumString.getEditableText().toString().replace(" ", "");
        parame.put("addressDetail", detailString);

        AddressComponent addressComponent = mAddressBean.getAddressComponent();
        if (addressComponent == null) {
            mSaveAddressView.dismiss();
            ToaskUtil.showToast(this, "获取位置失败");
            return;
        }
        parame.put("province", addressComponent.getProvince());
        parame.put("city", addressComponent.getCity());
        parame.put("district", addressComponent.getDistrict());

        HttpProxy.obtain().post(PlatformContans.TravelAgency.updateLocation, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                closeLoadView(mSaveAddressView);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        ToaskUtil.showToast(AddressManagerActivity.this, "保存成功");
                        EventBus.getDefault().post(new UpdateMineUI());
                        finish();
                    } else {
                        ToaskUtil.showToast(AddressManagerActivity.this, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {
                closeLoadView(mSaveAddressView);

            }
        });
    }
}
