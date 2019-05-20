package com.tec.travelagency.home.entity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.common.myviewpager.CommonViewPager;
import com.tec.travelagency.common.myviewpager.ViewPagerHolderCreator;
import com.tec.travelagency.common.recyclerDecoration.HomeOptionDividerItemDecoration;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseCell;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.home.activity.HotelManagerNewActivity;
import com.tec.travelagency.home.activity.route.NewPathActivity;
import com.tec.travelagency.home.activity.meeting.NewMeetingActivity;
import com.tec.travelagency.home.activity.rent.CarActivity;
import com.tec.travelagency.home.activity.ticket.TicketsActivity;
import com.tec.travelagency.home.adapter.ViewImageHolder;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.DisplayUtils;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.widget.VerticalRollingTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：凌涛 on 2018/8/8 15:15
 * 邮箱：771548229@qq..com
 */
public class HomeOption extends RVBaseCell implements VerticalRollingTextView.OnItemClickListener {

    private int bgRes;
    private int tagImgRes;
    private String name;
    private String des;
    private String notice;
    //布局的类型
    private int layoutType = 0;

    public HomeOption() {
        super(null);
    }

    @Override
    public int getItemType() {
        return layoutType;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        if (layoutType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homeoption_rv_layout, parent, false);
        } else if (layoutType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_banner_layout, parent, false);
        } else if (layoutType == 2) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homeoption_rv_layout3, parent, false);
        } else if (layoutType == 3) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homeoption_rv_layout4, parent, false);
        }
        return new RVBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position, final Context context, RVBaseAdapter adapter) {
        if (layoutType == 0) {
//            RelativeLayout parent = (RelativeLayout) holder.getView(R.id.item);
//            parent.setBackgroundResource(bgRes);
//            holder.setImageByRes(R.id.tagImg, tagImgRes);
            holder.setText(R.id.name, name);
//            holder.setText(R.id.des, des);
            holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (name) {
                        case "酒店":
                            context.startActivity(new Intent(context, HotelManagerNewActivity.class));
                            break;

                    }
                }
            });
        } else if (layoutType == 1) {
            CommonViewPager homeBannerVp = (CommonViewPager) holder.getView(R.id.home_banner);
            // 设置数据
            requestViewpagerImg(homeBannerVp);

        } else if (layoutType == 2) {
//            VerticalRollingTextView mVerticalRollingView = (VerticalRollingTextView) holder.getView(R.id.rollingView);
//            List<String> noticeList = new ArrayList<>();
//            if (notice.length() > 15) {
//                int length = notice.length();
//                int space = length / 15;
//
//                for (int i = 0; i <= space; i++) {
//                    int start = i * space;
//                    int end = start + space;
//
//                    if (end >= length) {
//                        end = length;
//                    }
//                    String substring = notice.substring(start, end);
//                    noticeList.add(substring);
//                }
//
//            } else {
//                noticeList.add(notice);
//            }
//
////            mVerticalRollingView.setDataSetAdapter(new DataSetAdapter<String>(Arrays.asList(mStrs)) {
//            mVerticalRollingView.setDataSetAdapter(new DataSetAdapter<String>(noticeList) {
//
//                @Override
//                protected String text(String s) {
//                    return s;
//                }
//            });
//            mVerticalRollingView.setOnItemClickListener(this);
//            mVerticalRollingView.run();
            TextView wisdomTv = holder.getTextView(R.id.wisdom_tv);
            wisdomTv.setText(notice);
            wisdomTv.setSelected(true);
        } else if (layoutType == 3) {
            RecyclerView optionRv = (RecyclerView) holder.getView(R.id.optionRv);

            optionRv.setLayoutManager(new GridLayoutManager(context, 2));
            //设置分隔线
            optionRv.addItemDecoration(new HomeOptionDividerItemDecoration(DisplayUtils.dpToPx(context,10)));

            RVBaseAdapter<GridViewItem> rvBaseAdapter = new RVBaseAdapter<GridViewItem>() {
                @Override
                protected void onViewHolderBound(RVBaseViewHolder holder, int position) {

                }
            };
            List<GridViewItem> list = getGridViewTime(context);
            rvBaseAdapter.setData(list);
            optionRv.setAdapter(rvBaseAdapter);
        }

    }

    String[] mStrs = {
            "君不见，黄河之水天上来，奔流到海不复回",
            "君不见，高堂明镜悲白发，朝如青丝暮成雪",
            "人生得意须尽欢，莫使金樽空对月",
            "天生我材必有用，千金散尽还复来",
            "烹羊宰牛且为乐，会须一饮三百杯",
            "岑夫子，丹丘生，将进酒，杯莫停"
    };

    private void requestViewpagerImg(final CommonViewPager homeBannerVp) {
        HttpProxy.obtain().post(PlatformContans.Banner.getList, null, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("getListBanner", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    if (resultCode == 0) {
                        JSONArray data = object.getJSONArray("data");
                        int length = data.length();
                        List<DataEntry> list = new ArrayList<>();
                        for (int i = 0; i < length; i++) {
                            JSONObject item = data.getJSONObject(i);
                            String imageUrl = item.getString("imageUrl");
                            list.add(new DataEntry(imageUrl));
                        }
                        setViewPagerView(list, homeBannerVp);

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

    private List<DataEntry> mockData() {
        List<DataEntry> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new DataEntry("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1538048142923&di=680731743d13e570fa24046093206f89&imgtype=0&src=http%3A%2F%2Fpic.97uimg.com%2Fback_pic%2F20%2F15%2F11%2F04%2Fab122dacc3cbc8ea2c84f9019f9328a4.jpg"));
        }
        return list;
    }

    public int getBgRes() {
        return bgRes;
    }

    public void setBgRes(int bgRes) {
        this.bgRes = bgRes;
    }

    public int getTagImgRes() {
        return tagImgRes;
    }

    public void setTagImgRes(int tagImgRes) {
        this.tagImgRes = tagImgRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(int layoutType) {
        this.layoutType = layoutType;
    }

    public void setViewPagerView(List<DataEntry> list, CommonViewPager homeBannerVp) {
        homeBannerVp.setPages(list, new ViewPagerHolderCreator<ViewImageHolder>() {
            @Override
            public ViewImageHolder createViewHolder() {
                // 返回ViewPagerHolder
                return new ViewImageHolder();
            }
        });
        //开启轮播
        homeBannerVp.setOpenCarousel(true);
    }

    @Override
    public void onItemClick(VerticalRollingTextView view, int index) {

    }

    public List<GridViewItem> getGridViewTime(Context context) {
        List<GridViewItem> list = new ArrayList<>();
        GridViewItem gridViewItem;
        for (int i = 1; i < 5; i++) {
            gridViewItem = new GridViewItem();
            switch (i) {
                case 1:
                    gridViewItem.setBgRes(R.mipmap.home_bg_huiyishi);
                    gridViewItem.setName("会议室");
                    gridViewItem.setDes(context.getResources().getString(R.string.home_option_dex1));
                    gridViewItem.setTagImgRes(R.mipmap.list_meeting);
                    list.add(gridViewItem);

                    break;
                case 2:
                    gridViewItem.setBgRes(R.mipmap.home_bg_luxian);
                    gridViewItem.setName(context.getResources().getString(R.string.home_option_name4));
                    gridViewItem.setDes(context.getResources().getString(R.string.home_option_dex4));
                    gridViewItem.setTagImgRes(R.mipmap.home_tickets);
                    list.add(gridViewItem);

                    break;
                case 3:
                    gridViewItem.setBgRes(R.mipmap.home_bg_tickets);
                    gridViewItem.setName(context.getResources().getString(R.string.home_option_name3));
                    gridViewItem.setDes(context.getResources().getString(R.string.home_option_dex3));
                    gridViewItem.setTagImgRes(R.mipmap.home_route);
                    list.add(gridViewItem);
                    break;
                case 4:
//                    gridViewItem.setBgRes(R.mipmap.home_bg_huiyishi);
//                    gridViewItem.setName("会议室");
//                    gridViewItem.setDes(context.getResources().getString(R.string.home_option_dex1));
//                    gridViewItem.setTagImgRes(R.mipmap.list_meeting);
//                    list.add(gridViewItem);

                    gridViewItem.setBgRes(R.mipmap.home_bg_carrental);
                    gridViewItem.setName(context.getResources().getString(R.string.home_option_name2));
                    gridViewItem.setDes(context.getResources().getString(R.string.home_option_dex2));
                    gridViewItem.setTagImgRes(R.mipmap.home_carrental);
                    list.add(gridViewItem);
                    break;
            }
        }
        return list;
    }

    public static class GridViewItem extends RVBaseCell implements Serializable {

        private int bgRes;
        private int tagImgRes;
        private String name;
        private String des;

        public GridViewItem() {
            super(null);
        }

        public GridViewItem(int bgRes, int tagImgRes, String name) {
            super(null);
            this.bgRes = bgRes;
            this.tagImgRes = tagImgRes;
            this.name = name;
        }

        @Override
        public int getItemType() {
            return 0;
        }

        @Override
        public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homeoption_rv_item_layout, parent, false);
            return new RVBaseViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RVBaseViewHolder holder, int position, final Context context, RVBaseAdapter adapter) {
            RelativeLayout parent = (RelativeLayout) holder.getView(R.id.item);
            parent.setBackgroundResource(bgRes);
            holder.setText(R.id.name, name);
            holder.getTextView(R.id.isNoData).setVisibility(View.VISIBLE);
            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (name) {
                        case "租车":
                            context.startActivity(new Intent(context, CarActivity.class));
                            break;
                        case "路线":
                            context.startActivity(new Intent(context, NewPathActivity.class));
                            break;
                        case "门票":
                            context.startActivity(new Intent(context, TicketsActivity.class));
                            break;
                        case "会议室":
                            context.startActivity(new Intent(context, NewMeetingActivity.class));
                            //ToaskUtil.showToast(context, "会议室");
                            break;

                    }
                }
            });
        }

        public int getBgRes() {
            return bgRes;
        }

        public void setBgRes(int bgRes) {
            this.bgRes = bgRes;
        }

        public int getTagImgRes() {
            return tagImgRes;
        }

        public void setTagImgRes(int tagImgRes) {
            this.tagImgRes = tagImgRes;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }
}
