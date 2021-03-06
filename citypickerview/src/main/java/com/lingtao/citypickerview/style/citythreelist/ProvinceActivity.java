package com.lingtao.citypickerview.style.citythreelist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lingtao.citypickerview.R;
import com.lingtao.citypickerview.style.citylist.bean.CityInfoBean;
import com.lingtao.citypickerview.style.citylist.utils.CityListLoader;
import com.lingtao.citypickerview.widget.RecycleViewDividerForList;

import java.util.List;

import static com.lingtao.citypickerview.style.citylist.utils.CityListLoader.BUNDATA;


public class ProvinceActivity extends Activity {

    private TextView mCityNameTv;

    private RecyclerView mCityRecyclerView;

    public static final int RESULT_DATA = 1001;
    public static final int RESET_DATA = 1002;

    private CityBean provinceBean = new CityBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citylist);
        CityListLoader.getInstance().loadProData(this);
        initView();
        setData();

    }

    private void setData() {

        final List<CityInfoBean> cityList = CityListLoader.getInstance().getProListData();
        if (cityList == null) {
            return;
        }

        CityAdapter cityAdapter = new CityAdapter(ProvinceActivity.this, cityList);
        mCityRecyclerView.setAdapter(cityAdapter);
        cityAdapter.setOnItemClickListener(new CityAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position) {

                provinceBean.setId(cityList.get(position).getId());
                provinceBean.setName(cityList.get(position).getName());

                Intent intent = new Intent(ProvinceActivity.this, CityActivity.class);
                CityInfoBean value = cityList.get(position);
                intent.putExtra(BUNDATA, value);
                startActivityForResult(intent, RESULT_DATA);

            }
        });

    }

    private void initView() {
        mCityNameTv = (TextView) findViewById(R.id.cityname_tv);
        mCityNameTv.setText("选择省份");
        mCityRecyclerView = (RecyclerView) findViewById(R.id.city_recyclerview);

        mCityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCityRecyclerView.addItemDecoration(new RecycleViewDividerForList(this, LinearLayoutManager.HORIZONTAL, true));

        findViewById(R.id.img_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_DATA && data != null) {

            Intent intent = new Intent();
            boolean reset = data.getBooleanExtra("reset", false);
            if (reset) {
                Log.d("onActivityResult", "onActivityResult: 省级里面的返回重置");
                intent.putExtra("reset", true);
                setResult(RESULT_DATA, intent);
                finish();
                return;
            }

            CityBean area = data.getParcelableExtra("area");
            CityBean city = data.getParcelableExtra("city");
            intent.putExtra("province", provinceBean);
            intent.putExtra("city", city);
            intent.putExtra("area", area);
            setResult(RESULT_OK, intent);
            finish();
        }

    }


}
