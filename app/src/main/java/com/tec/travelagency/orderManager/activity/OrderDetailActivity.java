package com.tec.travelagency.orderManager.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.reasonLayout)
    LinearLayout reasonLayout;
    @BindView(R.id.reason_text)
    TextView reasonText;

    @Override
    protected int getContentId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initView() {
        title.setText("订单详情");
    }


    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
