package com.tec.travelagency.home.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.eventBusBean.UpdatRentCarUI;
import com.tec.travelagency.home.adapter.HotelHeadImgAdapter;
import com.tec.travelagency.home.entity.HotelRoomDetail;
import com.tec.travelagency.home.entity.RentCarBean;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.widget.CustomPopWindow;
import com.tec.travelagency.widget.KyLoadingBuilder;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class RentCarDetailActivity extends BaseActivity {

    @BindView(R.id.rentCarViewpager)
    ViewPager rentCarViewpager;

    @BindView(R.id.cur_viewpager_number)
    TextView curViewpagerNum;
    @BindView(R.id.carName)
    TextView carNameText;
    @BindView(R.id.carDes)
    TextView carDesText;
    @BindView(R.id.address)
    TextView addressText;
    @BindView(R.id.telephone)
    TextView telephoneText;
    @BindView(R.id.priceEdit)
    TextView priceEdit;
    @BindView(R.id.specification)
    TextView specification;

    private RentCarBean mRentCarBean;
    private List<String> mList = new ArrayList<>();
    private KyLoadingBuilder mOpenUpdatePriceView;


    @Override
    protected int getContentId() {
        return R.layout.activity_rent_car_detail;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        mRentCarBean = (RentCarBean) intent.getSerializableExtra("rentCarBean");
        if (mRentCarBean == null) {
            finish();
            return;
        }
        getImages();
        setUI();

        HotelHeadImgAdapter hotelHeadImgAdapter = new HotelHeadImgAdapter(this, mList);
        rentCarViewpager.setAdapter(hotelHeadImgAdapter);
        rentCarViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                curViewpagerNum.setText((position + 1) + "/" + mList.size());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setUI() {
        carNameText.setText(mRentCarBean.getName());
        carDesText.setText(mRentCarBean.getIntroduce());
        addressText.setText(mRentCarBean.getAddress());
        telephoneText.setText(mRentCarBean.getTelephone());
        priceEdit.setText(mRentCarBean.getPrice() + "");
        specification.setText(mRentCarBean.getSpecification());

    }

    private void getCarRentalById() {

        Map<String, Object> parame = new HashMap<>();
        parame.put("id", mRentCarBean.getId());
        HttpProxy.obtain().get(PlatformContans.CarRental.getCarRentalById, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("getCarRentalById", result);
            }

            @Override
            public void onLtFailure(String error) {

            }
        });
    }

    @OnClick({R.id.back, R.id.priceEdit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.priceEdit:
                showSellingEditWindow(view);
                break;
        }
    }

    private void showSellingEditWindow(View view) {
        View brandView = LayoutInflater.from(this).inflate(R.layout.pw_updata_room_price_edit, null);
        CustomPopWindow customPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(brandView)
                .sizeByPercentage(this, 0.8f, 0f)
                .setOutsideTouchable(true)
                .enableBackgroundDark(true)
                .setAnimationStyle(R.style.CustomPopWindowStyle)
                .setBgDarkAlpha(0.5f)
                .create();
        handlerSynopsisEditWindow(brandView, customPopWindow);
        customPopWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    private void handlerSynopsisEditWindow(View view, final CustomPopWindow customPopWindow) {
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customPopWindow != null) {
                    customPopWindow.dissmiss();
                }
            }
        });
        final EditText sellEditText = (EditText) view.findViewById(R.id.synopsis_edit);
        sellEditText.setText(mRentCarBean.getPrice() + "");
        view.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (customPopWindow != null) {
                    customPopWindow.dissmiss();
                }
                String s = sellEditText.getEditableText().toString();
                if (TextUtils.isEmpty(s)) {
                    ToaskUtil.showToast(RentCarDetailActivity.this, "请输入价格");
                    return;
                }
                priceEdit.setText(s);
                mOpenUpdatePriceView = openLoadView("");
                updateCarPrice(s);
            }


        });


    }

    /**
     * 修改房间类型价格
     *
     * @param price
     */
    private void updateCarPrice(String price) {

        Map<String, Object> parame = new HashMap<>();
        parame.put("id", mRentCarBean.getId());
        parame.put("price", price);
        HttpProxy.obtain().post(PlatformContans.CarRental.updateCarRental, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                closeLoadView(mOpenUpdatePriceView);
                LogUtil.Log("updatePrice", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        ToaskUtil.showToast(RentCarDetailActivity.this, "修改成功");
                        EventBus.getDefault().post(new UpdatRentCarUI());
                    } else {
                        ToaskUtil.showToast(RentCarDetailActivity.this, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {
                closeLoadView(mOpenUpdatePriceView);

            }
        });

    }

    public void getImages() {
        String images = mRentCarBean.getImages();
        String[] split = images.split(",");
        for (String s : split) {
            mList.add(s);
        }
    }
}
