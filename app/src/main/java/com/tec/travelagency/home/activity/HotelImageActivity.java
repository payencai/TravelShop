package com.tec.travelagency.home.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.common.activity.MineActivity;
import com.tec.travelagency.common.bean.RoomPhoto;
import com.tec.travelagency.common.recyclerDecoration.MyDividerItemDecoration;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.home.entity.UpImageBean;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.widget.KyLoadingBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HotelImageActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.hotel_photo_rv)
    RecyclerView hotelPhotoRv;
    @BindView(R.id.out)
    TextView out;
    @BindView(R.id.in)
    TextView in;
    Handler mHandler = new Handler();
    private RVBaseAdapter<RoomPhoto> mAdapter;
    private List<RoomPhoto> mPhotoList = new ArrayList<>();
    private Set<String> innerOldImageKey = new HashSet<>();
    private Set<String> outerOldImageKey = new HashSet<>();
    //已选的图片
    public ArrayList<String> selected = new ArrayList<>();
    //上传成功的图片key
    public List<UpImageBean> upImgList = new ArrayList<>();
    private String upOuterStr = "";
    private String upInnerStr = "";
    private KyLoadingBuilder mAddRoomLoadView;
    private boolean isOut = true;
    //上传图片的数量次数
    private int count = 0;
    private String id="";
    private boolean isLoadFinish = false;
    @Override
    protected int getContentId() {
        return R.layout.activity_hotel_image;
    }
    private void addImage(String key, String url) {
        if (!TextUtils.isEmpty(key) && !"null".equals(key)) {
            upImgList.add(new UpImageBean(key, url));
            RoomPhoto roomPhoto = new RoomPhoto();
            roomPhoto.setImgUrl(url);
            roomPhoto.canDel = false;
            roomPhoto.isEditImgKey = true;
            roomPhoto.key = key;
            mPhotoList.add(roomPhoto);
        }
    }
    @Override
    protected void initView() {
        id=getIntent().getStringExtra("id");
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
                
                if (!isLoadFinish) {
                    return;
                }


            }
        };
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title.setText("酒店图片");
        mAdapter.setData(mPhotoList);
        hotelPhotoRv.setAdapter(mAdapter);
        getImageCount(id);
        getOuterImage(id);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOut) {
                    isOut = true;
                    out.setTextColor(getResources().getColor(R.color.red));
                    in.setTextColor(getResources().getColor(R.color.color_333));
                    getOuterImage(id);
                }
            }
        });
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOut) {
                    isOut = false;
                    in.setTextColor(getResources().getColor(R.color.red));
                    out.setTextColor(getResources().getColor(R.color.color_333));
                    getInnerImage(id);
                }
            }
        });
    }




    private void getInnerImage(String id){
        selected.clear();
        upImgList.clear();
        mPhotoList.clear();
        isLoadFinish = false;
        Map<String,Object> params=new HashMap<>();
        params.put("hotelId",id);
        HttpProxy.obtain().get(PlatformContans.Hotel.getInnerImage, App.getInstance().getUserEntity().getToken(),params, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    Log.e("inner", result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONArray data = object.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject outer = (JSONObject) data.get(i);
                            String url = outer.getString("imageUrl");
                            String key = outer.getString("imageKey");
                            if (!"null".equals(key) && !TextUtils.isEmpty(key)) {
                                innerOldImageKey.add(key);
                            }
                            addImage(key, url);
                        }
                        mAdapter.setDataByRemove(mPhotoList);

                    } else {
                        ToaskUtil.showToast(HotelImageActivity.this, message);
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
    private void getOuterImage(String id){
        Map<String,Object> params=new HashMap<>();
        params.put("hotelId",id);
        selected.clear();
        upImgList.clear();
        mPhotoList.clear();
        isLoadFinish = false;
        HttpProxy.obtain().get(PlatformContans.Hotel.getOuterImage, App.getInstance().getUserEntity().getToken(),params, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    Log.e("outer", result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONArray data = object.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject outer = (JSONObject) data.get(i);
                            String url = outer.getString("imageUrl");
                            String key = outer.getString("imageKey");
                            if (!"null".equals(key) && !TextUtils.isEmpty(key)) {
                                outerOldImageKey.add(key);
                            }
                            addImage(key, url);
                            ;
                        }

                        mAdapter.setDataByRemove(mPhotoList);

                    } else {
                        ToaskUtil.showToast(HotelImageActivity.this, message);
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
    private void getImageCount(String id) {
        Map<String,Object> params=new HashMap<>();
        params.put("hotelId",id);
        HttpProxy.obtain().get(PlatformContans.Hotel.getImageNum, App.getInstance().getUserEntity().getToken(),params, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                try {
                    Log.e("num", result);
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        String tv_outer = "外景图片(" + data.getInt("outer") + ")";
                        String tv_inner = "内景图片(" + data.getInt("inner") + ")";
                        out.setText(tv_outer);
                        in.setText(tv_inner);
                    } else {
                        ToaskUtil.showToast(HotelImageActivity.this, message);
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


}
