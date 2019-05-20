package com.tec.travelagency.home.activity.rent;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.home.activity.CommentActivity;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.orderManager.fragment.detail.CarOrderDelActivity;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.widget.ButtomDialogView;
import com.tec.travelagency.widget.StarLinearLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class CarDetailActivity extends BaseActivity {


    @BindView(R.id.hotelName)
    TextView hotelName;

    @BindView(R.id.HotelInfo)
    TextView HotelInfo;

    @BindView(R.id.Star)
    StarLinearLayout Star;
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.evaluate)
    TextView evaluate;
    @BindView(R.id.scale)
    TextView scale;

    @BindView(R.id.cartype)
    TextView cartype;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.iscancel)
    TextView iscancel;
    @BindView(R.id.callImg)
    ImageView callImg;
    @BindView(R.id.relative1)
    RelativeLayout comment;
    @BindView(R.id.seanum)
    TextView seanum;
    @BindView(R.id.des)
    TextView des;
    @BindView(R.id.troduce)
    TextView troduce;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.platform_price)
    TextView platform_price;
    @BindView(R.id.selling_price)
    TextView selling_price;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.oneselfBuy)
    TextView oneselfBuy;
    @BindView(R.id.updatePrice)
    TextView updatePrice;
    @BindView(R.id.sold_out)
    TextView sold_out;
    @BindView(R.id.up)
    TextView up;
    String id;
    //    @BindView(R.id.instr)
