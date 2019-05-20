package com.tec.travelagency.home.activity.meeting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.haohaohu.citypickerview.CityPickerView;
import com.lingtao.citypickerview.style.citythreelist.CityBean;
import com.lingtao.citypickerview.style.citythreelist.ProvinceActivity;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.eventBusBean.UpdateHotelList;
import com.tec.travelagency.home.activity.HotelDetailActivity;
import com.tec.travelagency.home.activity.HotelManagerNewActivity;
import com.tec.travelagency.home.activity.HotelSelfOrderActivity;
import com.tec.travelagency.home.activity.PlatformHotelListActivity;
import com.tec.travelagency.home.adapter.RVAdapter;
import com.tec.travelagency.home.adapter.RVMeetAdapter;
import com.tec.travelagency.home.entity.HotelNew2Bean;
import com.tec.travelagency.home.entity.MeetingModel;
import com.tec.travelagency.home.rvItemCallBack.RvItemActionListener;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.widget.CustomPopWindow;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class NewMeetingActivity extends BaseActivity implements  RvItemActionListener  {


    @BindView(R.id.bottom_layout)
    LinearLayout bottom_layout;
    @BindView(R.id.base_fragment_rv)
    RecyclerView baseFragmentRv;
    @BindView(R.id.base_refresh_layout)
    SwipeRefreshLayout baseRefreshLayout;
    @BindView(R.id.bottom_column)
    LinearLayout bottom_column;

    @BindView(R.id.selectorLayout)
    LinearLayout selectorLayout;
    @BindView(R.id.addressShowText)
    TextView addressShowText;
    @BindView(R.id.starGradle)
    TextView starGradle;
    @BindView(R.id.arrowImg1)
    ImageView arrowImg1;
    @BindView(R.id.arrowImg2)
    ImageView arrowImg2;
    @BindView(R.id.arrowImg3)
    ImageView arrowImg3;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_all)
    TextView tv_adapot;
    @BindView(R.id.headLayout)
    LinearLayout head;
    private int page = 1;
    //是否加载更多
    private boolean isLoadMore = false;
    /**
     * RecyclerView 最后可见Item在Adapter中的位置
     */
    private int mLastVisiblePosition = -1;

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    private String curProvince = null;
    private String curCity = null;
    private String curDistrict = null;
    private int curHoTelRating = -1;
    private String name=null;
    private String isAdapot=null;
    private List<MeetingModel> list = new ArrayList<>();
    private RVMeetAdapter mAdapter;


    List<String> alreadyLists = new ArrayList<>();


    @Override
    protected int getContentId() {
        return R.layout.activity_new_meeting;
    }

    @Override
    protected void initView() {

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name=s.toString();
                requestData(name,isAdapot,curProvince, curCity, curDistrict, curHoTelRating);
            }
        });
        baseRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isLoadMore = false;
                requestData(name,isAdapot,curProvince, curCity, curDistrict, curHoTelRating);
            }
        });

        baseFragmentRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RVMeetAdapter(this, list);
        mAdapter.setOnItemActionListener(this);
        baseFragmentRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        baseFragmentRv.setAdapter(mAdapter);
        baseFragmentRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    mLastVisiblePosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                } else if (layoutManager instanceof GridLayoutManager) {
                    mLastVisiblePosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    int[] lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                    staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                    mLastVisiblePosition = findMax(lastPositions);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                View firstView = recyclerView.getChildAt(0);
                if (firstView == null) {
                    return;
                }
                int top = firstView.getTop();
                int topEdge = recyclerView.getPaddingTop();
                //判断RecyclerView 的ItemView是否满屏，如果不满一屏，上拉不会触发加载更多
                boolean isFullScreen = top < topEdge;
                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                int itemCount = manager.getItemCount();

                if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisiblePosition == itemCount - 1 && isFullScreen) {

                    if (isLoadMore) {
                        return;
                    }
                    bottom_column.setVisibility(View.VISIBLE);
                    page++;
                    isLoadMore = true;

                    requestData(name,isAdapot,curProvince, curCity, curDistrict, curHoTelRating);
//                    requestData(null, null, null, -1);
                }
            }
        });
        getBanner();
        requestData(name,isAdapot,curProvince, curCity, curDistrict, curHoTelRating);

    }


    /**
     * 获取组数最大值
     *
     * @param lastPositions
     * @return
     */
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
    private void getHeader(List<String> images) {

        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                //此处可以自行选择，我直接用的Picasso
                Glide.with(context).load((String) path).into(imageView);
            }
        });
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(2000);//设置轮播时间
        banner.setImages(images);//设置图片源
        banner.start();

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
                        getHeader(list);

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

    @OnClick({R.id.back, R.id.bottom_layout, R.id.locationSelect, R.id.starSelect,R.id.all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.bottom_layout:
                startActivity(new Intent(this, MeetingUpActivity.class));
                break;
            case R.id.locationSelect:
//                showAddressSelectView(view, R.layout.pw_address_select, 0);
                Intent intent = new Intent(this, ProvinceActivity.class);
                startActivityForResult(intent, ProvinceActivity.RESULT_DATA);
                break;
            case R.id.starSelect:
                showAddressSelectView(view, R.layout.pw_star_select, 1);
                break;
            case R.id.all:
                showAdapotSelectView(view, R.layout.pw_star_select, 1);
        }
    }
    private void showAdapotSelectView(View view, @LayoutRes int resource, int type) {

        View pwView = LayoutInflater.from(this).inflate(resource, null);
        int hight = 0;
        if (type == 0) {
            hight = 210;
        } else {
            hight = 300;
        }
        PopupWindow pw = new PopupWindow(pwView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        pw.setOutsideTouchable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setAnimationStyle(R.style.pwStyle);
        pw.showAsDropDown(view, 0, 0);
        pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tv_adapot.setTextColor(Color.parseColor("#333333"));
                arrowImg3.setImageResource(R.mipmap.arrow_down);
            }
        });
        if (type == 0) {
            tv_adapot.setTextColor(Color.parseColor("#ffa726"));
            arrowImg3.setImageResource(R.mipmap.ic_arrow_up);
            handleAddressSelectView(pwView, pw);
        } else {
            arrowImg3.setImageResource(R.mipmap.ic_arrow_up);
            tv_adapot.setTextColor(Color.parseColor("#ffa726"));
            handleAdapotSelectView(pwView, pw);

        }
    }
    public List<String> getAdapotList() {
        List<String> list = new ArrayList<>();
        list.add("全部");
        list.add("已上架");
        list.add("未上架");
        return list;
    }
    private void handleAdapotSelectView(View view, final PopupWindow pw) {
        ListView starListView = (ListView) view.findViewById(R.id.starList);
        List<String> starList = getAdapotList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, starList);
        starListView.setAdapter(adapter);
        starListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getAdapter().getItem(position);
                if("已上架".equals(item));
                   isAdapot="1";
                if("未上架".equals(item))
                    isAdapot="2";
                if("全部".equals(item))
                    isAdapot=null;
                tv_adapot.setText(item);
                page = 1;
                isLoadMore = false;
                requestData(name,isAdapot,curProvince, curCity, curDistrict, curHoTelRating);
                pw.dismiss();

            }
        });

    }
    private void requestData(String name,String isAdapot, String province, String city, String district, int hotelRating) {
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
        if (!TextUtils.isEmpty(isAdapot)) {
            parame.put("isAdded", isAdapot);
        }
        if (!TextUtils.isEmpty(name)) {
            parame.put("name", name);
        }
        if (hotelRating >= 0) {
            parame.put("starLevel", hotelRating+"");
        }
        Log.e("pa",parame.toString());
        HttpProxy.obtain().get(PlatformContans.Meeting.getMeetingList, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                Log.e("getoutHotels", result);
                try {
                    baseRefreshLayout.setRefreshing(false);
                    bottom_column.setVisibility(View.GONE);
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        JSONArray beanList = data.getJSONArray("beanList");
                        int length = beanList.length();
                        Gson gson = new Gson();
                        List<MeetingModel> tempList = new ArrayList<>();
                        for(int i=0;i<length;i++){
                            JSONObject item=beanList.getJSONObject(i);
                            JSONObject hotel=item.getJSONObject("conferenceRoom");
                            String isAdded=item.getString("isAdded");
                            MeetingModel bean = gson.fromJson(hotel.toString(), MeetingModel.class);
                            if (isAdded.equals("1")) {
                                bean.setAdapot(true);
                            }
                            tempList.add(bean);
                        }
                        if (!isLoadMore) {
                            list.clear();
                            mAdapter.restart();
                        }
                        isLoadMore = false;
                        list.addAll(tempList);
                        mAdapter.notifyDataSetChanged();

                    } else {
                        ToaskUtil.showToast(NewMeetingActivity.this, message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {
                if (bottom_column != null && baseRefreshLayout != null) {
                    bottom_column.setVisibility(View.GONE);
                    baseRefreshLayout.setRefreshing(false);
                }
            }
        });
    }


    @Override
    public void OnItemClick(int position) {
        MeetingModel bean = list.get(position);
        MeetingDetailActivity.startDetail(NewMeetingActivity.this, bean);
    }

    private void showAddressSelectView(View view, @LayoutRes int resource, int type) {

        View pwView = LayoutInflater.from(this).inflate(resource, null);
        int hight = 0;
        if (type == 0) {
            hight = 210;
        } else {
            hight = 300;
        }
        PopupWindow pw = new PopupWindow(pwView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
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
                requestData(name,isAdapot,curProvince, curCity, curDistrict, curHoTelRating);
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
                if("无星级".equals(item)){
                    curHoTelRating=-1;
                }
                page = 1;
                isLoadMore = false;
                requestData(name,isAdapot,curProvince, curCity, curDistrict, curHoTelRating);
                pw.dismiss();

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
                page = 1;
                isLoadMore = false;
                requestData(name,isAdapot,curProvince, curCity, curDistrict, curHoTelRating);
            } else {
                if (data == null) {
                    return;
                }
                boolean reset = data.getBooleanExtra("reset", false);
                if (reset) {
                    page = 1;
                    isLoadMore = false;
                    curProvince = null;
                    curCity = null;
                    curDistrict = null;
                    addressShowText.setText("所在地");
                    Log.d("onActivityResult", "onActivityResult: 重置");
                    requestData(name,isAdapot,curProvince, curCity, curDistrict, curHoTelRating);
                    return;
                }
            }
        }
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



}
