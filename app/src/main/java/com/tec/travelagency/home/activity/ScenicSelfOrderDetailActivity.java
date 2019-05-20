package com.tec.travelagency.home.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.home.entity.ScenicSelfOrderBean;
import com.tec.travelagency.home.fragment.PathDetailContainerFragment;
import com.tec.travelagency.home.fragment.ScenicDetailContainerFragment;
import com.tec.travelagency.utils.EasyBlur;
import com.tec.travelagency.utils.FastBlurUtil;
import com.tec.travelagency.widget.CustomPopWindow;
import com.tec.travelagency.widget.StarLinearLayout;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ScenicSelfOrderDetailActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.gaussImg)
    ImageView gaussImg;
    @BindView(R.id.scenery)
    ImageView scenery;
    @BindView(R.id.picture_number)
    TextView pictureNumber;
    @BindView(R.id.des)
    TextView des;
    @BindView(R.id.address)
    TextView address;

    @BindView(R.id.Star)
    StarLinearLayout Star;
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.evaluate)
    TextView evaluate;
    @BindView(R.id.scale)
    TextView scale;

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;


    private ScenicSelfOrderBean mScenicSelfOrderBean;
    private Handler mHandler = new Handler();
    private List<String> mSmallUrls = new LinkedList<>();

    @Override
    protected int getContentId() {
        return R.layout.activity_scenic_self_order_detail;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        mScenicSelfOrderBean = ((ScenicSelfOrderBean) intent.getSerializableExtra("ScenicSelfOrderBean"));
        if (mScenicSelfOrderBean == null) {
            finish();
            return;
        }
//        Bitmap overlay = BitmapFactory.decodeResource(getResources(), R.drawable.p001);
//        Bitmap finalBitmap = EasyBlur.with(this).bitmap(overlay) //要模糊的图片
//                .radius(10)//模糊半径
//                .scale(4)//指定模糊前缩小的倍数
////                .policy(EasyBlur.BlurPolicy.FAST_BLUR)//使用fastBlur
//                .blur();
//        Glide.with(this).load(finalBitmap).into(gaussImg);
        title.setText(mScenicSelfOrderBean.getName());
        initUI();
        loadImg();
        logdSmallImg();
        initFragment();

    }

    private void initUI() {
        Star.setScore((float) mScenicSelfOrderBean.getCommentData().getScore());
        score.setText(mScenicSelfOrderBean.getCommentData().getScore() + "分");
        evaluate.setText(mScenicSelfOrderBean.getCommentData().getNumber() + "条评论");
        double rate = mScenicSelfOrderBean.getCommentData().getRate();
        scale.setText("好评率: " + (rate * 100) + "%");

    }

    private void logdSmallImg() {
        String image1Url = mScenicSelfOrderBean.getImage1Url();
        String image2Url = mScenicSelfOrderBean.getImage2Url();
        String image3Url = mScenicSelfOrderBean.getImage3Url();
        String image4Url = mScenicSelfOrderBean.getImage4Url();
        String image5Url = mScenicSelfOrderBean.getImage5Url();
        String image6Url = mScenicSelfOrderBean.getImage6Url();

        addToList(image1Url);
        addToList(image2Url);
        addToList(image3Url);
        addToList(image4Url);
        addToList(image5Url);
        addToList(image6Url);

        Glide.with(this).load(image1Url).into(scenery);
        if (mSmallUrls.size() == 0) {
            pictureNumber.setText("1/1");
        } else {
            pictureNumber.setText("1/" + mSmallUrls.size());
        }

        String introduce = mScenicSelfOrderBean.getIntroduce();
        if (TextUtils.isEmpty(introduce)) {
            des.setText("景点简介：\n");
        } else {
            des.setText("景点简介：\n" + introduce);
        }
        address.setText(mScenicSelfOrderBean.getAddress());


    }

    private void addToList(String url) {

        if (!TextUtils.isEmpty(url)) {
            if (!(url.equals("null") || url.equals("NULL"))) {
                mSmallUrls.add(url);
            }
        }

    }

    @OnClick({R.id.back, R.id.mine, R.id.des, R.id.relative1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.mine:
                showRoomDetailPw(view);
                break;
            case R.id.des:
                Intent intent = new Intent(this, SynopsisActivity.class);
                intent.putExtra("content", mScenicSelfOrderBean.getIntroduce());
                startActivity(intent);
                break;
            case R.id.relative1:
                CommentActivity.startCommentActivity(this, 4, mScenicSelfOrderBean.getId());
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

    /**
     * 加载网络高斯图片
     */
    private void loadImg() {
        //url为网络图片的url，10 是缩放的倍数（越大模糊效果越高）

        final String url = mScenicSelfOrderBean.getImage1Url();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int scaleRatio = 0;
                //                        下面的这个方法必须在子线程中执行
                final Bitmap blurBitmap2 = FastBlurUtil.GetUrlBitmap(url, scaleRatio);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        gaussImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        gaussImg.setImageBitmap(blurBitmap2);
                    }
                });
            }
        }).start();
    }

    private void initFragment() {
        ScenicDetailContainerFragment fragment = new ScenicDetailContainerFragment(mScenicSelfOrderBean);
        getSupportFragmentManager().beginTransaction().add(R.id.scenic_container, fragment).commit();
    }
}