//    TextView instr;
    @BindView(R.id.addressDetail)
    TextView addressDetail;
    String flag;
    SaleCarBean saleCarBean;
    CarRental mCarRental;
    TravelAgencyCarRental mTravelAgencyCarRental;

    @Override
    protected int getContentId() {
        return R.layout.activity_car_detail;
    }

    private void setUi(SaleCarBean saleCarBean) {
        List<String> list = new ArrayList<>();

        Log.e("url", saleCarBean.getImage1Url());
        if (!TextUtils.isEmpty(saleCarBean.getImage1Url())) {
            list.add(saleCarBean.getImage1Url());
        }
        if (!TextUtils.isEmpty(saleCarBean.getImage2Url())) {
            list.add(saleCarBean.getImage2Url());
        }
        if (!TextUtils.isEmpty(saleCarBean.getImage3Url())) {
            list.add(saleCarBean.getImage3Url());
        }
        if (!TextUtils.isEmpty(saleCarBean.getImage4Url())) {
            list.add(saleCarBean.getImage4Url());
        }
        if (!TextUtils.isEmpty(saleCarBean.getImage5Url())) {
            list.add(saleCarBean.getImage5Url());
        }
        if (!TextUtils.isEmpty(saleCarBean.getImage6Url())) {
            list.add(saleCarBean.getImage6Url());
        }
        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                //此处可以自行选择，我直接用的Picasso
                Glide.with(CarDetailActivity.this).load((String) path).into(imageView);
            }
        });
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(2000);//设置轮播时间
        banner.setImages(list);//设置图片源
        banner.start();
        hotelName.setText(saleCarBean.getName());
        cartype.setText("车型:"+saleCarBean.getName());
        Star.setScore(saleCarBean.getCommentData().getScore());
        score.setText(saleCarBean.getCommentData().getScore() + "分");
        evaluate.setText(saleCarBean.getCommentData().getNumber() + "条评论");
        String s = saleCarBean.getCommentData().getRate() * 100 + "%";
        switch (saleCarBean.getCarType()){
            case "1":
                type.setText("类型:" + "汽油车");
                break;
            case "2":
                type.setText("类型:" + "柴油车");
                break;
            case "3":
                type.setText("类型:" + "电车");
                break;
        }
        seanum.setText("座位:" + saleCarBean.getSeatNumber() + "座");
        scale.setText("好评率:" + s);
        des.setText(saleCarBean.getSpecification());
        troduce.setText(saleCarBean.getIntroduce());
        platform_price.setVisibility(View.GONE);
        selling_price.setText("￥" + saleCarBean.getPublishPrice() + "/日");
        selling_price.setVisibility(View.VISIBLE);
        sold_out.setVisibility(View.GONE);
        oneselfBuy.setVisibility(View.GONE);
        up.setVisibility(View.VISIBLE);
        if (saleCarBean.getIsCanCancel().equals("0")) {
            iscancel.setText("不可退");
        } else {
            iscancel.setText("可退");
        }
        if (saleCarBean.getIsUsed().equals("1")) {

            selling_price.setVisibility(View.VISIBLE);
            up.setText("下架");

        }
        addressDetail.setText(saleCarBean.getAddress());
        oneselfBuy.setVisibility(View.GONE);


    }

    private void getBanner() {
        Map<String, Object> params = new HashMap<>();
        params.put("type", "6");
        HttpProxy.obtain().post(PlatformContans.Banner.getList, params, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("type5", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    if (resultCode == 0) {
                        JSONArray data = object.getJSONArray("data");
                        int length = data.length();
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < length; i++) {
                            JSONObject item = data.getJSONObject(i);
                            String imageUrl = item.getString("imageUrl");
                            list.add(imageUrl);
                        }
                        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
                            @Override
                            public void displayImage(Context context, Object path, ImageView imageView) {
                                //此处可以自行选择，我直接用的Picasso
                                Glide.with(CarDetailActivity.this).load((String) path).into(imageView);
                            }
                        });
                        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
                        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
                        banner.setDelayTime(2000);//设置轮播时间
                        banner.setImages(list);//设置图片源
                        banner.start();

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

    private void setUi(CarRental carRental, TravelAgencyCarRental travelAgencyCarRental) {

        List<String> list = new ArrayList<>();
        if (!TextUtils.isEmpty(carRental.getImage1Url())) {
            list.add(carRental.getImage1Url());
        }
        if (!TextUtils.isEmpty(carRental.getImage2Url())) {
            list.add(carRental.getImage2Url());
        }
        if (!TextUtils.isEmpty(carRental.getImage3Url())) {
            list.add(carRental.getImage3Url());
        }
        if (!TextUtils.isEmpty(carRental.getImage4Url())) {
            list.add(carRental.getImage4Url());
        }
        if (!TextUtils.isEmpty(carRental.getImage5Url())) {
            list.add(carRental.getImage5Url());
        }
        if (!TextUtils.isEmpty(carRental.getImage6Url())) {
            list.add(carRental.getImage6Url());
        }
        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                //此处可以自行选择，我直接用的Picasso
                Glide.with(CarDetailActivity.this).load((String) path).into(imageView);
            }
        });
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(2000);//设置轮播时间
        banner.setImages(list);//设置图片源
        banner.start();
        String tv_type=carRental.getCarType();
        //Log.e("type","gfg");
        switch (tv_type){
            case "1":
                type.setText("类型:" + "汽油车");
                break;
            case "2":
                type.setText("类型:" + "柴油车");
                break;
            case "3":
                type.setText("类型:" + "电车");
                break;
        }
        Log.e("ui","ui");
        cartype.setText("车型:" + carRental.getName());
        hotelName.setText(carRental.getName());
        Star.setScore(carRental.getCommentData().getScore());
        score.setText(carRental.getCommentData().getScore() + "分");
        evaluate.setText(carRental.getCommentData().getNumber() + "条评论");
        String s = carRental.getCommentData().getRate() * 100 + "%";
        seanum.setText("座位:" + carRental.getSeatNumber() + "座");
        scale.setText("好评率: " + s);
        des.setText(carRental.getSpecification());
        troduce.setText(carRental.getIntroduce());
        addressDetail.setText(carRental.getAddress());
        platform_price.setText("平台价: ￥" + travelAgencyCarRental.getPlatformPrice());
        selling_price.setText("售价: ￥" + travelAgencyCarRental.getTravelAgencyPrice());
        if (carRental.getIsCanCancel().equals("0")) {
            iscancel.setText("不可退");
        } else {
            iscancel.setText("可退");
        }
        if (travelAgencyCarRental.getIsAdopted().equals("1")) {
            sold_out.setText("下架");
            updatePrice.setVisibility(View.VISIBLE);
            selling_price.setVisibility(View.VISIBLE);
        }
        callImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + mCarRental.getContactNumber());
                intent.setData(data);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initView() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        flag = getIntent().getStringExtra("flag");
        id = getIntent().getStringExtra("id");
        updatePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyDialog(1);
            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saleCarBean.getIsUsed().equals("1")) {
                    cancel();
                } else {
                    adopt();
                }
            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentActivity.startCommentActivity(CarDetailActivity.this,2,id);
            }
        });
        sold_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTravelAgencyCarRental.getIsAdopted().equals("1")) {
                    cancel();
                } else {
                    showMyDialog(0);
                }
            }
        });
        if (flag == null)
            getDetail(id);
        else {
            Log.e("detail", "detail");
            getSaleDetail(id);
        }
        oneselfBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CarDetailActivity.this,CarOrderActivity.class);
                intent.putExtra("mCarRental",mCarRental);
                intent.putExtra("mTravelAgencyCarRental",mTravelAgencyCarRental);
                startActivityForResult(intent,3);
            }
        });
        //getBanner();
    }

    private void getSaleDetail(String id) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", id);
        String token = App.getInstance().getUserEntity().getToken();
        HttpProxy.obtain().get(PlatformContans.TravelAgency.getSaleCarDetail, token, parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {

                Log.e("getoutHotels", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    Gson gson = new Gson();
                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        saleCarBean = gson.fromJson(data.toString(), SaleCarBean.class);
                        setUi(saleCarBean);
                    } else {
                        ToaskUtil.showToast(CarDetailActivity.this, message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {

            }
        });
    }


    double price;
    @SuppressLint("MissingPermission")
    public void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + mCarRental.getContactNumber());
        intent.setData(data);
        startActivity(intent);
    }
    public void showMyDialog(final int type) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_updateprice, null);
        final ButtomDialogView dialogView = new ButtomDialogView(this, view, Gravity.CENTER);
        dialogView.show();
        TextView cancel = view.findViewById(R.id.cancel);
        TextView confrim = view.findViewById(R.id.confirm);
        final EditText et_price = view.findViewById(R.id.et_price);
        et_price.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        TextView helpprice = view.findViewById(R.id.helpprice);

        helpprice.setText("￥" + mCarRental.getManagePriceFloor() + "-" + "￥" + mCarRental.getManagePriceCeiling());
        TextView platprice = view.findViewById(R.id.platprice);
        platprice.setText("￥" + mTravelAgencyCarRental.getPlatformPrice());
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
                if(TextUtils.isEmpty(et_price.getEditableText().toString())){
                    return;
                }
                price = Double.parseDouble(et_price.getEditableText().toString());
                if (type == 0) {
                    adopt();
                } else if (type == 1) {
                    updatePrice(price);
                }
            }
        });
        Window window=dialogView.getWindow();
        WindowManager windowManager=getWindowManager();
        Display display=windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp=window.getAttributes();
        lp.width= (int) (display.getWidth()*0.8);
        window.setAttributes(lp);
    }

    private void getDetail(String id) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", id);
        String token = App.getInstance().getUserEntity().getToken();
        HttpProxy.obtain().get(PlatformContans.TravelAgency.getPlatCarDetail, token, parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {

                Log.e("plat", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    //String message = object.getString("message");
                    Gson gson = new Gson();
                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        JSONObject carRental = data.getJSONObject("carRental");
                        JSONObject travelAgencyCarRental = data.getJSONObject("travelAgencyCarRental");
                        mCarRental = gson.fromJson(carRental.toString(), CarRental.class);
                        mTravelAgencyCarRental = gson.fromJson(travelAgencyCarRental.toString(), TravelAgencyCarRental.class);
                        setUi(mCarRental, mTravelAgencyCarRental);
                    } else {
                        //ToaskUtil.showToast(CarDetailActivity.this, message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {

            }
        });
    }

    private void updatePrice(double price) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", mTravelAgencyCarRental.getId());
        parame.put("travelAgencyPrice", price);
        Log.e("param", parame.toString());
        HttpProxy.obtain().post(PlatformContans.TravelAgency.updatePlatCarPrice, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {

                LogUtil.Log("updatePrice", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        ToaskUtil.showToast(CarDetailActivity.this, "修改成功");
                        getDetail(id);
                    } else {
                        ToaskUtil.showToast(CarDetailActivity.this, message);
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


    private void adopt() {
        Map<String, Object> parame = new HashMap<>();
        String url = "";
        if (mTravelAgencyCarRental != null) {
            parame.put("price", price);
            url = PlatformContans.TravelAgency.adapotPlatCar;
            parame.put("id", mTravelAgencyCarRental.getId());
        } else {
            url = PlatformContans.TravelAgency.upSaleCar;
            parame.put("id", saleCarBean.getId());
        }

        HttpProxy.obtain().post(url, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("adopt", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        ToaskUtil.showToast(CarDetailActivity.this, "上架成功");
                        if (flag != null) {
                            up.setText("下架");
                            getSaleDetail(id);
                        }else{
                            sold_out.setText("下架");
                            updatePrice.setVisibility(View.VISIBLE);
                            selling_price.setVisibility(View.VISIBLE);
                            getDetail(id);
                        }
                    } else {
                        ToaskUtil.showToast(CarDetailActivity.this, message);
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


    private void cancel() {
        Map<String, Object> parame = new HashMap<>();
        String url = "";
        if (mTravelAgencyCarRental != null) {
            url = PlatformContans.TravelAgency.removePlatCar;
            parame.put("id", mTravelAgencyCarRental.getId());
        } else {
            url = PlatformContans.TravelAgency.downSaleCar;
            parame.put("id", saleCarBean.getId());
        }
        HttpProxy.obtain().post(url, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("cancel", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        ToaskUtil.showToast(CarDetailActivity.this, "下架成功");
                        if (flag != null) {
                            getSaleDetail(id);
                            up.setText("上架");
                        }else{
                            sold_out.setText("上架");
                            updatePrice.setVisibility(View.GONE);
                            selling_price.setVisibility(View.GONE);
                            getDetail(id);
                        }

                    } else {
                        ToaskUtil.showToast(CarDetailActivity.this, message);
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
