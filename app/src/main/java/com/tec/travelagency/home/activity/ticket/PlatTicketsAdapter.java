package com.tec.travelagency.home.activity.ticket;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tec.travelagency.R;
import com.tec.travelagency.home.activity.route.PlatPathBean;
import com.tec.travelagency.widget.StarLinearLayout;

/**
 * 作者：凌涛 on 2018/11/17 14:52
 * 邮箱：771548229@qq..com
 */
public class PlatTicketsAdapter extends BaseQuickAdapter<PlatTicketsBean, BaseViewHolder> {

    public PlatTicketsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, PlatTicketsBean platCarBean) {
        TextView isadd = baseViewHolder.getView(R.id.isPutaway);
        TextView saleprice = baseViewHolder.getView(R.id.saleprice);
        TextView plat_price = baseViewHolder.getView(R.id.plat_price);
        plat_price.setVisibility(View.GONE);
        //plat_price.setText(platCarBean.getScenicSpot().get);
        StarLinearLayout starLinearLayout = baseViewHolder.getView(R.id.star);
        TextView comment = baseViewHolder.getView(R.id.evaluate);
        TextView score = baseViewHolder.getView(R.id.score);
        TextView name = baseViewHolder.getView(R.id.name);
        TextView star=baseViewHolder.getView(R.id.tv_star);
        ImageView carImg = baseViewHolder.getView(R.id.hotelHead);
        star.setText(platCarBean.getScenicSpot().getStarLevel()+"星级");
        saleprice.setText("平台价: ￥"+platCarBean.getScenicSpot().getBottomPrice()+"起");
        starLinearLayout.setScore(platCarBean.getScenicSpot().getCommentData().getScore());
        score.setText(platCarBean.getScenicSpot().getCommentData().getScore() + "分");
        comment.setText(platCarBean.getScenicSpot().getCommentData().getNumber() + "条评论");
        name.setText(platCarBean.getScenicSpot().getName());
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.error(R.drawable.test_img).placeholder(R.drawable.test_img);
        Glide.with(baseViewHolder.itemView.getContext())
                .load(platCarBean.getScenicSpot().getImage1Url())
                .apply(requestOptions)
                .into(carImg);

    }
}
