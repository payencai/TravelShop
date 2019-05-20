package com.tec.travelagency.home.activity.hotel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;

import butterknife.BindView;

public class HotelSearchActivity extends BaseActivity {

    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.btn_search)
    TextView tv_search;
    @Override
    protected int getContentId() {
        return R.layout.activity_hotel_search;
    }

    @Override
    protected void initView() {
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HotelSearchActivity.this,SearchResultActivity.class);
                intent.putExtra("name",search.getEditableText().toString());
                startActivity(intent);
            }
        });
    }
}
