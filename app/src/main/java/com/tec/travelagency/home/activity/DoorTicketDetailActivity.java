package com.tec.travelagency.home.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.home.adapter.HotelHeadImgAdapter;
import com.tec.travelagency.home.entity.DoorTicket;
import com.tec.travelagency.home.entity.DoorTicketDetailUIBean;
import com.tec.travelagency.home.entity.ScenicBean;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class DoorTicketDetailActivity extends BaseActivity {
    @BindView(R.id.door_viewpager)
    ViewPager DoorViewpager;

    @BindView(R.id.cur_viewpager_number)
    TextView curViewpagerNum;
    @BindView(R.id.priceShow)
    TextView priceShow;
    @BindView(R.id.doorName)
    TextView doorName;
    @BindView(R.id.doorDes)
    TextView doorDes;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.typeName)
    TextView typeName;
    @BindView(R.id.reservation)
    TextView reservation;
    @BindView(R.id.fee)
    TextView fee;
    @BindView(R.id.use)
    TextView use;
    @BindView(R.id.refund)
    TextView refund;

    private DoorTicket mDoorTicket;
    private ScenicBean mScenicBean;
    //图片的路径
    private List<String> mList;
    private HotelHeadImgAdapter mHotelHeadImgAdapter;

    @Override
    protected int getContentId() {
        return R.layout.activity_door_ticket_detail;
    }

    private void setUI() {
        priceShow.setText(mDoorTicket.getPrice() + "");
        obtainUrl();
        doorName.setText(mScenicBean.getName());
        doorDes.setText(mScenicBean.getIntroduce());
    }

    private void setUI2(DoorTicketDetailUIBean doorTicketDetailUIBean) {
        DoorTicketDetailUIBean.TicketBean ticket = doorTicketDetailUIBean.getTicket();
        DoorTicketDetailUIBean.TicketSpecificationBean specification = doorTicketDetailUIBean.getTicketSpecification();

        name.setText(ticket.getName());
        typeName.setText(ticket.getTypeName());
        reservation.setText(specification.getReservation());
        fee.setText(specification.getFee());
        use.setText(specification.getUse());
        refund.setText(specification.getRefund());

    }

    @Override
    protected void initView() {
        mDoorTicket = ((DoorTicket) getIntent().getSerializableExtra("doorTicket"));
        if (mDoorTicket == null) {
            finish();
            return;
        }
        mScenicBean = mDoorTicket.getScenicBean();
        if (mScenicBean == null) {
            finish();
            return;
        }
        mList = new ArrayList<>();
        requestDoorDetailData();
        mHotelHeadImgAdapter = new HotelHeadImgAdapter(this, mList);
        DoorViewpager.setAdapter(mHotelHeadImgAdapter);
        DoorViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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


        setUI();
    }

    private void obtainUrl() {
        String image1Url = mScenicBean.getImage1Url();
        String image2Url = mScenicBean.getImage2Url();
        String image3Url = mScenicBean.getImage3Url();
        String image4Url = mScenicBean.getImage4Url();
        String image5Url = mScenicBean.getImage5Url();
        String image6Url = mScenicBean.getImage6Url();
        setList(image1Url);
        setList(image2Url);
        setList(image3Url);
        setList(image4Url);
        setList(image5Url);
        setList(image6Url);
        mHotelHeadImgAdapter.notifyDataSetChanged();
    }

    public void setList(String url) {
        if (!TextUtils.isEmpty(url)) {
            if (!url.equals("null") || !url.equals("NULL")) {
                mList.add(url);
            }
        }

    }

    private void requestDoorDetailData() {
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", mDoorTicket.getId());
        HttpProxy.obtain().get(PlatformContans.Ticket.getDetail, parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("getDetail", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONObject item = object.getJSONObject("data");
                        DoorTicketDetailUIBean doorTicketDetailUIBean = new Gson().fromJson(item.toString(), DoorTicketDetailUIBean.class);
                        setUI2(doorTicketDetailUIBean);
                    } else {
                        ToaskUtil.showToast(DoorTicketDetailActivity.this, message);
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


    @OnClick({R.id.back, R.id.priceShow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.priceShow:
                Intent intent = new Intent(this, SelectUpdateDataActivity.class);
                intent.putExtra("DoorTicket", mDoorTicket);
                startActivity(intent);
                break;
        }
    }


}
