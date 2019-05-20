package com.tec.travelagency.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.tec.travelagency.R;
import com.tec.travelagency.bean.ProductDatePrice;
import com.tec.travelagency.home.activity.hotel.PosPage;
import com.tec.travelagency.utils.DateUtils;
import com.tec.travelagency.utils.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * yangqiangyu on 14/11/2016 11:38
 */

public class CommonCalendarView extends FrameLayout implements View.OnClickListener {

    private ViewPager mViewPager;
    private TextView mMonthTv;
    private Context mContext;
    private ImageButton mLeftMonthBtn;
    private ImageButton mRightMonthBtn;

    private SparseArray<GridView> mViewMap = new SparseArray<>();

    private Map<String, List> mYearMonthMap;
    private DatePickerController mController;
    private CalendarAdapter adapter;
    private Date maxDate;
    private Date minDate;

    public Date getMaxDate() {
        return maxDate;
    }

    public Date getMinDate() {
        return minDate;
    }

    public int getCurrentMonthDates() {
        return currentMonthDates;
    }

    public void setCurrentMonthDates(int currentMonthDates) {
        this.currentMonthDates = currentMonthDates;
    }
    Set<String> dateSet=new HashSet<>();//用于存储最大和最小date
    private int currentMonthDates=0;
    private int nextMonthDate=0;

    public int getNextMonthDate() {
        return nextMonthDate;
    }

    public void setNextMonthDate(int nextMonthDate) {
        this.nextMonthDate = nextMonthDate;
    }

    private int currentPage=0;
    private int minPosition=100;
    private int nextPageMinPostion=100;
    public int getNextPageMinPostion() {
        return nextPageMinPostion;
    }

    public void setNextPageMinPostion(int nextPageMinPostion) {
        this.nextPageMinPostion = nextPageMinPostion;
    }

    public void setMinPosition(int minPosition) {
        this.minPosition = minPosition;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public CommonCalendarView(Context context) {
        this(context,null);
    }

    public CommonCalendarView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CommonCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }




