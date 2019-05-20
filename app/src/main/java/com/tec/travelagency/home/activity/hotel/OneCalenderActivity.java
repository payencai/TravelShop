package com.tec.travelagency.home.activity.hotel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.lingtao.citypickerview.style.citylist.Toast.ToastUtils;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.bean.ProductDatePrice;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.DateUtils;
import com.tec.travelagency.utils.StringUtils;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.view.CommonCalendarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OneCalenderActivity extends AppCompatActivity {

    private CommonCalendarView calendarView;
    private Map<String, List> mYearMonthMap = new HashMap<>();
    private int curMonth = 11;
    private int curYear = 2018;
    private int totalDate = 30;
    private int today = 15;
    private int curMonthDate = totalDate - today + 1;
    private int nextMonthDate = totalDate - curMonthDate;
    private int curPageMinPostion = 100;
    private int nextPageMinPostion = 100;
    private Set<ProductDatePrice> select = new HashSet<>();
    boolean isFirst = true;
    String id;
    String route;
    String ticket;
    List<ProductDatePrice> mDatePriceList = new ArrayList<>();
    Map<String, Integer> datePositions = new HashMap<>();
    Map<Integer, Integer> posCurpage = new HashMap<>();
    ArrayList<String> dataSet = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_calender);
        id = getIntent().getStringExtra("id");
        route = getIntent().getStringExtra("route");
        // Log.e("route",route);
        ticket = getIntent().getStringExtra("ticket");
        if (!TextUtils.isEmpty(id)) {
            {
                getDateList(id);
            }
        }
        if (!TextUtils.isEmpty(route)) {
            {
                getPathDate(route);
            }
        }
        if (!TextUtils.isEmpty(ticket)) {
            {
                getTickect(ticket);
            }
        }
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataSet.size() == 0) {
                    ToastUtils.showLongToast(OneCalenderActivity.this, "请至少选择一个日期");
                    return;
                }
                Intent intent = new Intent();
                //String date = getMaxSelect();
                if (dataSet.size() == 1) {
                    intent.putExtra("date", dataSet.get(0));
                    intent.putExtra("num", getProductDatePrice(dataSet.get(0)));
                }
                setResult(0, intent);
                finish();
                // Log.e("max", getMaxSelect());
            }
        });

    }

    private ProductDatePrice getProductDatePrice(String date) {
        for (int i = 0; i < mDatePriceList.size(); i++) {
            ProductDatePrice productDatePrice = mDatePriceList.get(i);
            if (TextUtils.equals(productDatePrice.getDate(), date)) {
                return productDatePrice;
            }
        }
        return null;
    }

    private void initDate(String date) {
        today = Integer.parseInt(date.substring(8, 10));
        curMonth = Integer.parseInt(date.substring(5, 7));
        curYear = Integer.parseInt(date.substring(0, 4));
        Log.e("date", curYear + "-" + curMonth + "-" + today);
    }

    private void getTickect(String id) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("ticketId", id);
        HttpProxy.obtain().get(PlatformContans.Ticket.getTicketDate, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {

            @Override
            public void OnLtSuccess(String result) {
                try {
                    Log.e("date", result);
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONArray data = object.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            ProductDatePrice productDatePrice = new ProductDatePrice();
                            productDatePrice.setEmptyNum(item.getInt("emptyNum"));
                            productDatePrice.setDate(item.getString("date").substring(0, 10));
                            productDatePrice.setPrice(item.getDouble("price"));
                            mDatePriceList.add(productDatePrice);
                        }
                        setData(mDatePriceList);
                    } else {
                        ToaskUtil.showToast(OneCalenderActivity.this, message);
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

    private void getPathDate(String id) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("routeId", id);
        HttpProxy.obtain().get(PlatformContans.Path.getPathDate, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {

            @Override
            public void OnLtSuccess(String result) {
                try {
                    Log.e("route", result);
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONArray data = object.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            ProductDatePrice productDatePrice = new ProductDatePrice();
                            productDatePrice.setEmptyNum(item.getInt("emptyNum"));
                            productDatePrice.setDate(item.getString("date").substring(0, 10));
                            productDatePrice.setPrice(item.getDouble("price"));
                            mDatePriceList.add(productDatePrice);
                        }
                        if (mDatePriceList.size() > 0)
                            setData(mDatePriceList);
                    } else {
                        ToaskUtil.showToast(OneCalenderActivity.this, message);
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

    private void getDateList(final String id) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("carRentalId", id);
        HttpProxy.obtain().get(PlatformContans.Hotel.getCardDate, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {

            @Override
            public void OnLtSuccess(String result) {
                try {
                    Log.e("getDateList", id + "");
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONArray data = object.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            ProductDatePrice productDatePrice = new ProductDatePrice();
                            productDatePrice.setEmptyNum(item.getInt("emptyNum"));
                            productDatePrice.setDate(item.getString("date").substring(0, 10));
                            productDatePrice.setPrice(item.getDouble("price"));
                            mDatePriceList.add(productDatePrice);
                        }
                        if (mDatePriceList.size() > 0)
                            setData(mDatePriceList);
                    } else {
                        ToaskUtil.showToast(OneCalenderActivity.this, message);
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


    private void setData(final List<ProductDatePrice> mDatePriceList) {
        initDate(mDatePriceList.get(0).getDate());
        totalDate = mDatePriceList.size();
        //Log.e("total", mDatePriceList.get(18).getPrice() + "");
        for (ProductDatePrice productDatePrice : mDatePriceList) {//把价格数据改为同一个月的list 在一个key value里，减少渲染界面时循环判断数量
            String yearMonth = TextUtils.substring(productDatePrice.getDate(), 0, TextUtils.lastIndexOf(productDatePrice.getDate(), '-'));
            List list = mYearMonthMap.get(yearMonth);
            if (list == null) {
                list = new ArrayList();
                list.add(productDatePrice);
                mYearMonthMap.put(yearMonth, list);
            } else {
                list.add(productDatePrice);
            }
        }

        this.calendarView = (CommonCalendarView) findViewById(R.id.calendarView);
        this.calendarView.setMinDate(DateUtils.stringtoDate(mDatePriceList.get(0).getDate(), "yyyy-MM-dd"));
        String maxDate = "";
        if (curMonth >= 10) {
            String month = "0" + (curMonth - 9);

            maxDate = (curYear + 1) + "-" + month + "-" + today;
        } else {
            maxDate = curYear + "-" + (curMonth + 3) + "-" + today;
        }
        this.calendarView.setMaxDate(DateUtils.stringtoDate(maxDate, "yyyy-MM-dd"));
        this.calendarView.init(new CommonCalendarView.DatePickerController() {
            @Override
            public int getMaxYear() {
                return curYear;
            }

            @Override
            public void onPageChange(int curpage,GridView gridView) {
                if(posCurpage.size()>0){
                    int page= posCurpage.get(datePositions.get(dataSet.get(0)));
                    if(page==curpage){
                       gridView.getChildAt(datePositions.get(dataSet.get(0))).setBackgroundColor(getResources().getColor(R.color.fbutton_color_sun_flower));
                    }
                }
            }

            @Override
            public void onDayOfMonthSelected(int year, int month, int day) {
                Toast.makeText(OneCalenderActivity.this, String.format("%s-%s-%s", year, StringUtils.leftPad(String.valueOf(month), 2, "0"),
                        StringUtils.leftPad(String.valueOf(day), 2, "0")), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDayOfMonthAndDataSelected(int year, int month, int day, List obj, View view, SparseArray<GridView> gridViewSparseArray, int curpage) {
                Log.e("view", view.getId() + "");
                if (obj == null) {
                    return;
                }
                String priceDate = String.format("%s-%s-%s", year,
                        StringUtils.leftPad(month + "", 2, "0"), StringUtils.leftPad(String.valueOf(day), 2, "0"));
                for (int i = 0; i < obj.size(); i++) {
                    ProductDatePrice datePrice = (ProductDatePrice) obj.get(i);
                    if (datePrice == null) {
                        continue;
                    } else {
                        datePrice.setId(view.getId());
                    }
                    if (TextUtils.equals(datePrice.getDate(), priceDate)) {
                        int position = view.getId();
                        GridView gridView = gridViewSparseArray.get(curpage);
                        if (curpage == 0) {
                            GridView gridView1 = gridViewSparseArray.get(1);
                            GridView gridView2 = gridViewSparseArray.get(2);
                            GridView gridView3 = gridViewSparseArray.get(3);
                            if (dataSet.size() == 0) {
                                dataSet.add(datePrice.getDate());
                                view.setBackgroundColor(getResources().getColor(R.color.fbutton_color_sun_flower));
                                datePositions.put(datePrice.getDate(), position);
                                posCurpage.put(position,curpage);
                            } else {
                                if (dataSet.size() == 1) {
                                    if (!dataSet.contains(datePrice.getDate())) {
                                        String date = dataSet.get(0);
                                        int pos = datePositions.get(date);
                                        dataSet.remove(date);
                                        posCurpage.remove(pos);
                                        datePositions.remove(date);
                                        if (gridView1.getChildAt(pos) != null)
                                            gridView1.getChildAt(pos).setBackgroundColor(Color.WHITE);
                                        if (gridView2 != null) {
                                            if (gridView2.getChildAt(pos) != null)
                                                gridView2.getChildAt(pos).setBackgroundColor(Color.WHITE);
                                        }
                                        if (gridView3 != null) {
                                            if (gridView3.getChildAt(pos) != null)
                                                gridView3.getChildAt(pos).setBackgroundColor(Color.WHITE);
                                        }
                                        gridView.getChildAt(pos).setBackgroundColor(Color.WHITE);
                                        dataSet.add(datePrice.getDate());
                                        datePositions.put(datePrice.getDate(), position);
                                        posCurpage.put(position,curpage);
                                        view.setBackgroundColor(getResources().getColor(R.color.fbutton_color_sun_flower));
                                    }
                                }
                            }

                        } else if (curpage == 1) {
                            GridView gridView0 = gridViewSparseArray.get(0);
                            GridView gridView2 = gridViewSparseArray.get(2);
                            GridView gridView3 = gridViewSparseArray.get(3);
                            if (dataSet.size() == 0) {
                                dataSet.add(datePrice.getDate());
                                view.setBackgroundColor(getResources().getColor(R.color.fbutton_color_sun_flower));
                                datePositions.put(datePrice.getDate(), position);
                                posCurpage.put(position,curpage);
                            } else {
                                if (dataSet.size() == 1) {
                                    if (!dataSet.contains(datePrice.getDate())) {
                                        String date = dataSet.get(0);
                                        int pos = datePositions.get(date);
                                        dataSet.remove(date);
                                        posCurpage.remove(pos);
                                        datePositions.remove(date);
                                        if (gridView0.getChildAt(pos) != null)
                                            gridView0.getChildAt(pos).setBackgroundColor(Color.WHITE);
                                        if (gridView2.getChildAt(pos) != null)
                                            gridView2.getChildAt(pos).setBackgroundColor(Color.WHITE);
                                        if (gridView3 != null) {
                                            if (gridView3.getChildAt(pos) != null)
                                                gridView3.getChildAt(pos).setBackgroundColor(Color.WHITE);
                                        }
                                        gridView.getChildAt(pos).setBackgroundColor(Color.WHITE);
                                        dataSet.add(datePrice.getDate());
                                        datePositions.put(datePrice.getDate(), position);
                                        posCurpage.put(position,curpage);
                                        view.setBackgroundColor(getResources().getColor(R.color.fbutton_color_sun_flower));
                                    }
                                }
                            }
                        } else if (curpage == 2) {
                            GridView gridView0 = gridViewSparseArray.get(0);
                            GridView gridView1 = gridViewSparseArray.get(1);
                            GridView gridView3 = gridViewSparseArray.get(3);
                            if (dataSet.size() == 0) {
                                dataSet.add(datePrice.getDate());
                                view.setBackgroundColor(getResources().getColor(R.color.fbutton_color_sun_flower));
                                datePositions.put(datePrice.getDate(), position);
                                posCurpage.put(position,curpage);
                            } else {
                                if (dataSet.size() == 1) {
                                    if (!dataSet.contains(datePrice.getDate())) {
                                        String date = dataSet.get(0);
                                        int pos = datePositions.get(date);
                                        dataSet.remove(date);
                                        datePositions.remove(date);
                                        posCurpage.remove(pos);
                                        if (gridView1.getChildAt(pos) != null)
                                            gridView1.getChildAt(pos).setBackgroundColor(Color.WHITE);
                                        if (gridView0.getChildAt(pos) != null)
                                            gridView0.getChildAt(pos).setBackgroundColor(Color.WHITE);
                                        if (gridView3.getChildAt(pos) != null)
                                            gridView3.getChildAt(pos).setBackgroundColor(Color.WHITE);
                                        gridView.getChildAt(pos).setBackgroundColor(Color.WHITE);
                                        dataSet.add(datePrice.getDate());
                                        datePositions.put(datePrice.getDate(), position);
                                        posCurpage.put(position,curpage);
                                        view.setBackgroundColor(getResources().getColor(R.color.fbutton_color_sun_flower));
                                    }
                                }
                            }
                        } else {
                            GridView gridView0 = gridViewSparseArray.get(0);
                            GridView gridView1 = gridViewSparseArray.get(1);
                            GridView gridView2 = gridViewSparseArray.get(2);
                            if (dataSet.size() == 0) {
                                dataSet.add(datePrice.getDate());
                                view.setBackgroundColor(getResources().getColor(R.color.fbutton_color_sun_flower));
                                datePositions.put(datePrice.getDate(), position);
                                posCurpage.put(position,curpage);
                            } else {
                                if (dataSet.size() == 1) {
                                    if (!dataSet.contains(datePrice.getDate())) {
                                        String date = dataSet.get(0);
                                        int pos = datePositions.get(date);
                                        dataSet.remove(date);
                                        datePositions.remove(date);
                                        posCurpage.remove(pos);
                                        if (gridView1.getChildAt(pos) != null)
                                            gridView1.getChildAt(pos).setBackgroundColor(Color.WHITE);
                                        if (gridView0.getChildAt(pos) != null)
                                            gridView0.getChildAt(pos).setBackgroundColor(Color.WHITE);
                                        if (gridView2.getChildAt(pos) != null)
                                            gridView2.getChildAt(pos).setBackgroundColor(Color.WHITE);
                                        gridView.getChildAt(pos).setBackgroundColor(Color.WHITE);
                                        dataSet.add(datePrice.getDate());
                                        datePositions.put(datePrice.getDate(), position);
                                        posCurpage.put(position,curpage);
                                        view.setBackgroundColor(getResources().getColor(R.color.fbutton_color_sun_flower));
                                    }
                                }
                            }
                        }

                    }
                }

            }

            @Override
            public void showOtherFields(Object obj, View view, int gridItemYear, int gridItemMonth, int gridItemDay, int page) {
                //当你设置了数据源之后，界面渲染会循环调用showOtherFields方法，在该方法中实现同一日期设置界面显示效果。
                ProductDatePrice productDatePrice = (ProductDatePrice) obj;
                if (TextUtils.equals(productDatePrice.getDate(), String.format("%s-%s-%s", gridItemYear,
                        StringUtils.leftPad(gridItemMonth + "", 2, "0"), StringUtils.leftPad(String.valueOf(gridItemDay), 2, "0")))) {
                    CommonCalendarView.GridViewHolder viewHolder = (CommonCalendarView.GridViewHolder) view.getTag();
                    viewHolder.mTvNum.setText(productDatePrice.getEmptyNum() + "间");
                    viewHolder.mTvNum.setVisibility(View.VISIBLE);
                    viewHolder.mPriceTv.setText(String.format("¥ %s", productDatePrice.getPrice()));
                    view.setEnabled(true);
                    Log.e("bbb", view.getId() + "");
                    int id = view.getId();
                    if (isFirst) {
                        isFirst = false;
                        curPageMinPostion = id;
                    } else {
                        if (id < nextPageMinPostion) {
                            nextPageMinPostion = id;
                        }
                    }
                    calendarView.setCurrentMonthDates(curMonthDate);
                    calendarView.setNextMonthDate(nextMonthDate);
                    calendarView.setMinPosition(curPageMinPostion);
                    calendarView.setNextPageMinPostion(nextPageMinPostion);
                    viewHolder.mTextView.setEnabled(true);

                }
            }

            @Override
            public Map<String, List> getDataSource() {
                return mYearMonthMap;
            }
        });
    }

    private void clearColor(GridView gridView, GridView another, int page) {
        String startdate = "";
        String enddate = "";

        if (!compareTime(dataSet.get(0), dataSet.get(1))) {
            startdate = dataSet.get(0);
            enddate = dataSet.get(1);
        } else {
            startdate = dataSet.get(1);
            enddate = dataSet.get(0);
        }
        int minpos = datePositions.get(startdate);
        int maxpos = datePositions.get(enddate);
        if (startdate.substring(5, 7).equals(enddate.substring(5, 7))) {

            for (int j = minpos; j <= maxpos; j++) {
                View child = another.getChildAt(j);
                child.setBackgroundColor(Color.WHITE);
            }
            for (int j = minpos; j <= maxpos; j++) {
                View child = gridView.getChildAt(j);
                child.setBackgroundColor(Color.WHITE);
            }

        } else {
            if (page == 1) {
                for (int i = minpos; i <= curPageMinPostion + curMonthDate - 1; i++) {
                    View child = another.getChildAt(i);
                    if (child != null)
                        child.setBackgroundColor(Color.WHITE);
                }
                for (int i = nextPageMinPostion; i <= maxpos; i++) {
                    View child = gridView.getChildAt(i);
                    if (child != null)
                        child.setBackgroundColor(Color.WHITE);
                }
            } else {
                for (int i = minpos; i <= curPageMinPostion + curMonthDate - 1; i++) {
                    View child = gridView.getChildAt(i);
                    if (child != null)
                        child.setBackgroundColor(Color.WHITE);
                }
                for (int i = nextPageMinPostion; i <= maxpos; i++) {
                    View child = another.getChildAt(i);
                    if (child != null)
                        child.setBackgroundColor(Color.WHITE);
                }
            }


        }
    }

    private void setMiddleColor(GridView gridView, GridView another, int page) {
        String startdate = "";
        String enddate = "";

        if (!compareTime(dataSet.get(0), dataSet.get(1))) {
            startdate = dataSet.get(0);
            enddate = dataSet.get(1);
        } else {
            startdate = dataSet.get(1);
            enddate = dataSet.get(0);
        }

        int minpos = datePositions.get(startdate) + 1;
        int maxpos = datePositions.get(enddate);
        if (startdate.substring(5, 7).equals(enddate.substring(5, 7))) {
            for (int j = minpos; j < maxpos; j++) {
                View child = gridView.getChildAt(j);
                child.setBackgroundColor(Color.GREEN);
            }
        } else {
            if (page == 1) {
                for (int i = minpos; i < curPageMinPostion + curMonthDate - 1; i++) {
                    View child = another.getChildAt(i);
                    if (child != null)
                        child.setBackgroundColor(Color.GREEN);
                }
                for (int i = nextPageMinPostion; i < maxpos; i++) {
                    View child = gridView.getChildAt(i);
                    if (child != null)
                        child.setBackgroundColor(Color.GREEN);
                }
            } else {
                for (int i = minpos; i < curPageMinPostion + curMonthDate - 1; i++) {
                    View child = gridView.getChildAt(i);
                    if (child != null)
                        child.setBackgroundColor(Color.GREEN);
                }
                for (int i = nextPageMinPostion; i < maxpos; i++) {
                    View child = another.getChildAt(i);
                    if (child != null)
                        child.setBackgroundColor(Color.GREEN);
                }
            }


        }

    }

    private String getMaxSelect() {
        String maxdate = "1990-01-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date bt = null;
        Date et = null;
        try {
            for (Iterator it2 = select.iterator(); it2.hasNext(); ) {
                bt = sdf.parse(maxdate);
                ProductDatePrice productDatePrice = (ProductDatePrice) it2.next();
                et = sdf.parse(productDatePrice.getDate());
                if (bt.before(et)) {
                    //表示bt小于et
                    maxdate = productDatePrice.getDate();
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return maxdate;
    }

    private boolean compareTime(String curtime, String maxdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date bt = null;
        try {
            bt = sdf.parse(curtime);
            Date et = sdf.parse(maxdate);
            if (bt.before(et)) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return true;

    }

    private void removeMax(String curdate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date bt = null;
        Date et = null;
        try {
            bt = sdf.parse(curdate);
            for (Iterator it2 = select.iterator(); it2.hasNext(); ) {
                ProductDatePrice productDatePrice = (ProductDatePrice) it2.next();
                et = sdf.parse(productDatePrice.getDate());
                if (bt.before(et)) {
                    it2.remove();
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
