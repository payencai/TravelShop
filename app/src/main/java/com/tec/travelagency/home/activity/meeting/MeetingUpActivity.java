package com.tec.travelagency.home.activity.meeting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.haohaohu.citypickerview.CityPickerView;
import com.lingtao.citypickerview.style.citythreelist.CityBean;
import com.lingtao.citypickerview.style.citythreelist.ProvinceActivity;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.common.rv.abs.AbsBaseActivity;
import com.tec.travelagency.common.rv.base.Cell;
import com.tec.travelagency.eventBusBean.UpdateHotelList;
import com.tec.travelagency.home.activity.PlatformHotelListActivity;
import com.tec.travelagency.home.entity.HotelNew3Bean;
import com.tec.travelagency.home.entity.MeetingModel;
import com.tec.travelagency.home.myInterface.IHotelNew3ClickCallback;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.widget.CustomPopWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeetingUpActivity extends AbsBaseActivity<MeetingModel> {

    private int page = 1;
    private boolean isLoadMore;
    List<String> alreadyLists = new ArrayList<>();

    private String mCallNumber;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    private LinearLayout selectorLayout;
    private LinearLayout locationSelect;
    private LinearLayout starSelect;
    TextView addressShowText;
    TextView starGradle;
    ImageView arrowImg1;
    ImageView arrowImg2;

    private String curProvince = null;
    private String curCity = null;
    private String curDistrict = null;
    private int curHoTelRating = -1;



    @Override
    public void onRecyclerViewInitialized() {
       // EventBus.getDefault().register(this);

        page = 1;
        isLoadMore = false;
        addDividerItem(0);

        requestData(curProvince, curCity, curDistrict, curHoTelRating);
    }

    @Override
    public void onPullRefresh() {
        page = 1;
        isLoadMore = false;

        curProvince = null;
        curCity = null;
        curDistrict = null;
        curHoTelRating = -1;

        starGradle.setText("星级");
        addressShowText.setText("所在地");
        requestData(curProvince, curCity, curDistrict, curHoTelRating);

    }

    @Override
    public void onLoadMore() {
        page++;
        isLoadMore = true;
        requestData(curProvince, curCity, curDistrict, curHoTelRating);
    }



    @Override
    protected List<Cell> getCells(List<MeetingModel> list) {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.locationSelect:
//                showAddressSelectView(v, R.layout.pw_address_select, 0);
                Intent intent = new Intent(this, ProvinceActivity.class);
                startActivityForResult(intent, ProvinceActivity.RESULT_DATA);
                break;
            case R.id.starSelect:
                showAddressSelectView(v, R.layout.pw_star_select, 1);
        }
    }

    @Override
    public View addToolbar() {
        View view = LayoutInflater.from(this).inflate(R.layout.toobar_head_layout, null);
        view.findViewById(R.id.back).setOnClickListener(this);
        ((TextView) view.findViewById(R.id.title)).setText("已上架会议室列表");
        TextView orderText = (TextView) view.findViewById(R.id.exit);
        view.findViewById(R.id.back).setOnClickListener(this);
        orderText.setOnClickListener(this);
        return view;
    }

    @Override
    public View addAddressSelect() {

        View view = LayoutInflater.from(this).inflate(R.layout.address_select_layout, null);
        selectorLayout = ((LinearLayout) view.findViewById(R.id.selectorLayout));

        locationSelect = ((LinearLayout) view.findViewById(R.id.locationSelect));
        starSelect = ((LinearLayout) view.findViewById(R.id.starSelect));
        locationSelect.setOnClickListener(this);
        starSelect.setOnClickListener(this);

        addressShowText = (TextView) view.findViewById(R.id.addressShowText);
        starGradle = (TextView) view.findViewById(R.id.starGradle);
        arrowImg1 = (ImageView) view.findViewById(R.id.arrowImg1);
        arrowImg2 = (ImageView) view.findViewById(R.id.arrowImg2);
        return view;
    }

    private void showAddressSelectView(View view, @LayoutRes int resource, int type) {

        View pwView = LayoutInflater.from(this).inflate(resource, null);
        int hight = 0;
        if (type == 0) {
            hight = 210;
        } else {
            hight = 250;
        }
        PopupWindow pw = new PopupWindow(pwView, LinearLayout.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, hight, getResources().getDisplayMetrics()), true);
        pw.setOutsideTouchable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setAnimationStyle(R.style.pwStyle);
        pw.showAsDropDown(view, 0, 0);
        pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                addressShowText.setTextColor(Color.parseColor("#333333"));
                arrowImg1.setImageResource(R.mipmap.arrow_down);
                starGradle.setTextColor(Color.parseColor("#333333"));
                arrowImg2.setImageResource(R.mipmap.arrow_down);
            }
        });
        if (type == 0) {
            addressShowText.setTextColor(Color.parseColor("#ffa726"));
            arrowImg1.setImageResource(R.mipmap.ic_arrow_up);
            handleAddressSelectView(pwView, pw);
        } else {
            arrowImg2.setImageResource(R.mipmap.ic_arrow_up);
            starGradle.setTextColor(Color.parseColor("#ffa726"));
            handleStarSelectView(pwView, pw);

        }
    }

    private void handleAddressSelectView(View view, final PopupWindow pw) {
        final CityPickerView mView = (CityPickerView) view.findViewById(R.id.citypickerview);
        TextView resetTv = (TextView) view.findViewById(R.id.reset);
        TextView confirmTv = (TextView) view.findViewById(R.id.confirm);
        resetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressShowText.setText("所在地");
                pw.dismiss();
            }
        });
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mView.getName();
                addressShowText.setText(name);
                pw.dismiss();
                curProvince = mView.getProvince();
                curCity = mView.getCity();
                curDistrict = mView.getDistrict();
                page = 1;
                isLoadMore = false;
                requestData(curProvince, curCity, curDistrict, curHoTelRating);
            }
        });

    }

    private void handleStarSelectView(View view, final PopupWindow pw) {
        ListView starListView = (ListView) view.findViewById(R.id.starList);
        List<String> starList = getStarList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, starList);
        starListView.setAdapter(adapter);

        starListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getAdapter().getItem(position);
                starGradle.setText(item);
                curHoTelRating = position;

                page = 1;
                isLoadMore = false;
                requestData(curProvince, curCity, curDistrict, curHoTelRating);
                pw.dismiss();

            }
        });

    }

    public List<String> getStarList() {
        List<String> list = new ArrayList<>();
        list.add("无星级");
        list.add("一星级");
        list.add("二星级");
        list.add("三星级");
        list.add("四星级");
        list.add("五星级");
        return list;
    }

    private void requestData(String province, String city, String district, int hotelRating) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("page", page);
        if (!TextUtils.isEmpty(province)) {
            parame.put("province", province);
        }
        if (!TextUtils.isEmpty(city)) {
            parame.put("city", city);
        }
        if (!TextUtils.isEmpty(district)) {
            parame.put("district", district);
        }
        if (hotelRating >= 0) {
            parame.put("starLevel", hotelRating);
        }
        Log.e("params",parame.toString());
        HttpProxy.obtain().get(PlatformContans.Meeting.getAdoptMeetingList, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (isLoadMore) {
                    mBaseAdapter.hideLoadMore();
                }
                Log.e("getHotels", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONObject page = object.getJSONObject("data");
                        JSONArray beanList = page.getJSONArray("beanList");
                        int length = beanList.length();
                        if (page.getInt("pageNum") != 1 && length <= 0) {
                            ToaskUtil.showToast(MeetingUpActivity.this, "没有更多数据了");
                            return;
                        }

                        Gson gson = new Gson();
                        List<MeetingModel> tempList = new ArrayList<>();
                        for (int i = 0; i < length; i++) {
                            JSONObject item = beanList.getJSONObject(i);
                            MeetingModel bean = gson.fromJson(item.toString(), MeetingModel.class);
                            tempList.add(bean);
                        }
                        if (isLoadMore) {
                            isLoadMore = false;
                            mBaseAdapter.setData(tempList);
                        } else {
                            mBaseAdapter.setDataByRemove(tempList);
                        }
                    } else {
                        ToaskUtil.showToast(MeetingUpActivity.this, message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (isLoadMore) {
                    mBaseAdapter.hideLoadMore();
                }
            }
        });
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ProvinceActivity.RESULT_DATA) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    return;
                }

                CityBean province = data.getParcelableExtra("province");
                CityBean area = data.getParcelableExtra("area");
                CityBean city = data.getParcelableExtra("city");

                if (province != null) {
                    curProvince = province.getName();
                } else {
                    curProvince = "";
                }

                if (city != null) {
                    curCity = city.getName();
                } else {
                    curCity = "";
                }

                if (area != null) {
                    curDistrict = area.getName();
                } else {
                    curDistrict = "";
                }

                addressShowText.setText(curProvince + curCity + curDistrict);
//                Log.d("onActivityResult", "所选省市区城市： " + province.getName() + " " + province.getId() + "\n" + city.getName()
//                        + " " + city.getId() + "\n" + area.getName() + " " + area.getId() + "\n");
                requestData(curProvince, curCity, curDistrict, curHoTelRating);

            }
        }
    }





}
