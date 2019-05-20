package com.tec.travelagency.common.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.common.bean.RoomPhoto;
import com.tec.travelagency.common.recyclerDecoration.DividerGridItemDecoration;
import com.tec.travelagency.common.recyclerDecoration.MyDividerItemDecoration;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.home.entity.HotelInfo;
import com.tec.travelagency.home.entity.UpImageBean;
import com.tec.travelagency.eventBusBean.UpdateMineUI;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.manager.ActivityManager;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.utils.UserInfoSharedPre;
import com.tec.travelagency.widget.CustomPopWindow;
import com.tec.travelagency.widget.KyLoadingBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MineActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.exit)
    TextView exit;
    @BindView(R.id.hotel_photo_rv)
    RecyclerView hotelPhotoRv;
    @BindView(R.id.scrollLayout)
    ScrollView scrollLayout;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.synopsis)
    TextView synopsis;
    @BindView(R.id.mine_pic)
    ImageView mMinePic;

    Handler mHandler = new Handler();
    private RVBaseAdapter<RoomPhoto> mAdapter;
    private List<RoomPhoto> mPhotoList = new ArrayList<>();
    //已选的图片
    public ArrayList<String> selected = new ArrayList<>();

    //上传成功的图片key
    public List<UpImageBean> upImgList = new ArrayList<>();

    //记录编辑的文字
    String recordEditString;
    public static final int REQUEST_CODE = 0;
    public static final int SINGLE_REQUEST_CODE = 1;
    private HotelInfo mHotelInfo;
    private KyLoadingBuilder mUpdateLogoView;

    //上传图片的数量次数
    private int count = 0;
    private KyLoadingBuilder mAddRoomLoadView;
    private boolean isLoadFinish = false;

    @Override
    protected int getContentId() {
        return R.layout.activity_mine;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        title.setText("我的");
        exit.setVisibility(View.VISIBLE);
        //scrollLayout.fullScroll(ScrollView.FOCUS_UP);
        hotelPhotoRv.setLayoutManager(new GridLayoutManager(this, 3));
        //设置分隔线
        hotelPhotoRv.addItemDecoration(new MyDividerItemDecoration(15));
        //设置增加或删除条目的动画
        hotelPhotoRv.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RVBaseAdapter<RoomPhoto>() {
            @Override
            protected void onViewHolderBound(RVBaseViewHolder holder, int position) {

            }

            @Override
            protected void onViewClick(RVBaseViewHolder holder, final int position) {
//                ImageView delImg = holder.getImageView(R.id.del);
//                RoomPhoto roomPhoto = getData().get(position);
//                final String imgUrl = roomPhoto.getImgUrl();
//                delImg.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        changeSelectphoto(imgUrl);
//                    }
//                });

                if (!isLoadFinish) {
                    return;
                }
                ImageView delImg = holder.getImageView(R.id.del);
                final RoomPhoto roomPhoto = getData().get(position);

                delImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeSelectphoto(roomPhoto);
                    }
                });
            }
        };
        mAdapter.setData(mPhotoList);
        hotelPhotoRv.setAdapter(mAdapter);

        requestHotelInfo();

    }

    private void requestHotelInfo() {

        HttpProxy.obtain().get(PlatformContans.TravelAgency.getMyInfo, App.getInstance().getUserEntity().getToken(), new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                try {
                    LogUtil.Log("getMyInfo", result);
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        Gson gson = new Gson();
                        mHotelInfo = gson.fromJson(data.toString(), HotelInfo.class);
                        isLoadFinish = true;
                        upMineUI(mHotelInfo);
                    } else {
                        ToaskUtil.showToast(MineActivity.this, message);
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

    //网络请求成功，
    private void upMineUI(HotelInfo hotelInfo) {
        name.setText(hotelInfo.getName());
        Glide.with(this).load(hotelInfo.getLogoUrl()).into(mMinePic);
        String Instr = hotelInfo.getInstr();
        if (!TextUtils.isEmpty(Instr)) {
            if (Instr.equals("null")) {
                synopsis.setText("");
            } else {
                synopsis.setText(Instr);
            }
        }

        String image1Key = hotelInfo.getImage1();
        String image2Key = hotelInfo.getImage2();
        String image3Key = hotelInfo.getImage3();
        String image4Key = hotelInfo.getImage4();
        String image5Key = hotelInfo.getImage5();
        String image6Key = hotelInfo.getImage6();

        String image1Url = hotelInfo.getImage1Url();
        String image2Url = hotelInfo.getImage2Url();
        String image3Url = hotelInfo.getImage3Url();
        String image4Url = hotelInfo.getImage4Url();
        String image5Url = hotelInfo.getImage5Url();
        String image6Url = hotelInfo.getImage6Url();

        addAlreadyImage(image1Key, image1Url);
        addAlreadyImage(image2Key, image2Url);
        addAlreadyImage(image3Key, image3Url);
        addAlreadyImage(image4Key, image4Url);
        addAlreadyImage(image5Key, image5Url);
        addAlreadyImage(image6Key, image6Url);

        if (mPhotoList.size() < 6) {//添加最后一张图片
            RoomPhoto roomPhoto = new RoomPhoto();
            mPhotoList.add(0,roomPhoto);
        }
        mAdapter.setDataByRemove(mPhotoList);

    }

    @Subscribe
    public void upDateUI(UpdateMineUI updateMineUI) {
        selected.clear();
        upImgList.clear();
        mPhotoList.clear();
        isLoadFinish = false;
        requestHotelInfo();
    }

    private void addAlreadyImage(String key, String url) {
        if (!TextUtils.isEmpty(key)) {
            if (!key.equals("null")) {

                upImgList.add(new UpImageBean(key, url));

                RoomPhoto roomPhoto = new RoomPhoto();
                roomPhoto.setImgUrl(url);
                roomPhoto.canDel = true;
                roomPhoto.isEditImgKey = true;
                roomPhoto.key = key;

                mPhotoList.add(roomPhoto);
            }
        }
    }

    @OnClick({R.id.back, R.id.synopsis_edit_btn, R.id.minePositionLayout, R.id.mineTelLayout, R.id.exit, R.id.mine_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.synopsis_edit_btn:
                showSynopsisEditWindow(view);
                break;
            case R.id.minePositionLayout:
                if (mHotelInfo == null) {
                    return;
                }
                Intent intent1 = new Intent(this, AddressManagerActivity.class);
                intent1.putExtra("address", mHotelInfo.getAddress());
                intent1.putExtra("addressDetail", mHotelInfo.getAddressDetail());

                startActivity(intent1);

                break;
            case R.id.mineTelLayout:
                if (mHotelInfo == null) {
                    return;
                }
                Intent intent = new Intent(this, TelephoneManagerActivity.class);
                String contactNumber = mHotelInfo.getContactNumber();
                if (!TextUtils.isEmpty(contactNumber)) {
                    if (contactNumber.equals("null")) {
                        contactNumber = "";
                    }
                } else {
                    contactNumber = "";
                }

                intent.putExtra("tel", contactNumber);
                startActivity(intent);
                break;
            case R.id.exit:
                ActivityManager.getInstance().finishAllActivity();
                EMClient.getInstance().logout(true);
                UserInfoSharedPre intance = UserInfoSharedPre.getIntance(this);
                intance.clearUserInfo();
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.mine_pic:
                //单选
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(true)  //设置是否单选
                        .start(this, SINGLE_REQUEST_CODE); // 打开相册

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            ArrayList<String> images = data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
            mPhotoList.clear();
            selected.clear();
            int size = images.size();
            int len = upImgList.size();
            for (int i = 0; i < len; i++) {
                UpImageBean bean = upImgList.get(i);
                RoomPhoto roomPhoto = new RoomPhoto();
                roomPhoto.setImgUrl(bean.url);
                roomPhoto.canDel = true;
                roomPhoto.isEditImgKey = true;
                roomPhoto.key = bean.key;
                mPhotoList.add(roomPhoto);

            }

            for (int i = 0; i < size; i++) {
                RoomPhoto roomPhoto = new RoomPhoto();
                String imgUrl = images.get(i);
                roomPhoto.setImgUrl(imgUrl);
                roomPhoto.canDel = true;
                Log.d("onActivityResult", "onActivityResult: " + imgUrl);
                mPhotoList.add(0,roomPhoto);
                selected.add(imgUrl);
            }
            if (mPhotoList.size() < 6) {//添加最后一张图片
                RoomPhoto roomPhoto = new RoomPhoto();
                mPhotoList.add(0,roomPhoto);
            }
            mAdapter.setDataByRemove(mPhotoList);
            commitImage();
        }
        if (requestCode == SINGLE_REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            ArrayList<String> images = data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);

            String url = images.get(0);
            if (TextUtils.isEmpty(url)) {
                return;
            }
            mUpdateLogoView = openLoadView("");
//            Glide.with(this).load(url).into(mMinePic);
            updateLogoImg(url);
        }
    }

    private void updateLogoImg(String fileUrl) {
        // Log.e("tag",url+filePath);
        OkHttpClient mOkHttpClent = new OkHttpClient();
        File file = new File(fileUrl);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "image",
                        RequestBody.create(MediaType.parse("image/png"), file));
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(PlatformContans.Image.uploadImage)
                .post(requestBody)
                .build();
        Call call = mOkHttpClent.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MineActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtil.Log("requestAddRoomType", "onResponse: " + string);
                try {
                    JSONObject object = new JSONObject(string);
                    int resultCode = object.getInt("resultCode");
                    if (resultCode == 0) {
                        final String imgKey = object.getString("data");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateLogo(imgKey);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        });
    }

    private void updateLogo(String logoKey) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("logo", logoKey);
        HttpProxy.obtain().post(PlatformContans.TravelAgency.uploadLogo, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                if (mUpdateLogoView != null) {
                    mUpdateLogoView.dismiss();
                }
                LogUtil.Log("updateLogo", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        ToaskUtil.showToast(MineActivity.this, "更新Logo成功");
                    } else {
                        ToaskUtil.showToast(MineActivity.this, message);
                    }
                    selected.clear();
                    upImgList.clear();
                    mPhotoList.clear();
                    isLoadFinish = false;
                    requestHotelInfo();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {
                if (mUpdateLogoView != null) {
                    mUpdateLogoView.dismiss();
                }
            }
        });

    }


    private void commitImage() {
        if (mAddRoomLoadView != null) {
            mAddRoomLoadView = null;
        }
        mAddRoomLoadView = openLoadView("添加中...");
        if (selected.size() != 0) {
            for (String filepath : selected) {
                upImage(PlatformContans.Image.uploadImage, filepath);
            }
        } else {
            updataHotelImage();
        }
    }

    private void upImage(String url, String filePath) {
        // Log.e("tag",url+filePath);
        OkHttpClient mOkHttpClent = new OkHttpClient();
        File file = new File(filePath);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "image",
                        RequestBody.create(MediaType.parse("image/png"), file));
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = mOkHttpClent.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MineActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtil.Log("requestAddRoomType", "onResponse: " + string);
                try {
                    JSONObject object = new JSONObject(string);
                    int resultCode = object.getInt("resultCode");
                    if (resultCode == 0) {
                        String imgKey = object.getString("data");
                        upImgList.add(new UpImageBean(imgKey, null));
                        count++;
                        if (count == selected.size()) {
                            count = 0;
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    updataHotelImage();
                                }
                            });
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void changeSelectphoto(RoomPhoto roomPhotoSrc) {
        final String imgUrlSrc = roomPhotoSrc.getImgUrl();
        if (roomPhotoSrc.isEditImgKey) {
            for (int i = 0; i < upImgList.size(); i++) {
                UpImageBean upImageBean = upImgList.get(i);
                if (imgUrlSrc.equals(upImageBean.url)) {
                    upImgList.remove(upImageBean);
                }
            }
        } else {
            selected.remove(imgUrlSrc);
        }
        mPhotoList.clear();

        int sizeKeyNum = upImgList.size();
        int sizeSelectedNum = selected.size();

        int totalSize = sizeKeyNum + sizeSelectedNum;

        for (int i = 0; i < sizeKeyNum; i++) {
            RoomPhoto roomPhoto = new RoomPhoto();
            UpImageBean bean = upImgList.get(i);
            roomPhoto.setImgUrl(bean.url);
            roomPhoto.canDel = true;
            roomPhoto.isEditImgKey = true;
            roomPhoto.key = bean.key;
            mPhotoList.add(roomPhoto);
        }

        for (int i = 0; i < sizeSelectedNum; i++) {
            RoomPhoto roomPhoto = new RoomPhoto();
            String imgUrl = selected.get(i);
            roomPhoto.setImgUrl(imgUrl);
            roomPhoto.canDel = true;
            mPhotoList.add(roomPhoto);

        }
        if (totalSize < 6) {//添加最后一张图片
            RoomPhoto roomPhoto = new RoomPhoto();
            mPhotoList.add(0,roomPhoto);
        }
        mAdapter.setDataByRemove(mPhotoList);
        commitImage();
    }

    private void showSynopsisEditWindow(View view) {
        View brandView = LayoutInflater.from(this).inflate(R.layout.pw_synopsis_edit, null);
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
        final EditText editText = (EditText) view.findViewById(R.id.synopsis_edit);
        recordEditString = synopsis.getText().toString();
        editText.setText(recordEditString);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                recordEditString = s.toString();
            }
        });
        view.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAddRoomLoadView != null) {
                    mAddRoomLoadView = null;
                }
                mAddRoomLoadView = openLoadView("");
                recordEditString = "";
                updateIntroduce(editText.getEditableText().toString());
                if (customPopWindow != null) {
                    customPopWindow.dissmiss();
                }
            }
        });

    }

    private void updataHotelImage() {

        Map<String, Object> parame = new HashMap<>();
        for (int i = 1; i <= upImgList.size(); i++) {
            String key = upImgList.get(i - 1).key;
            parame.put("image" + i, key);
        }
        if (upImgList.size() < 6) {
            for (int i = upImgList.size() + 1; i < 7; i++) {
                parame.put("image" + i, "");
            }
        }

        HttpProxy.obtain().post(PlatformContans.TravelAgency.uploadImage, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                closeLoadView(mAddRoomLoadView);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        selected.clear();
                        upImgList.clear();
                        mPhotoList.clear();
                        isLoadFinish = false;
                        ToaskUtil.showToast(MineActivity.this, "更新成功");
                        requestHotelInfo();
                    } else {
                        ToaskUtil.showToast(MineActivity.this, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onLtFailure(String error) {
                closeLoadView(mAddRoomLoadView);

            }
        });

    }

    private void updateIntroduce(String introduce) {
        if (introduce == null) {
            introduce = "";
        }
        Map<String, Object> parame = new HashMap<>();
        parame.put("introduce", introduce);
        HttpProxy.obtain().post(PlatformContans.TravelAgency.updateIntroduce, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                closeLoadView(mAddRoomLoadView);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        selected.clear();
                        upImgList.clear();
                        mPhotoList.clear();
                        isLoadFinish = false;
                        requestHotelInfo();
                    } else {
                        ToaskUtil.showToast(MineActivity.this, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {
                closeLoadView(mAddRoomLoadView);

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
