package com.tec.travelagency.home.activity.route;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tec.travelagency.R;
import com.tec.travelagency.home.activity.rent.SaleCarBean;
import com.tec.travelagency.widget.StarLinearLayout;

/**
 * 作者：凌涛 on 2018/11/17 10:47
 * 邮箱：771548229@qq..com
 */
public class SalePathAdapter extends BaseQuickAdapter<SalePathBean, BaseViewHolder> {

    public SalePathAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, SalePathBean platCarBean) {
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
        cartype.setVisibility(View.GONE);
        seaNum.setVisibility(View.GONE);
        if (isadd.equals("1")) {
        } else {
            isadd.setVisibility(View.GONE);
        }
        plat_price.setVisibility(View.GONE);
        saleprice.setText("售价:￥"+platCarBean.getPublishPrice()+"/日");
        if (platCarBean.getIsCanCancel().equals("0")) {
            isCancel.setText("不可退");
        } else {
            isCancel.setText("可退");
        }
        saleprice.setVisibility(View.VISIBLE);
        starLinearLayout.setScore(platCarBean.getCommentData().getScore());
        score.setText(platCarBean.getCommentData().getScore() + "分");
        comment.setText(platCarBean.getCommentData().getNumber() + "条评论");
        name.setText(platCarBean.getName());


        loadIntoUseFitWidth(baseViewHolder.itemView.getContext(), platCarBean.getImage1Url(), carImg);
        //Glide.with(baseViewHolder.itemView.getContext()).load(platCarBean.getImage1Url()).into(carImg);

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
}