package com.tec.travelagency.home.activity.hotel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.lingtao.citypickerview.style.citylist.Toast.ToastUtils;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.bean.ProductDatePrice;
import com.tec.travelagency.home.activity.HotelDetailActivity;
import com.tec.travelagency.home.entity.HotelRoomDetail;
import com.tec.travelagency.home.entity.RoomTypeDesBean;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.DateUtils;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.RandomUtils;
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

public class MoreInfoCalendarActivity extends AppCompatActivity {

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
    String meeting;
    List<ProductDatePrice> mDatePriceList = new ArrayList<>();
    Map<String, Integer> datePositions = new HashMap<>();
    ArrayList<String> dataSet = new ArrayList<>();
    List<PosPage> posCurPage = new ArrayList<>();
    List<PosPage> oneDate=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info_calendar);
        id = getIntent().getStringExtra("id");
        meeting = getIntent().getStringExtra("meeting");
        if (!TextUtils.isEmpty(meeting)) {
            getMeetingDate(meeting);
        }
        if (!TextUtils.isEmpty(id)) {
            {
                getDateList(id);
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
                    ToastUtils.showLongToast(MoreInfoCalendarActivity.this, "请至少选择一个日期");
                    return;
                }
                Intent intent = new Intent();
                //String date = getMaxSelect();
                if (dataSet.size() >= 1)
                    intent.putExtra("date1", dataSet.get(0));
                if (dataSet.size() == 2)
                    intent.putExtra("date2", dataSet.get(1));
                setResult(0, intent);
                finish();
                //Log.e("max", getMaxSelect());
            }
        });

    }

    private void initDate(String date) {
        today = Integer.parseInt(date.substring(8, 10));
        curMonth = Integer.parseInt(date.substring(5, 7));
        curYear = Integer.parseInt(date.substring(0, 4));
        Log.e("date", curYear + "-" + curMonth + "-" + today);
    }

    private void getDateList(String id) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("roomtypeId", id);
        HttpProxy.obtain().get(PlatformContans.Hotel.getDateList, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {

            @Override
            public void OnLtSuccess(String result) {
                Log.e("date", result);
                try {
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
                        ToaskUtil.showToast(MoreInfoCalendarActivity.this, message);
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

    private void getMeetingDate(String id) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("roomtypeId", id);
        HttpProxy.obtain().get(PlatformContans.Hotel.getMeetingDate, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {

            @Override
            public void OnLtSuccess(String result) {
                Log.e("meeting", result);
                try {
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
                        ToaskUtil.showToast(MoreInfoCalendarActivity.this, message);
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
        // Log.e("total", mDatePriceList.get(18).getPrice() + "");
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
            int flag=0;
            @Override
            public void onPageChange(int curpage, GridView gridView) {

            }

            @Override
            public void onDayOfMonthSelected(int year, int month, int day) {
                Toast.makeText(MoreInfoCalendarActivity.this, String.format("%s-%s-%s", year, StringUtils.leftPad(String.valueOf(month), 2, "0"),
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
                        if (true) {

                            if (dataSet.size() == 0) {
                                dataSet.add(datePrice.getDate());
                                view.setBackgroundColor(getResources().getColor(R.color.fbutton_color_sun_flower));
                                datePositions.put(datePrice.getDate(), position);
                                posCurPage.add(new PosPage(position, curpage));
                            } else {
                                if (dataSet.size() == 1) {
                                    if (!dataSet.contains(datePrice.getDate())) {
                                        dataSet.add(datePrice.getDate());
                                        datePositions.put(datePrice.getDate(), position);
                                        posCurPage.add(new PosPage(position, curpage));
                                        view.setBackgroundColor(getResources().getColor(R.color.fbutton_color_sun_flower));
                                        setMiddleColor(curpage, gridViewSparseArray);
                                        Log.e("date", datePositions.get(dataSet.get(1)) + "---" + datePositions.get(dataSet.get(0)));
                                    } else {
                                        dataSet.remove(datePrice.getDate());
                                        datePositions.remove(datePrice.getDate());
                                        posCurPage.clear();
                                        view.setBackgroundColor(Color.WHITE);
                                    }

                                } else if (dataSet.size() == 2) {
                                    clearColor(gridViewSparseArray, curpage);
                                    dataSet.clear();
                                    datePositions.clear();
                                    posCurPage.clear();
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
                    if(gridItemDay==1){
                        flag++;
                        Log.e("datepos",gridItemYear+"-"+gridItemMonth+"-"+gridItemDay+","+view.getId()+","+page);
                        if(curMonth!=gridItemMonth)
                             oneDate.add(new PosPage(flag,view.getId()));
                    }
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

    private void clearColor(SparseArray<GridView> gridViewSparseArray, int page) {
        for (int i = 0; i < 4; i++) {
            GridView gridView = gridViewSparseArray.get(i);
            if (gridView != null) {
                for (int j = 0; j < gridView.getCount(); j++) {
                    View view = gridView.getChildAt(j);
                    view.setBackgroundColor(Color.WHITE);
                }
            }
        }
    }


    private void setMiddleColor(int page, SparseArray<GridView> gridViewSparseArray) {
        String startdate = "";
        String enddate = "";
        int firstdatepage = 0;
        int lastdatepage = 0;
        int firstdateminpos = 0;
        int lastdatemaxpos = 0;
        int lastdateminpos = 0;//最大月的起始pos
        if (!compareTime(dataSet.get(0), dataSet.get(1))) {
            startdate = dataSet.get(0);
            enddate = dataSet.get(1);
        } else {
            startdate = dataSet.get(1);
            enddate = dataSet.get(0);
        }

        if (posCurPage.get(0).getPage() > posCurPage.get(1).getPage()) {
            firstdatepage = posCurPage.get(1).getPage();
            firstdateminpos = posCurPage.get(1).getPos();
            lastdatepage = posCurPage.get(0).getPage();
            lastdatemaxpos = posCurPage.get(0).getPos();
        } else {
            firstdatepage = posCurPage.get(0).getPage();
            firstdateminpos = posCurPage.get(0).getPos();
            lastdatepage = posCurPage.get(1).getPage();
            lastdatemaxpos = posCurPage.get(1).getPos();
        }
        int flag = -100;
        flag = lastdatepage - firstdatepage;
        if (flag == 0) {
            GridView gridView = gridViewSparseArray.get(firstdatepage);
            for (int i = firstdateminpos + 1; i < lastdatemaxpos; i++) {
                View view = gridView.getChildAt(i);
                view.setBackgroundColor(Color.GREEN);
            }
        } else if (flag == 1) {
            lastdateminpos = lastdatemaxpos - Integer.parseInt(enddate.substring(enddate.length() - 2, enddate.length())) + 1;
            GridView gridView1 = gridViewSparseArray.get(firstdatepage);
            GridView gridView2 = gridViewSparseArray.get(lastdatepage);
            for (int i = firstdateminpos + 1; i < gridView1.getCount(); i++) {
                View view = gridView1.getChildAt(i);
                view.setBackgroundColor(Color.GREEN);
            }
            for (int i = lastdateminpos; i < lastdatemaxpos; i++) {
                View view = gridView2.getChildAt(i);
                view.setBackgroundColor(Color.GREEN);
            }

        } else if (flag >= 2) {
            GridView lastgridview=null;

            lastdateminpos = lastdatemaxpos - Integer.parseInt(enddate.substring(enddate.length() - 2, enddate.length())) + 1;
            if(lastdatepage==2){
                lastgridview=gridViewSparseArray.get(2);
            }
            if(lastdatepage==3){
                lastgridview=gridViewSparseArray.get(3);
            }
            //为最后一页着色
            for (int i = lastdateminpos; i <lastdatemaxpos ; i++) {
                View view = lastgridview.getChildAt(i);
                view.setBackgroundColor(Color.GREEN);
            }
            //为首页着色
            for (int i = firstdateminpos + 1; i < gridViewSparseArray.get(firstdatepage).getCount(); i++) {
                View view = gridViewSparseArray.get(firstdatepage).getChildAt(i);
                view.setBackgroundColor(Color.GREEN);
            }//为中间部分上色
            for (int i = firstdatepage+1; i <lastdatepage ; i++) {
                GridView gridView=gridViewSparseArray.get(i);
                int minpos=0;
                if (gridView != null) {
                    for (int k = 0; k <oneDate.size(); k++) {
                        Log.e("datepos",oneDate.size()+"");
                        PosPage posPage=oneDate.get(k);
                        if(i==posPage.getPos()){
                            minpos=posPage.getPage();
                            break;
                        }
                    }
                    for (int j = minpos; j < gridView.getCount(); j++) {
                        View view = gridView.getChildAt(j);
                        view.setBackgroundColor(Color.GREEN);
                    }
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
