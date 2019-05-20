package com.tec.travelagency.common.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AgreementActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;

    @Override
    protected int getContentId() {
        return R.layout.activity_agreement;
    }

    @Override
    protected void initView() {
        title.setText("新旅盟协议");
    }


    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
