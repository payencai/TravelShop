package com.tec.travelagency.home.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.common.myviewpager.CommonViewPager;
import com.tec.travelagency.common.myviewpager.ViewPagerHolderCreator;
import com.tec.travelagency.home.adapter.ViewImageHolder;
import com.tec.travelagency.home.entity.DataEntry;
import com.tec.travelagency.home.entity.RentalSelfOrderBean;
import com.tec.travelagency.widget.CustomPopWindow;
import com.tec.travelagency.widget.StarLinearLayout;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RentalSelfOrderDetailActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.activity_common_view_pager)
    CommonViewPager mCommonViewPager;
    @BindView(R.id.Star)
    StarLinearLayout Star;
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.evaluate)
    TextView evaluate;
    @BindView(R.id.scale)
    TextView scale;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.seatNumber)
    TextView seatNumber;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.specification)
    TextView specification;

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private RentalSelfOrderBean mRentalSelfOrderBean;


    @Override
    protected int getContentId() {
        return R.layout.activity_rental_self_order_detail;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        mRentalSelfOrderBean = ((RentalSelfOrderBean) intent.getSerializableExtra("RentalSelfOrderBean"));
        if (mRentalSelfOrderBean == null) {
            finish();
            return;
        }
        title.setText(mRentalSelfOrderBean.getName());
        initView2();
        initUI();
    }

    private void initUI() {
        try {
            Star.setScore((float) mRentalSelfOrderBean.getCommentData().getScore());
            score.setText(mRentalSelfOrderBean.getCommentData().getScore() + "分");
            evaluate.setText(mRentalSelfOrderBean.getCommentData().getNumber() + "条评论");
            double rate = mRentalSelfOrderBean.getCommentData().getRate();
            scale.setText("好评率: " + (rate * 100) + "%");
            name.setText(mRentalSelfOrderBean.getName());
            seatNumber.setText(getSeatNumber());
            String priceText = "¥ " + mRentalSelfOrderBean.getPrice() + "/日";
            price.setText(priceText);
            specification.setText(mRentalSelfOrderBean.getSpecification());
        } catch (Exception e) {

        }
    }

    private void initView2() {
        // 设置数据
        mCommonViewPager.setPages(mockData(), new ViewPagerHolderCreator<ViewImageHolder>() {
            @Override
            public ViewImageHolder createViewHolder() {
                // 返回ViewPagerHolder
                return new ViewImageHolder();
            }
        });
    }

    private List mockData() {
        List<DataEntry> list = new LinkedList<>();
        String images = mRentalSelfOrderBean.getImages();
        String[] split = images.split(",");
        for (String s : split) {
            list.add(new DataEntry(s));
        }

        return list;
    }

    @OnClick({R.id.back, R.id.mine, R.id.reserve_now,R.id.relative1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.mine:
                showRoomDetailPw(view);
                break;
            case R.id.reserve_now:
                Intent intent = new Intent(this, WriteRentalCarInfoActivity.class);
                intent.putExtra("RentalSelfOrderBean", mRentalSelfOrderBean);
                intent.putExtra("getSeatNumber", getSeatNumber());
                startActivity(intent);
                break;
            case R.id.relative1:
                CommentActivity.startCommentActivity(this, 2, mRentalSelfOrderBean.getId());
                break;
        }
    }

    private void showRoomDetailPw(View view) {
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
        handleRoomDetailView(otherView, customPopWindow);
    }

    private void handleRoomDetailView(View view, final CustomPopWindow customPopWindow) {
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

    public String getSeatNumber() {
        StringBuffer result = new StringBuffer();
        result.append(mRentalSelfOrderBean.getSeatNumber() + " 座\t");
        switch (mRentalSelfOrderBean.getCarType()) {
            case "1":
                result.append("汽油车");
                break;
            case "2":
                result.append("柴油车");
                break;
            case "3":
                result.append("电车");
                break;
        }

        return result.toString();
    }
}
