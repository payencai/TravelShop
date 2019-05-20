package com.tec.travelagency.home.activity.ticket;

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
import com.tec.travelagency.home.activity.route.SalePathBean;
import com.tec.travelagency.widget.StarLinearLayout;

/**
 * 作者：凌涛 on 2018/11/17 15:21
 * 邮箱：771548229@qq..com
 */
public class SaleTicketsAdapter extends BaseQuickAdapter<SaleTicketsBean, BaseViewHolder> {

    public SaleTicketsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, SaleTicketsBean platCarBean) {
        TextView isadd = baseViewHolder.getView(R.id.isPutaway);
        TextView saleprice = baseViewHolder.getView(R.id.saleprice);
        TextView plat_price = baseViewHolder.getView(R.id.plat_price);
        StarLinearLayout starLinearLayout = baseViewHolder.getView(R.id.star);
        TextView comment = baseViewHolder.getView(R.id.evaluate);
        TextView score = baseViewHolder.getView(R.id.score);
        TextView name = baseViewHolder.getView(R.id.name);
        TextView star=baseViewHolder.getView(R.id.tv_star);
        ImageView carImg = baseViewHolder.getView(R.id.hotelHead);
        isadd.setVisibility(View.GONE);
        plat_price.setVisibility(View.GONE);
        star.setText(platCarBean.getStarLevel()+"星级");
        saleprice.setText("售价: ￥"+platCarBean.getBottomPrice()+"起");
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
