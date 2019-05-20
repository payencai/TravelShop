package com.tec.travelagency.home.activity.ticket;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.haohaohu.citypickerview.CityPickerView;
import com.lingtao.citypickerview.style.citythreelist.CityBean;
import com.lingtao.citypickerview.style.citythreelist.ProvinceActivity;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseFragment;
import com.tec.travelagency.home.activity.route.PathDetailActivity;
import com.tec.travelagency.home.activity.route.PlatPathAdapter;
import com.tec.travelagency.home.activity.route.PlatPathBean;
import com.tec.travelagency.home.activity.route.PlatPathFragment;
import com.tec.travelagency.home.adapter.ticket.TicketPlatAdapter;
import com.tec.travelagency.home.entity.test.TestTicket;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;
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

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class TicketPlatFragment extends BaseFragment {
    @BindView(R.id.selectorLayout)
    LinearLayout selectorLayout;
    @BindView(R.id.locationSelect)
    LinearLayout locationSelect;
    @BindView(R.id.starSelect)
    LinearLayout starSelect;
    @BindView(R.id.all)
    LinearLayout all;
    @BindView(R.id.addressShowText)
    TextView addressShowText;
    @BindView(R.id.starGradle)
    TextView starGradle;
    @BindView(R.id.tv_all)
    TextView tv_adapot;
    @BindView(R.id.arrowImg1)
    ImageView arrowImg1;
    @BindView(R.id.arrowImg2)
    ImageView arrowImg2;
    @BindView(R.id.arrowImg3)
    ImageView arrowImg3;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.sf_ticket1)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.rv_ticket1)
    RecyclerView rv_ticket1;
    PlatTicketsAdapter mTicketPlatAdapter;

    private String curProvince = null;
    private String curCity = null;
    private String curDistrict = null;
    private int curHoTelRating = -1;
    private String name = null;
    private String isAdapot = null;
    private int page = 1;
    private boolean isLoadMore = false;

    public static TicketPlatFragment newInstance(String from) {
        Bundle args = new Bundle();
        args.putString("from", from);
        TicketPlatFragment fragment = new TicketPlatFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getContentId() {
        return R.layout.fragment_ticket_plat;
    }

    @Override
    protected void initView() {
        initRecycle();
        locationSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProvinceActivity.class);
                startActivityForResult(intent, ProvinceActivity.RESULT_DATA);
            }
        });
        starSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddressSelectView(v, R.layout.pw_star_select, 1);
            }
        });
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdapotSelectView(v, R.layout.pw_star_select, 1);
            }
        });
    }

    private View getHeader(List<String> images) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.banner_head, null);
        Banner banner = view.findViewById(R.id.banner);
        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                //此处可以自行选择，我直接用的Picasso
                Glide.with(getContext()).load((String) path).into(imageView);
            }
        });
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(2000);//设置轮播时间
        banner.setImages(images);//设置图片源
        banner.start();
        return view;
    }

    private void initRecycle() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name = s.toString();
                page = 1;
                requestData(name, isAdapot, curProvince, curCity, curDistrict);
            }
        });
        rv_ticket1.setLayoutManager(new LinearLayoutManager(getContext()));
        mTicketPlatAdapter = new PlatTicketsAdapter(R.layout.item_ticket);

        mTicketPlatAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(getContext(), PlatTicketsDetailActivity.class);
                intent.putExtra("id", mTicketPlatAdapter.getData().get(i).getScenicSpot().getId());
                startActivity(intent);
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                curProvince = null;
                curCity = null;
                curDistrict = null;
                name = null;
                isAdapot = null;
                page = 1;
                isLoadMore = false;
                tv_adapot.setText("全部");
                addressShowText.setText("所在地");
                requestData(name, isAdapot, curProvince, curCity, curDistrict);
            }
        });
        mTicketPlatAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isLoadMore = true;
                page++;
            }
        }, rv_ticket1);

        getBanner();

    }

    private List<TestTicket> getData() {
        List<TestTicket> testTickets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            testTickets.add(new TestTicket());
        }
        return testTickets;
    }

    private void showAdapotSelectView(View view, @LayoutRes int resource, int type) {

        View pwView = LayoutInflater.from(getContext()).inflate(resource, null);
        int hight = 0;
        if (type == 0) {
            hight = 210;
        } else {
            hight = 300;
        }
        PopupWindow pw = new PopupWindow(pwView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                if (!TextUtils.isEmpty(curDistrict))
                    addressShowText.setText(curDistrict);
                else if (TextUtils.isEmpty(curDistrict) && !TextUtils.isEmpty(curCity)) {
                    addressShowText.setText(curCity);
                } else if (TextUtils.isEmpty(curDistrict) && TextUtils.isEmpty(curCity) && !TextUtils.isEmpty(curProvince)) {
                    addressShowText.setText(curProvince);
                }
                page = 1;
                isLoadMore = false;
                requestData(name, isAdapot, curProvince, curCity, curDistrict);
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
                    requestData(name, isAdapot, curProvince, curCity, curDistrict);
                    return;
                }
            }
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, starList);
        starListView.setAdapter(adapter);
        starListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getAdapter().getItem(position);
                if ("已上架".equals(item)) ;
                    isAdapot = "1";
                if ("未上架".equals(item))
                    isAdapot = "2";
                if ("全部".equals(item))
                    isAdapot = null;
                tv_adapot.setText(item);
                page = 1;
                isLoadMore = false;
                requestData(name, isAdapot, curProvince, curCity, curDistrict);
                pw.dismiss();

            }
        });

    }

    private void showAddressSelectView(View view, @LayoutRes int resource, int type) {

        View pwView = LayoutInflater.from(getContext()).inflate(resource, null);
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
                //requestData(name,isAdapot,curProvince, curCity, curDistrict, curHoTelRating);
            }
        });

    }

    private void handleStarSelectView(View view, final PopupWindow pw) {
        ListView starListView = (ListView) view.findViewById(R.id.starList);
        List<String> starList = getStarList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, starList);
        starListView.setAdapter(adapter);

        starListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getAdapter().getItem(position);
                starGradle.setText(item);
                curHoTelRating = position;

                page = 1;
                isLoadMore = false;
                //requestData(name,isAdapot,curProvince, curCity, curDistrict, curHoTelRating);
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

    private void getBanner() {
        Map<String, Object> params = new HashMap<>();
        params.put("type", "4");
        HttpProxy.obtain().post(PlatformContans.Banner.getList, params, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                Log.e("type4", result);
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
                        mTicketPlatAdapter.addHeaderView(getHeader(list));
                        requestData(name, isAdapot, curProvince, curCity, curDistrict);

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

    private void requestData(String name, String isAdapot, String province, String city, String district) {
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
        // Log.e("getoutHotels", parame.toString());
        HttpProxy.obtain().get(PlatformContans.Ticket.getPlatTicketsList, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }

                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        JSONArray beanList = data.getJSONArray("beanList");
                        int num = data.getInt("tr");
                        int length = beanList.length();
                        Gson gson = new Gson();
                        List<PlatTicketsBean> tempList = new ArrayList<>();
                        for (int i = 0; i < length; i++) {
                            JSONObject item = beanList.getJSONObject(i);
                            Log.e("requestData", item.toString());
                            PlatTicketsBean bean = gson.fromJson(item.toString(), PlatTicketsBean.class);
                            tempList.add(bean);
                        }
                        if (isLoadMore) {
                            mTicketPlatAdapter.addData(tempList);
                            mTicketPlatAdapter.loadMoreComplete();
                            if (tempList.size() == 0) {
                                mTicketPlatAdapter.loadMoreEnd(true);
                                isLoadMore = false;
                            }
                        } else {
                           // Log.e("requestData","wwww");
                            mTicketPlatAdapter.setNewData(tempList);
                            rv_ticket1.setAdapter(mTicketPlatAdapter);
                            if (num <= 10) {
                                mTicketPlatAdapter.loadMoreEnd(true);
                            }
                        }

                    } else {
                        ToaskUtil.showToast(getContext(), message);
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


}

