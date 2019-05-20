package com.tec.travelagency.home.activity.ticket;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.baiduMap.LocationService;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.eventBusBean.UpdateHotelList;
import com.tec.travelagency.home.activity.CommentActivity;
import com.tec.travelagency.home.activity.meeting.TakeOrderActivity;
import com.tec.travelagency.home.activity.route.PathDetailActivity;
import com.tec.travelagency.home.adapter.HotelHeadImgAdapter;
import com.tec.travelagency.home.entity.MeetDelBean;
import com.tec.travelagency.home.rvItemCallBack.RvItemActionListener;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.LoginSharedUilt;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.widget.ButtomDialogView;
import com.tec.travelagency.widget.CustomPopWindow;
import com.tec.travelagency.widget.KyLoadingBuilder;
import com.tec.travelagency.widget.StarLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class PlatTicketsDetailActivity extends BaseActivity implements RvItemActionListener {
    @BindView(R.id.relative1)
    RelativeLayout comment;
    @BindView(R.id.hotel_viewpager)
    ViewPager hotelViewpager;
    @BindView(R.id.hotel_room_rv)
    RecyclerView hotelRoomRv;
    @BindView(R.id.hotelName)
    TextView hotelName;
    @BindView(R.id.hotelTel)
    TextView hotelTel;
    @BindView(R.id.Star)
    StarLinearLayout Star;
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.evaluate)
    TextView evaluate;
    @BindView(R.id.scale)
    TextView scale;
    @BindView(R.id.callImg)
    ImageView callImg;
    @BindView(R.id.addressDetail)
    TextView addressDetail;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.back)
    ImageView back;
    PlatTicketsDetailBean mSaleTicketsBean;
    PlatTicketsSubDetailBean bean;
    private List<PlatTicketSubBean> mList = new ArrayList<>();
    private KyLoadingBuilder mOpenUpdatePriceView;

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    private List<String> mVPImagerUrlList;

    private PlatTicketSubAdapter mRoomDetailAdapter;

    private KyLoadingBuilder mOpenAdoptView;

    private KyLoadingBuilder mOpenCancelView;

    private final int SDK_PERMISSION_REQUEST = 127;

    private String permissionInfo;

    private LocationService locationService;
    String flag = "0";
    String id = null;

    @Override
    protected int getContentId() {
        locationService = App.getInstance().locationService;
        locationService.registerListener(myListener);
        return R.layout.activity_plat_tickets_detail;
    }

    @Override
    protected void initView() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        id = getIntent().getStringExtra("id");
        flag = getIntent().getStringExtra("flag");
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentActivity.startCommentActivity(PlatTicketsDetailActivity.this,4,mSaleTicketsBean.getId());
            }
        });
        initRv();
        getSaleTicketsDetail(id);
        requestHotelRoomData(id);
    }

    private void getBanner() {
        mVPImagerUrlList = new ArrayList<>();
        getVPImagerUrlList();
        HotelHeadImgAdapter hotelHeadImgAdapter = new HotelHeadImgAdapter(this, mVPImagerUrlList);
        hotelHeadImgAdapter.setOnClickImageListener(new HotelHeadImgAdapter.OnClickImageListener() {
            @Override
            public void onClick(Context context) {

            }
        });
        hotelViewpager.setAdapter(hotelHeadImgAdapter);

    }

    private void setData() {
        desc.setText(mSaleTicketsBean.getOneWord());
        hotelName.setText(mSaleTicketsBean.getName());
        Star.setScore(mSaleTicketsBean.getCommentData().getScore());
        scale.setText(mSaleTicketsBean.getCommentData().getRate() * 100 + "%好评");
        score.setText(mSaleTicketsBean.getCommentData().getScore() + "分");
        evaluate.setText(mSaleTicketsBean.getCommentData().getNumber() + "条评价");
        addressDetail.setText(mSaleTicketsBean.getAddress() + mSaleTicketsBean.getAddressDetail());
        getBanner();
    }

    private void getSaleTicketsDetail(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        HttpProxy.obtain().get(PlatformContans.Ticket.getPlatTicketsDetail, App.getInstance().getUserEntity().getToken(), map, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                Log.e("getSaleTicketsDetail", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        mSaleTicketsBean = new Gson().fromJson(data.toString(), PlatTicketsDetailBean.class);
                        setData();
                    } else {

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


    private void requestHotelRoomData(String id) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("scenicSpotId", id);
        HttpProxy.obtain().get(PlatformContans.Ticket.getPlatTicketsDetailList, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {

            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("geDetailtHotels", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {

                        JSONArray data = object.getJSONArray("data");
                        Gson gson = new Gson();
                        int length = data.length();
                        mList.clear();
                        for (int i = 0; i < length; i++) {
                            JSONObject item = data.getJSONObject(i);
                            PlatTicketSubBean bean = gson.fromJson(item.toString(), PlatTicketSubBean.class);
                            mList.add(bean);
                        }
                        mRoomDetailAdapter.notifyDataSetChanged();

                    } else {
                        ToaskUtil.showToast(PlatTicketsDetailActivity.this, message);
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


    private void initRv() {
        hotelRoomRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        hotelRoomRv.setLayoutManager(new LinearLayoutManager(this));
        mRoomDetailAdapter = new PlatTicketSubAdapter(this, mList);
        mRoomDetailAdapter.setOnItemActionListener(this);
        hotelRoomRv.setAdapter(mRoomDetailAdapter);
    }


    /**
     * 请求房间类型详情接口
     *
     * @param roomDetail
     */
    private void getRoomTypeDetail(final PlatTicketSubBean roomDetail) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", roomDetail.getId());
        HttpProxy.obtain().get(PlatformContans.Ticket.getPlatTicketsSubDetail, App.getInstance().getUserEntity().getToken(), map, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("getRoomtypeDetail", result);
                Log.e("detail", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        bean = new Gson().fromJson(data.toString(), PlatTicketsSubDetailBean.class);
                        showRoomDetailDialog(bean);

                    } else {

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

    private void showRoomDetailDialog(PlatTicketsSubDetailBean roomDetail) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_ticket_plat, null);
        ButtomDialogView dialogView = new ButtomDialogView(this, view, Gravity.BOTTOM);
        dialogView.show();
        setDialogData(view, roomDetail, dialogView);
    }

    private void setDialogData(View view, final PlatTicketsSubDetailBean meetingBean, final Dialog dialog) {

        ImageView head = view.findViewById(R.id.head);
        TextView periodOfValidity = view.findViewById(R.id.periodOfValidity);
        TextView regulation = view.findViewById(R.id.regulation);
        TextView name = view.findViewById(R.id.name);
        TextView warmPrompt = view.findViewById(R.id.warmPrompt);
        TextView platform_price = view.findViewById(R.id.platform_price);
        TextView selling_price = view.findViewById(R.id.selling_price);
        TextView adapot = view.findViewById(R.id.sold_out);
        TextView buy = view.findViewById(R.id.oneselfBuy);
        TextView updatePrice = view.findViewById(R.id.updatePrice);
        platform_price.setText("平台价: ￥" + meetingBean.getTravelAgencyTicket().getPlatformPrice());
        selling_price.setText("售价: ￥" + meetingBean.getTravelAgencyTicket().getTravelAgencyPrice());
        final ImageView cancel = view.findViewById(R.id.cancel);
        regulation.setText(meetingBean.getTicket().getRegulation());
        name.setText(meetingBean.getTicket().getName());
        // platform_price.setText("平台价: ￥" + meetingBean.getOneSignPrice());
        Glide.with(dialog.getContext()).load(meetingBean.getTicket().getImage1Url()).into(head);
        periodOfValidity.setText(meetingBean.getTicket().getPeriodOfValidity());
        warmPrompt.setText(meetingBean.getTicket().getWarmPrompt());
        if (meetingBean.getTravelAgencyTicket().getIsAdopted().equals("2")) {
            adapot.setText("上架");
            selling_price.setVisibility(View.GONE);
            platform_price.setVisibility(View.VISIBLE);
            updatePrice.setVisibility(View.GONE);
        } else {
            adapot.setText("下架");
            selling_price.setVisibility(View.VISIBLE);
            platform_price.setVisibility(View.VISIBLE);
            updatePrice.setVisibility(View.VISIBLE);
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        updatePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(meetingBean, 1,dialog);
            }
        });
        adapot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (meetingBean.getTravelAgencyTicket().getIsAdopted().equals("2"))
                    showDialog(meetingBean, 0,dialog);
                else if (meetingBean.getTravelAgencyTicket().getIsAdopted().equals("1")) {
                    cancel(meetingBean);
                }
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(PlatTicketsDetailActivity.this, TicketOrderActivity.class);
                intent.putExtra("ticket", bean);
                startActivity(intent);
                //TakeOrderActivity.startHotelDetail(dialog.getContext(), meetDelBean, meetingBean.getName());
            }
        });

    }

    private void showDialog(final PlatTicketsSubDetailBean hotelRoomTypeDetail, final int type, final Dialog dialog) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_updateprice, null);
        final ButtomDialogView dialogView = new ButtomDialogView(this, view, Gravity.CENTER);
        dialogView.show();
        Window window = dialogView.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.CENTER);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        //window.setWindowAnimations(R.style.mypopwindow_anim_style);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        Display display = getWindowManager().getDefaultDisplay();
        //设置窗口宽度为充满全屏
        lp.width = (int) (display.getWidth() * 0.8);
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        TextView cancel = view.findViewById(R.id.cancel);
        TextView confrim = view.findViewById(R.id.confirm);
        final EditText et_price = view.findViewById(R.id.et_price);
        et_price.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        TextView helpprice = view.findViewById(R.id.helpprice);

        //helpprice.setText("￥" + hotelRoomTypeDetail.getConferenceRoomType().getManagePriceFloor() + "-" + "￥" + hotelRoomTypeDetail.getConferenceRoomType().getManagePriceCeiling());
        TextView platprice = view.findViewById(R.id.platprice);
        platprice.setText("￥" + hotelRoomTypeDetail.getTravelAgencyTicket().getPlatformPrice());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
            }
        });
        confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
                price = Double.parseDouble(et_price.getEditableText().toString());
                if(TextUtils.isEmpty(et_price.getEditableText().toString())){
                    return;
                }
                if (type == 0) {
                    mOpenAdoptView = openLoadView("");
                    adopt(hotelRoomTypeDetail);
                } else if (type == 1) {
                    mOpenUpdatePriceView = openLoadView("");
                    updateRoomPrice(hotelRoomTypeDetail, price,dialog);
                }
            }
        });


    }


    private boolean isCanAdd(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        if (url.equals("null")) {
            return false;
        }
        if (url.equals("NULL")) {
            return false;
        }
        return true;
    }


    @OnClick({R.id.back, R.id.relative1, R.id.callImg
            , R.id.addressLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.relative1:
                //CommentActivity.startCommentActivity(this, 1, mMeetingModel.getId());
                break;
            case R.id.callImg:
                 callPhone();
                //showCallPhoneView(view);
                break;

            case R.id.addressLayout:
                getPersimmions();
                break;
        }
    }

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            } else {
                // startNavigation();
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(permission)) {
                Log.d("addPermission", "addPermission: 111111");
                return true;
            } else {
                Log.d("addPermission", "addPermission: 22222");
                permissionsList.add(permission);
                return false;
            }

        } else {
            Log.d("addPermission", "addPermission: 333333");
            return true;
        }
    }


