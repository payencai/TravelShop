package com.tec.travelagency.home.activity.rent;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.home.entity.test.TestTicket;
import com.tec.travelagency.utils.GlideRoundTransform;
import com.tec.travelagency.widget.StarLinearLayout;
import com.squareup.picasso.Transformation;

/**
 * 作者：凌涛 on 2018/11/2 11:37
 * 邮箱：771548229@qq..com
 */
public class CarPlatAdapter extends BaseQuickAdapter<PlatCarBean, BaseViewHolder> {

    public CarPlatAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, PlatCarBean platCarBean) {
        TextView isadd = baseViewHolder.getView(R.id.isPutaway);
        TextView saleprice = baseViewHolder.getView(R.id.saleprice);
        TextView plat_price = baseViewHolder.getView(R.id.plat_price);
        StarLinearLayout starLinearLayout = baseViewHolder.getView(R.id.star);
        TextView comment = baseViewHolder.getView(R.id.comment);
        TextView score = baseViewHolder.getView(R.id.score);
        TextView name = baseViewHolder.getView(R.id.name);
        TextView isCancel = baseViewHolder.getView(R.id.label1);
        TextView cartype = baseViewHolder.getView(R.id.label2);
        TextView seaNum = baseViewHolder.getView(R.id.label3);
        ImageView carImg = baseViewHolder.getView(R.id.hotelHead);
        saleprice.setText("售价 ￥" + platCarBean.getTravelAgencyPrice() + "/日");
        plat_price.setText("平台价 ￥" + platCarBean.getPlatformPrice() + "/日");
        if (platCarBean.getIsCanCancel().equals("0")) {
            isCancel.setText("不可退");
        } else {
            isCancel.setText("可退");
        }
        if (platCarBean.getIsAdopted().equals("2")) {
            isadd.setVisibility(View.GONE);
        }
        starLinearLayout.setScore(platCarBean.getCommentData().getScore());
        score.setText(platCarBean.getCommentData().getScore() + "分");
        comment.setText(platCarBean.getCommentData().getNumber() + "条评论");
        name.setText(platCarBean.getName());
        seaNum.setText(platCarBean.getSeatNumber() + "座");
        switch (platCarBean.getCarType()) {
            case "1":
                cartype.setText("汽油车");
                break;
            case "2":
                cartype.setText("柴油车");
                break;
            case "3":
                cartype.setText("电车");
                break;
        }
        //laodImageByPicasso(carImg,platCarBean.getImage1Url(),baseViewHolder.itemView.getContext());
        loadIntoUseFitWidth(baseViewHolder.itemView.getContext(), platCarBean.getImage1Url(), carImg);
    }

    public static void loadIntoUseFitWidth(Context context, final String imageUrl, final ImageView imageView) {
        final RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.test_img);
        Glide.with(context)
                .load(imageUrl)
                .apply(requestOptions)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(imageView);

    }

    private void laodImageByPicasso(final ImageView imageView,String url,Context context) {
        Transformation transformation = new Transformation() {
            @Override
            public android.graphics.Bitmap transform(android.graphics.Bitmap source) {

                int targetWidth = imageView.getWidth();
                //Log.e("source.getHeight()="+source.getHeight()+",source.getWidth()="+source.getWidth()+",targetWidth="+targetWidth);
                if (source.getWidth() == 0) {
                    return source;
                }

                //如果图片小于设置的宽度，则返回原图
                if (source.getWidth() < targetWidth) {
                    return source;
                } else {
                    //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
                    double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                    int targetHeight = (int) (targetWidth * aspectRatio);
                    if (targetHeight != 0 && targetWidth != 0) {
                        android.graphics.Bitmap result = android.graphics.Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                        if (result != source) {
                            // Same bitmap is returned if sizes are the same
                            source.recycle();
                        }
                        return result;
                    } else {
                        return source;
                    }
                }

            }

            @Override
            public String key() {
                return "transformation" + " desiredWidth";
            }


        };
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.test_img)
                .error(R.drawable.test_img)
                .transform(transformation)
                .into(imageView);

    }

}
