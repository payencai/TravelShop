package com.tec.travelagency.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SynopsisActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content)
    TextView contentText;

    @Override
    protected int getContentId() {
        return R.layout.activity_synopsis;
    }

    @Override
    protected void initView() {

        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        title.setText("简介");
        contentText.setText(content);
    }


    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
