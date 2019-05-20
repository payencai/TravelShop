package com.tec.travelagency.common.bean;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.squareup.picasso.Picasso;
import com.tec.travelagency.R;
import com.tec.travelagency.common.activity.MineActivity;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseCell;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.utils.ScreenUtils;
import com.tec.travelagency.utils.ToaskUtil;

import java.io.Serializable;

import static com.tec.travelagency.common.activity.MineActivity.REQUEST_CODE;

/**
 * 作者：凌涛 on 2018/8/6 15:35
 * 邮箱：771548229@qq..com
 */
public class RoomPhoto extends RVBaseCell implements Serializable {

    private String imgUrl;
    public String key;
    public boolean canDel = false;
    public boolean isShowNetwork = false;//是否是展示详细信息
    public boolean isEditImgKey = false;//是否是编辑图片的Key


    public RoomPhoto() {
        super(null);
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_photo_rv_layout, parent, false);
        return new RVBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, final int position, final Context context, final RVBaseAdapter adapter) {
        ImageView delImg = holder.getImageView(R.id.del);
        if (canDel) {
            delImg.setVisibility(View.VISIBLE);
            if (isShowNetwork) {
                delImg.setVisibility(View.GONE);
            }
        } else {
            delImg.setVisibility(View.GONE);
        }
        ImageView imageView = (ImageView) holder.getView(R.id.roomImage);
        int screenWidth = ScreenUtils.getScreenWidth(context);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = screenWidth / 3;
        layoutParams.width = screenWidth / 3;
        imageView.setLayoutParams(layoutParams);
//        Picasso.with(context).load(imgUrl).placeholder(R.mipmap.pic_loading).error(R.mipmap.pic_loading_failed).into(imageView);
        if (TextUtils.isEmpty(imgUrl)) {
            Glide.with(context).load(R.mipmap.hotel_photo).into(imageView);
        } else {
            Glide.with(context).load(imgUrl).into(imageView);
        }


        //为空则是最后一项
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (context instanceof MineActivity) {
                    MineActivity activity = (MineActivity) context;
                    if (!canDel) {
                        //限数量的多选(比喻最多9张)
                        ImageSelector.builder()
                                .useCamera(true) // 设置是否使用拍照
                                .setSingle(false)  //设置是否单选
                                .setMaxSelectCount(6-activity.upImgList.size()) // 图片的最大选择数量，小于等于0时，不限数量。
                                .setSelected(activity.selected) // 把已选的图片传入默认选中。
                                .start(activity, REQUEST_CODE); // 打开相册
                    } else {
                        ToaskUtil.showToast(context, "点击第" + position + "张图片");
                    }
                }
            }
        });

    }


}

