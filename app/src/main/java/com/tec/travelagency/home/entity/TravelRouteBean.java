package com.tec.travelagency.home.entity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tec.travelagency.R;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseCell;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.home.activity.RouteDetailActivity;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：凌涛 on 2018/8/9 14:15
 * 邮箱：771548229@qq..com
 */
public class TravelRouteBean extends RVBaseCell implements Serializable {

    private List<NavigationPath> mPathList;

    private String id;
    private String name;
    private String image;
    private String imageUrl;
    private String travelAgencyId;
    private double price;
    private String isCanCancel;
    private String cityBegin;
    private String routeUsedTime;
    private String reservationSpecification;
    private String trafficSpecification;
    private String ticketSpecification;
    private String hotelSpecification;
    private String isDeleted;
    private String createTime;


    public TravelRouteBean() {
        super(null);
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_travel_route_rv_layout, parent, false);
        return new RVBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position, final Context context, RVBaseAdapter adapter) {

        holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RouteDetailActivity.class);
                intent.putExtra("TravelRouteBean", TravelRouteBean.this);
                context.startActivity(intent);
            }
        });

        holder.setText(R.id.name, name);
        holder.setText(R.id.time, routeUsedTime);
        holder.setText(R.id.cityBegin, cityBegin + "出发");
        holder.setText(R.id.price, "¥" + price + "起");


        // TODO 下面页面已经修改，可以保留
        RecyclerView navigationRv = (RecyclerView) holder.getView(R.id.navigationRv);
        RVBaseAdapter<NavigationPath> pathAdapter = new RVBaseAdapter<NavigationPath>() {
            @Override
            protected void onViewHolderBound(RVBaseViewHolder holder, int position) {

            }

            @Override
            protected void onViewClick(RVBaseViewHolder holder, int position) {
                LinearLayout arrowsLayout = (LinearLayout) holder.getView(R.id.arrowsLayout);
                if (position == mPathList.size() - 1) {
                    arrowsLayout.setVisibility(View.GONE);
                }
            }
        };
        pathAdapter.setData(mPathList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        navigationRv.setLayoutManager(linearLayoutManager);
        navigationRv.setAdapter(pathAdapter);
    }

    public List<NavigationPath> getPathList() {
        return mPathList;
    }

    public void setPathList(List<NavigationPath> pathList) {
        mPathList = pathList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTravelAgencyId() {
        return travelAgencyId;
    }

    public void setTravelAgencyId(String travelAgencyId) {
        this.travelAgencyId = travelAgencyId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIsCanCancel() {
        return isCanCancel;
    }

    public void setIsCanCancel(String isCanCancel) {
        this.isCanCancel = isCanCancel;
    }

    public String getCityBegin() {
        return cityBegin;
    }

    public void setCityBegin(String cityBegin) {
        this.cityBegin = cityBegin;
    }

    public String getRouteUsedTime() {
        return routeUsedTime;
    }

    public void setRouteUsedTime(String routeUsedTime) {
        this.routeUsedTime = routeUsedTime;
    }

    public String getReservationSpecification() {
        return reservationSpecification;
    }

    public void setReservationSpecification(String reservationSpecification) {
        this.reservationSpecification = reservationSpecification;
    }

    public String getTrafficSpecification() {
        return trafficSpecification;
    }

    public void setTrafficSpecification(String trafficSpecification) {
        this.trafficSpecification = trafficSpecification;
    }

    public String getTicketSpecification() {
        return ticketSpecification;
    }

    public void setTicketSpecification(String ticketSpecification) {
        this.ticketSpecification = ticketSpecification;
    }

    public String getHotelSpecification() {
        return hotelSpecification;
    }

    public void setHotelSpecification(String hotelSpecification) {
        this.hotelSpecification = hotelSpecification;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public static class NavigationPath extends RVBaseCell implements Serializable {

        private String imgUrl;
        private String distance;

        public NavigationPath() {
            super(null);
        }

        @Override
        public int getItemType() {
            return 0;
        }


        @Override
        public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_navigation_path_rv_layout, parent, false);
            return new RVBaseViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RVBaseViewHolder holder, int position, Context context, RVBaseAdapter adapter) {


        }
    }
}
