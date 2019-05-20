package com.tec.travelagency.home.activity;

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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
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
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.home.adapter.RVAdapter;
import com.tec.travelagency.home.entity.HotelNew2Bean;
import com.tec.travelagency.home.rvItemCallBack.RvItemActionListener;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.widget.CustomPopWindow;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class HotelManagerNewCNMActivity extends BaseActivity implements RVAdapter.ICallTel, RvItemActionListener {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.exit)
    TextView exit;
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

    private List<HotelNew2Bean> list = new ArrayList<>();
    private RVAdapter mAdapter;
    private String mCallNumber;

    @Override
    protected int getContentId() {
        return R.layout.activity_hotel_manager_new;
    }

    @Override
    protected void initView() {
        title.setText("酒店列表");
//        exit.setVisibility(View.VISIBLE);
//        exit.setText("去下单");
        baseRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
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
        });
        HotelNew2Bean banner = new HotelNew2Bean();
        banner.itemType = 1;
        list.add(0, banner);


        baseFragmentRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RVAdapter(this, list);

        mAdapter.setICallTel(this);
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
                //因为LoadMore View  是Adapter的一个Item,显示LoadMore 的时候，Item数量＋1了，导致 mLastVisibalePosition == itemCount-1
                // 判断两次都成立，因此必须加一个判断条件 !mBaseAdapter.isShowLoadMore()
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisiblePosition == itemCount - 1 && isFullScreen) {
                    //最后一个Item了
//                    showLoadMore();
//                    onLoadMore();
                    if (isLoadMore) {
                        return;
                    }
                    bottom_column.setVisibility(View.VISIBLE);
                    page++;
                    isLoadMore = true;

                    requestData(curProvince, curCity, curDistrict, curHoTelRating);
//                    requestData(null, null, null, -1);
                }
            }
        });

        requestData(curProvince, curCity, curDistrict, curHoTelRating);

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

    @OnClick({R.id.back, R.id.exit, R.id.bottom_layout, R.id.locationSelect, R.id.starSelect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.exit:
                startActivity(new Intent(this, HotelSelfOrderActivity.class));
                break;
            case R.id.bottom_layout:
                startActivity(new Intent(this, PlatformHotelListActivity.class));
                break;
            case R.id.locationSelect:
                showAddressSelectView(view, R.layout.pw_address_select, 0);
                break;
            case R.id.starSelect:
                showAddressSelectView(view, R.layout.pw_star_select, 1);
                break;
        }
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
            parame.put("hotelRating", hotelRating);
        }

        HttpProxy.obtain().get(PlatformContans.TravelAgency.getHotelsAdded, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("getoutHotels", result);
                try {
                    baseRefreshLayout.setRefreshing(false);
                    bottom_column.setVisibility(View.GONE);
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONObject page = object.getJSONObject("data");

                        JSONArray beanList = page.getJSONArray("beanList");

                        int length = beanList.length();
                        if (page.getInt("pageNum") != 1 && length <= 0) {
                            ToaskUtil.showToast(HotelManagerNewCNMActivity.this, "没有更多数据了");
                            return;
                        }

                        Gson gson = new Gson();
                        List<HotelNew2Bean> tempList = new ArrayList<>();
                        for (int i = 0; i < length; i++) {
                            JSONObject item = beanList.getJSONObject(i);
                            HotelNew2Bean bean = gson.fromJson(item.toString(), HotelNew2Bean.class);
                            bean.itemType = 0;
                            tempList.add(bean);
                        }
                        if (!isLoadMore) {
                            list.clear();
                            HotelNew2Bean banner = new HotelNew2Bean();
                            banner.itemType = 1;
                            mAdapter.restart();
                            list.add(0, banner);
                        }
                        isLoadMore = false;
                        list.addAll(tempList);
                        mAdapter.notifyDataSetChanged();

                    } else {
                        ToaskUtil.showToast(HotelManagerNewCNMActivity.this, message);
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
    public void call(String number, View view) {
        if (TextUtils.isEmpty(number)) {
            ToaskUtil.showToast(this, "号码为空");
            return;
        }
        if (number.length() != 11) {
            ToaskUtil.showToast(this, "号码格数错误");
            return;
        }
        mCallNumber = number;

        showCallPhoneView(view);
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

        TextView telText = ((TextView) view.findViewById(R.id.tel));
        telText.setText(mCallNumber);
        telText.setOnClickListener(new View.OnClickListener() {
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
        Uri data = Uri.parse("tel:" + mCallNumber);
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    @Override
    public void OnItemClick(int position) {
        HotelNew2Bean bean = list.get(position);
        HotelDetailActivity.startHotelDetail(HotelManagerNewCNMActivity.this, bean);
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
}
