package com.tec.travelagency.home.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.home.entity.DateSelectBean;
import com.tec.travelagency.home.entity.DayDetailDesignBean;
import com.tec.travelagency.home.entity.DayInfo;
import com.tec.travelagency.home.entity.PathSelfDetailBean;
import com.tec.travelagency.home.entity.PathSelfOrderBean;
import com.tec.travelagency.home.entity.SelfImageBean;
import com.tec.travelagency.home.fragment.PathDetailContainerFragment;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.orderManager.entity.PathDetailBean;
import com.tec.travelagency.utils.DateTools;
import com.tec.travelagency.utils.FastBlurUtil;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.widget.CustomPopWindow;
import com.tec.travelagency.widget.MonthMoneyDataFormView2;
import com.tec.travelagency.widget.StarLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class PathSelfOrderDetailActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.gaussImg)
    ImageView gaussImg;
    @BindView(R.id.date_select_rv)
    RecyclerView dateSelectRv;

    @BindView(R.id.Star)
    StarLinearLayout Star;
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.evaluate)
    TextView evaluate;
    @BindView(R.id.scale)
    TextView scale;

    @BindView(R.id.picture_number)
    TextView pictureNumber;
    @BindView(R.id.pathContent)
    TextView pathContent;
    @BindView(R.id.cityBegin)
    TextView cityBegin;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.main_tv_order)
    TextView mainTvOrder;
    @BindView(R.id.reserve_now)
    TextView reserve_now;
    @BindView(R.id.totalPrice)
    TextView totalPrice;
    @BindView(R.id.scenery)
    ImageView scenery;


    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private RVBaseAdapter<DateSelectBean> mBeanRVBaseAdapter;
    private List<DateSelectBean> mDateSelectBeans = new LinkedList<>();
    //一个月的全部信息
    private List<DayInfo> mDataSelectOtherBeans = new ArrayList<>();

    //外层的路线信息
    private PathSelfOrderBean mPathSelfOrderBean;

    //路线的详情信息
    private PathSelfDetailBean mPathSelfDetailBean;

    private Handler mHandler = new Handler();

    //当前选择的日期信息
    private DayInfo curSelectDayInfo;
    //路线id
    private String mRouteId;

    @Override
    protected int getContentId() {
        return R.layout.activity_path_self_order_detail;
    }

    @Override
    protected void initView() {
//        Bitmap overlay = BitmapFactory.decodeResource(getResources(), R.drawable.p001);
//        Bitmap finalBitmap = EasyBlur.with(this).bitmap(overlay) //要模糊的图片
//                .radius(10)//模糊半径
//                .scale(4)//指定模糊前缩小的倍数
////                .policy(EasyBlur.BlurPolicy.FAST_BLUR)//使用fastBlur
//                .blur();
//        Glide.with(this).load(finalBitmap).into(gaussImg);

        Intent intent = getIntent();
        mPathSelfOrderBean = ((PathSelfOrderBean) intent.getSerializableExtra("PathSelfOrderBean"));
        if (mPathSelfOrderBean == null) {
            finish();
            return;
        }

        title.setText("路线详情");

        EventBus.getDefault().register(this);

        initUI();
        initRecycview();
        //服务号获取路线当月的价格
        getPricePerDayForCustomer();
        loadImg(mPathSelfOrderBean.getImageUrl());
        initContainerFragment();
        //服务号获取路线详情
        getDetailForCustomer();
    }

    private void initUI() {
        cityBegin.setText(mPathSelfOrderBean.getCityBegin() + "出发");
        Glide.with(this).load(mPathSelfOrderBean.getImageUrl()).into(scenery);
        Star.setScore((float) mPathSelfOrderBean.getCommentData().getScore());
        score.setText(mPathSelfOrderBean.getCommentData().getScore() + "分");
        evaluate.setText(mPathSelfOrderBean.getCommentData().getNumber() + "条评论");
        int rate = (int) mPathSelfOrderBean.getCommentData().getRate();
        scale.setText("好评率: " + (rate * 100) + "%");
        pathContent.setText("路线行程:\n" + mPathSelfOrderBean.getName());
        price.setText("¥" + mPathSelfOrderBean.getPrice() + "起/人");
    }


    /**
     * 加载网络高斯图片
     */
    private void loadImg(final String imgUrl) {
        //url为网络图片的url，10 是缩放的倍数（越大模糊效果越高）

        new Thread(new Runnable() {
            @Override
            public void run() {
                int scaleRatio = 0;
                //                        下面的这个方法必须在子线程中执行
                final Bitmap blurBitmap2 = FastBlurUtil.GetUrlBitmap(imgUrl, scaleRatio);
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

    private void initContainerFragment() {
        PathDetailContainerFragment fragment = new PathDetailContainerFragment(mPathSelfOrderBean);
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
    }


    private void initRecycview() {
        mBeanRVBaseAdapter = new RVBaseAdapter<DateSelectBean>() {
            @Override
            protected void onViewHolderBound(RVBaseViewHolder holder, int position) {

            }

            @Override
            protected void onViewClick(RVBaseViewHolder holder, final int position) {

                holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mainTvOrder.setText("其他日期");
                        List<DateSelectBean> data = getData();
                        DateSelectBean dateSelectBean = data.get(position);
                        if (dateSelectBean.isChoose()) {
                            return;
                        }

                        for (DateSelectBean datum : data) {
                            datum.setChoose(false);
                        }
                        DateSelectBean selectBean = data.get(position);
                        selectBean.setChoose(true);

                        curSelectDayInfo = mDataSelectOtherBeans.get(position);
                        totalPrice.setText("¥" + curSelectDayInfo.getPrice());
                        notifyDataSetChanged();
                    }
                });
            }
        };
        mBeanRVBaseAdapter.setDataByRemove(mDateSelectBeans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dateSelectRv.setLayoutManager(linearLayoutManager);
        dateSelectRv.setAdapter(mBeanRVBaseAdapter);
    }


    @OnClick({R.id.back, R.id.mine, R.id.other_time, R.id.reserve_now,R.id.relative1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.mine:
                showRoomDetailPw(view);
                break;
            case R.id.other_time:
                if (mDataSelectOtherBeans.size() <= 0) {
                    ToaskUtil.showToast(this, "数据请求中...");
                    return;
                }
                Intent intent = new Intent(this, SelectDatePriceActivity.class);
                intent.putExtra("mDataSelectOtherBeans", (Serializable) mDataSelectOtherBeans);
                startActivity(intent);
                break;
            case R.id.reserve_now:
                if (curSelectDayInfo == null || TextUtils.isEmpty(mRouteId)) {
                    ToaskUtil.showToast(this, "数据加载中");
                    return;
                }
                Intent intent1 = new Intent(this, WritePathOrderActivity.class);
                intent1.putExtra("DayInfo", curSelectDayInfo);
                intent1.putExtra("PathSelfDetailBean", mPathSelfDetailBean);
                intent1.putExtra("routeId", mRouteId);
                startActivity(intent1);
                break;
            case R.id.relative1:
                CommentActivity.startCommentActivity(this, 3, mPathSelfOrderBean.getId());
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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 其他日期的选择的结果
     *
     * @param point
     */
    @Subscribe
    public void otherDataSelect(MonthMoneyDataFormView2.Point point) {
        List<DateSelectBean> data = mBeanRVBaseAdapter.getData();
        //在集合中的位置
        int index = point.getIndex();

        if (index < 7) {
            for (DateSelectBean datum : data) {
                datum.setChoose(false);
            }
            DateSelectBean selectBean = data.get(index);
            selectBean.setChoose(true);

        } else {
            for (DateSelectBean datum : data) {
                datum.setChoose(false);
            }
            mainTvOrder.setText(point.getDrawMonNumber() + "-" + point.getText());
        }

        mBeanRVBaseAdapter.notifyDataSetChanged();

        DayInfo dayInfo = mDataSelectOtherBeans.get(index);
        curSelectDayInfo = dayInfo;
        totalPrice.setText("¥" + curSelectDayInfo.getPrice());


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

    public void getPricePerDayForCustomer() {
        ///route/getPricePerDayForCustomer
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", mPathSelfOrderBean.getId());
        HttpProxy.obtain().get(PlatformContans.Route.getPricePerDayForCustomer, parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("DayForCustomer", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String messsage = object.getString("message");
                    if (resultCode == 0) {
                        JSONArray data = object.getJSONArray("data");
                        int length = data.length();
                        Gson gson = new Gson();
                        for (int i = 0; i < length; i++) {
                            JSONObject item = data.getJSONObject(i);
                            DayInfo bean = gson.fromJson(item.toString(), DayInfo.class);
                            mDataSelectOtherBeans.add(bean);
                        }
                        //初始化
                        curSelectDayInfo = mDataSelectOtherBeans.get(0);
                        totalPrice.setText("¥" + curSelectDayInfo.getPrice());

                        for (int i = 0; i < 7; i++) {
                            JSONObject item = data.getJSONObject(i);
                            DateSelectBean bean = gson.fromJson(item.toString(), DateSelectBean.class);
                            //得到星期几
                            String datetime = bean.getDay().split(" ")[0];
                            String dateString = datetime.substring(5);
                            String goOffString = DateTools.dateToWeek(datetime);
                            if (i == 0) {
                                bean.setChoose(true);
                            } else {
                                bean.setChoose(false);
                            }
                            bean.setDateString(dateString);
                            bean.setGoOffString(goOffString + "\n出发");
                            mDateSelectBeans.add(bean);
                        }
                        mBeanRVBaseAdapter.setDataByRemove(mDateSelectBeans);

                    } else {
                        ToaskUtil.showToast(PathSelfOrderDetailActivity.this, messsage);
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

    public void getDetailForCustomer() {
        // /route/getDetailForCustomer
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", mPathSelfOrderBean.getId());
        HttpProxy.obtain().get(PlatformContans.Route.getDetailForCustomer, parame, new ICallBack() {

            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("getDetailForCustomer", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        mRouteId = data.getString("id");
                        JSONArray routePerdayList = data.getJSONArray("routePerdayList");
                        JSONArray routeScenicSpotList = data.getJSONArray("routeScenicSpotList");
                        int length = routePerdayList.length();
                        int lengthScenic = routeScenicSpotList.length();

                        Gson gson = new Gson();
                        List<DayDetailDesignBean> list = new LinkedList<>();
                        List<SelfImageBean> selfImageBeans = new LinkedList<>();

                        for (int i = 0; i < length; i++) {
                            JSONObject item = routePerdayList.getJSONObject(i);
                            DayDetailDesignBean bean = gson.fromJson(item.toString(), DayDetailDesignBean.class);
                            list.add(bean);
                        }

                        for (int i = 0; i < lengthScenic; i++) {
                            JSONObject item = routeScenicSpotList.getJSONObject(i);
                            SelfImageBean bean = gson.fromJson(item.toString(), SelfImageBean.class);
                            selfImageBeans.add(bean);
                        }

                        mPathSelfDetailBean = gson.fromJson(data.toString(), PathSelfDetailBean.class);
                        EventBus.getDefault().post(list);//更新内部第一个Fragment（PathDetailFragment） 的列表
                        EventBus.getDefault().post(mPathSelfDetailBean);//更新内部后两个fragment 的界面

                        if (selfImageBeans.size() > 0) {
                            String imageUrl = selfImageBeans.get(0).getImageUrl();
                            loadImg(imageUrl);
                            Glide.with(PathSelfOrderDetailActivity.this).load(imageUrl).into(scenery);
                            pictureNumber.setText("1/" + selfImageBeans.size());
                        }


                    } else {
                        ToaskUtil.showToast(PathSelfOrderDetailActivity.this, message);
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

    public static class DayPriceInfo implements Serializable {

        private String id;
        private String routeId;
        private String day;
        private double price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRouteId() {
            return routeId;
        }

        public void setRouteId(String routeId) {
            this.routeId = routeId;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }


}
