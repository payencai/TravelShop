package com.tec.travelagency;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.gson.Gson;
import com.haohaohu.citypickerview.CityPickerView;
import com.lingtao.citypickerview.style.citylist.utils.CityListLoader;
import com.lingtao.citypickerview.style.citythreelist.ProvinceActivity;
import com.tec.travelagency.common.activity.MainActivity;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void btn(View view) {
        Intent intent = new Intent(this, ProvinceActivity.class);
        startActivityForResult(intent, ProvinceActivity.RESULT_DATA);
    }
}