    public void init(DatePickerController controller){
        if (controller==null){
             mController = new DatePickerController() {
                 @Override
                 public int getMaxYear() {
                     return DateUtils.getToYear()+1;
                 }

                 @Override
                 public void onPageChange(int curpage,GridView gridView) {

                 }

                 @Override
                 public void onDayOfMonthSelected(int year, int month, int day) {
                     Toast.makeText(mContext, String.format("%s-%s-%s", year, StringUtils.leftPad(String.valueOf(month),2,"0"),
                             StringUtils.leftPad(String.valueOf(day),2,"0")), Toast.LENGTH_SHORT).show();
                 }

                 @Override
                 public void onDayOfMonthAndDataSelected(int year, int month, int day, List obj,View view,SparseArray<GridView> gridViewSparseArray,int curpage) {

                 }

                 @Override
                 public void showOtherFields(Object obj, View view, int gridItemYear, int gridItemMonth, int gridItemDay,int cur) {

                 }

                 @Override
                 public Map<String, List> getDataSource() {
                     return null;
                 }

             };
        }else{
            mController = controller;
        }
        this.mYearMonthMap = mController.getDataSource();
        adapter = new CalendarAdapter(mContext);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageTransformer(true,new DepthPageTransformer());
        mViewPager.setAdapter(adapter);


        if (minDate!=null){
            mMonthTv.setText(String.format("%s年%s月",DateUtils.getYear(minDate), StringUtils.leftPad(String.valueOf(DateUtils.getMonth(minDate)),2,"0")));
        }else{
            mMonthTv.setText(String.format("%s年%s月",DateUtils.getToYear(), StringUtils.leftPad(String.valueOf(DateUtils.getToMonth()),2,"0")));
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage=position;
                Log.e("currentPage",currentPage+"");
                mMonthTv.setText(adapter.getPageTitle(position));
                mController.onPageChange(currentPage,mViewMap.get(currentPage));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_page_calendar_price,this,true);
        this.mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        this.mRightMonthBtn = (ImageButton) view.findViewById(R.id.right_month_btn);
        this.mMonthTv = (TextView) view.findViewById(R.id.month_tv);
        this.mLeftMonthBtn = (ImageButton) view.findViewById(R.id.left_month_btn);
        this.mLeftMonthBtn.setOnClickListener(this);
        this.mRightMonthBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_month_btn:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1,true);
                break;
            case R.id.right_month_btn:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1,true);
                break;
        }
    }


    class CalendarAdapter extends PagerAdapter implements AdapterView.OnItemClickListener {

        protected static final int MONTHS_IN_YEAR = 12;
        private final Calendar calendar = Calendar.getInstance();
        private Integer firstMonth = calendar.get(Calendar.MONTH);
        private LayoutInflater inflater;
        private Integer lastMonth = (calendar.get(Calendar.MONTH) - 1) % MONTHS_IN_YEAR;
        private Integer startYear = calendar.get(Calendar.YEAR);

        public CalendarAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            mContext = context;
            if (maxDate!=null){
                lastMonth = DateUtils.getMonth(maxDate)-1;
            }
            if (minDate!=null){
                startYear = DateUtils.getYear(minDate);
                firstMonth = DateUtils.getMonth(minDate)-1;
            }
        }


        @Override
        public CharSequence getPageTitle(int position) {
            int year = position / MONTHS_IN_YEAR + startYear + ((firstMonth + (position % MONTHS_IN_YEAR)) / MONTHS_IN_YEAR);
            int month = (firstMonth + (position % MONTHS_IN_YEAR)) % MONTHS_IN_YEAR;
            return String.format("%s年%s月",year, StringUtils.leftPad(String.valueOf(month+1),2,"0"));
        }

        @Override
        public int getCount() {
            int maxYear = mController.getMaxYear();
            int minYear = calendar.get(Calendar.YEAR) ;
            if (maxDate!=null){
                maxYear = DateUtils.getYear(maxDate);
            }
            if (minDate!=null){
                minYear = DateUtils.getYear(minDate);
            }

            int itemCount = (maxYear-minYear+1) * MONTHS_IN_YEAR;

            if (firstMonth != -1)
                itemCount -= firstMonth;

            if (lastMonth != -1)
                itemCount -= (MONTHS_IN_YEAR - lastMonth) - 1;
            Log.e("count",itemCount+"");
            return itemCount;
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            Log.e("init",position+",");
            GridView mGridView = mViewMap.get(position);
            if (mGridView ==null){
                mGridView = (GridView) inflater.inflate(R.layout.item_page_month_day, container, false);
                mViewMap.put(position,mGridView);
            }
            int year = position / MONTHS_IN_YEAR +  startYear + ((firstMonth + (position % MONTHS_IN_YEAR)) / MONTHS_IN_YEAR);
            int month = (firstMonth + (position % MONTHS_IN_YEAR)) % MONTHS_IN_YEAR;
            DateBean dateBean = new DateBean(year, month + 1);
            mGridView.setOnItemClickListener(this);

            mGridView.setAdapter(new MyGridAdapter(dateBean));
            container.addView(mGridView);
            return mGridView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MyGridAdapter gridAdapter = (MyGridAdapter) parent.getAdapter();
            int day = (int) gridAdapter.getItem(position);
            if (day == -1) {
                return;
            }
            Log.e("min",minPosition+"-"+nextPageMinPostion);
            DateBean bean = gridAdapter.getDateBean();
            List<ProductDatePrice> list = gridAdapter.getProductDatePriceList();
            if (mController!=null){
                if (list!=null&&!list.isEmpty()){
                    mController.onDayOfMonthAndDataSelected(bean.currentYear,bean.currentMonth,day+1,list,view,mViewMap,currentPage);
                }else{
                    mController.onDayOfMonthSelected(bean.currentYear,bean.currentMonth,day+1);
                }
            }
        }

    }

    List<PosPage> pos=new ArrayList<>();

    public List<PosPage> getPos() {
        return pos;
    }

    class MyGridAdapter extends BaseAdapter {


        private DateBean mDateBean;
        private int days;
        private int dayOfWeeks;
        private List mProductDatePriceList;


        public DateBean getDateBean() {
            return mDateBean;
        }

        public MyGridAdapter(DateBean dateBean) {
            this.mDateBean = dateBean;
            if (mYearMonthMap!=null){
                this.mProductDatePriceList = mYearMonthMap.get(String.format("%s-%s", dateBean.currentYear, StringUtils.leftPad(dateBean.currentMonth + "", 2, "0")));
            }
            GregorianCalendar c = new GregorianCalendar(dateBean.currentYear, dateBean.currentMonth - 1, 0);
            days = DateUtils.getDaysOfMonth(dateBean.currentYear, dateBean.currentMonth); //返回当前月的总天数。
            dayOfWeeks = c.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeeks == 7) {
                dayOfWeeks = 0;
            }
        }

        public List getProductDatePriceList() {
            return mProductDatePriceList;
        }

        @Override
        public int getCount() {
            return days + dayOfWeeks;
        }

        @Override
        public Object getItem(int i) {
            if (i < dayOfWeeks) {
                return -1;
            } else {
                return i - dayOfWeeks;
            }
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            GridViewHolder viewHolder ;

            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_day, viewGroup, false);
                viewHolder = new GridViewHolder();
                viewHolder.mTextView = (TextView) view.findViewById(R.id.day_tv);
                viewHolder.mPriceTv = (TextView) view.findViewById(R.id.price_tv);
                viewHolder.mTvNum= (TextView) view.findViewById(R.id.stock_tv);
                viewHolder.mLineView = view.findViewById(R.id.line_view);
                view.setTag(viewHolder);

            } else {
                viewHolder = (GridViewHolder) view.getTag();
            }
            int item = (int) getItem(i);
            view.setId(i);
            if (item == -1) {
                viewHolder.mTextView.setText("");
                viewHolder.mPriceTv.setText("");
                viewHolder.mTvNum.setText("");
            } else {

                viewHolder.mTextView.setText(String.valueOf(item + 1));
                viewHolder.mPriceTv.setText("");
                viewHolder.mTvNum.setText("");
                if (i%7==0||i%7==6){
                    viewHolder.mTextView.setActivated(true);
                }else{
                    viewHolder.mTextView.setActivated(false);
                }
                if (mProductDatePriceList != null) {
                    viewHolder.mTextView.setEnabled(false);
                    view.setEnabled(false);
                    for (Object obj : mProductDatePriceList) {//用于展示价格等额外的属性
                        if (mController!=null){
                            ProductDatePrice productDatePrice= (ProductDatePrice) obj;
//                            String date=productDatePrice.getDate();
//                            String enddatenum=date.substring(date.length()-2,date.length());
//                            if("01".equals(enddatenum)){
//                                Log.e("end",productDatePrice.getDate());
//                                pos.add(new PosPage(i,currentPage));
//                            }
                            mController.showOtherFields(obj,view,mDateBean.currentYear,mDateBean.currentMonth,item+1,currentPage);
                        }
                    }
                }
            }

            return view;
        }
    }

    static class DateBean {
        private int currentYear;
        private int currentMonth;

        public DateBean(int currentYear, int currentMonth) {
            this.currentYear = currentYear;
            this.currentMonth = currentMonth;
        }
    }

    public static class GridViewHolder {
        public TextView mTextView;
        public TextView mTvNum;
        public View mLineView;
        public TextView mPriceTv;
    }

    class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        @Override
        public void transformPage(View view, float position) {
            Log.e("change","change");
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    public interface DatePickerController {


        int getMaxYear();
        void onPageChange(int curpage,GridView gridView);
        void onDayOfMonthSelected(int year, int month, int day);

        void onDayOfMonthAndDataSelected(int year, int month, int day, List obj, View view,SparseArray<GridView> gridViewSparseArray,int curpage);

        //展示其它属性(用于扩展数据  日期相等时设置显示效果)
        void showOtherFields(Object obj, View view, int gridItemYear, int gridItemMonth, int gridItemDay, int page);

        Map<String,List> getDataSource();
    }

}