//    private void startNavigation() {
//        LoginSharedUilt intance = LoginSharedUilt.getIntance(this);
//        double lat = intance.getLat();
//        double lon = intance.getLon();
//        if (lat == 0 || lon == 0) {
//            ToaskUtil.showToast(this, "正在定位中...");
//            return;
//        }
//
//        Intent intent = new Intent();
//        Bundle bundle = new Bundle();
//        intent.putExtra("bundle", bundle);
//
//        double latValue = Double.parseDouble(mMeetingModel.getLatitude());
//        double lonValue = Double.parseDouble(mMeetingModel.getLongitude());
//        Log.d("onViewClicked", "目标维度: " + latValue);
//        Log.d("onViewClicked", "目标经度: " + lonValue);
//
//        Log.d("onViewClicked", "开始维度: " + lat);
//        Log.d("onViewClicked", "开始经度: " + lon);
//
//        bundle.putDouble("latNumber", latValue);//目标维度
//        bundle.putDouble("lonNumber", lonValue);//目标经度
//        bundle.putString("currentCity", mMeetingModel.getCity());
//        bundle.putString("startNodeStr", "");
//        bundle.putDouble("startLat", lat);//开始维度
//        bundle.putDouble("startLon", lon);//开始经度
//        intent.setClass(this, RoutePlanDemo.class);
//        startActivity(intent);
//    }

    public void getVPImagerUrlList() {

        addToList(mSaleTicketsBean.getImage1Url());
        addToList(mSaleTicketsBean.getImage2Url());
        addToList(mSaleTicketsBean.getImage3Url());
        addToList(mSaleTicketsBean.getImage4Url());
        addToList(mSaleTicketsBean.getImage5Url());
        addToList(mSaleTicketsBean.getImage6Url());


    }

    private void addToList(String url) {
        if (!TextUtils.isEmpty(url)) {
            if (!url.equals("null")) {
                mVPImagerUrlList.add(url);
            }
        }
    }

    /**
     * 修改房间类型价格
     *
     * @param roomDetail
     * @param price
     */
    private void updateRoomPrice(PlatTicketsSubDetailBean roomDetail, double price, final Dialog dialog) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", roomDetail.getTravelAgencyTicket().getId());
        parame.put("travelAgencyPrice", price);
        Log.e("param", parame.toString());
        HttpProxy.obtain().post(PlatformContans.Ticket.updatePlatTicketsPrice, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                closeLoadView(mOpenUpdatePriceView);
                LogUtil.Log("updatePrice", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        ToaskUtil.showToast(PlatTicketsDetailActivity.this, "修改成功");
                        dialog.dismiss();
                        requestHotelRoomData(id);
                    } else {
                        ToaskUtil.showToast(PlatTicketsDetailActivity.this, message);
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

    private double price;

    /**
     * 添加房型
     *
     * @param roomDetail
     */
    private void adopt(PlatTicketsSubDetailBean roomDetail) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", roomDetail.getTravelAgencyTicket().getId());
        parame.put("price", price);
        HttpProxy.obtain().post(PlatformContans.Ticket.upPlatickets, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("adopt", result);
                closeLoadView(mOpenAdoptView);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        ToaskUtil.showToast(PlatTicketsDetailActivity.this, "上架成功");
                        requestHotelRoomData(id);
                        EventBus.getDefault().post(new UpdateHotelList());
                    } else {
                        ToaskUtil.showToast(PlatTicketsDetailActivity.this, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {
                closeLoadView(mOpenAdoptView);

            }
        });

    }

    /**
     * 添加房型
     *
     * @param roomDetail
     */
    private void cancel(PlatTicketsSubDetailBean roomDetail) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", roomDetail.getTravelAgencyTicket().getId());
        HttpProxy.obtain().post(PlatformContans.Ticket.downPlatTickets, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("cancel", result);
                closeLoadView(mOpenCancelView);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        ToaskUtil.showToast(PlatTicketsDetailActivity.this, "下架成功");
                        requestHotelRoomData(id);
                        EventBus.getDefault().post(new UpdateHotelList());
                    } else {
                        ToaskUtil.showToast(PlatTicketsDetailActivity.this, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {
                closeLoadView(mOpenCancelView);

            }
        });

    }

    @Override
    public void OnItemClick(int position) {
        PlatTicketSubBean roomDetail = mList.get(position);
//        roomDetail.setName(mMeetingModel.getName());
//        String isAdopted = roomDetail.getIsAdopted();
        getRoomTypeDetail(roomDetail);
    }

    private void showCallPhoneView(View view) {
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
        handleCallPhoneView(otherView, customPopWindow);
    }

    private void handleCallPhoneView(View view, final CustomPopWindow customPopWindow) {
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customPopWindow != null) {
                    customPopWindow.dissmiss();
                }
            }
        });

        TextView callText = (TextView) view.findViewById(R.id.tel);
        //callText.setText(mMeetingModel.getContactNumber());
        callText.setOnClickListener(new View.OnClickListener() {
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
        Uri data = Uri.parse("tel:" + mSaleTicketsBean.getContactNumber());
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
        if (requestCode == SDK_PERMISSION_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationService.start();
            } else {
                // Permission Denied
                Toast.makeText(this, "使用导航定位权限为必须权限", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        locationService.unregisterListener(myListener); //注销掉监听
        locationService.stop(); //停止定位服务
    }

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDAbstractLocationListener myListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            int LocType = location.getLocType();    //返回码


            lat = location.getLatitude();
            lon = location.getLongitude();

            Log.d("onReceiveLocation", "onReceiveLocation: " + lat);
            Log.d("onReceiveLocation", "onReceiveLocation: " + lon);

            saveLocation(city, addr);
            locationService.setLocationOption(locationService.getSingleLocationClientOption());
        }

    };

    private double lat;
    private double lon;

    public void saveLocation(String city, String addr) {
        LoginSharedUilt intance = LoginSharedUilt.getIntance(this);
        intance.saveLat(lat);
        intance.saveLon(lon);
        intance.saveCity(city);
        intance.saveAddr(addr);
        //startNavigation();

    }

}


