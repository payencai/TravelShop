package com.tec.travelagency.home.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseFragment;
import com.tec.travelagency.common.activity.MineActivity;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.home.activity.NotifyActivity;
import com.tec.travelagency.home.entity.HomeOption;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：凌涛 on 2018/8/8 10:54
 * 邮箱：771548229@qq..com
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.mine)
    ImageView mine;
    @BindView(R.id.notify)
    ImageView notify;
    @BindView(R.id.headLayout)
    RelativeLayout headLayout;
    @BindView(R.id.homeOptionRv)
    RecyclerView homeOptionRv;

    @Override
    protected int getContentId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {

        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NotifyActivity.class));
            }
        });
        title.setTextColor(Color.WHITE);
        title.setText(App.getInstance().getUserEntity().getName());
        mine.setVisibility(View.VISIBLE);
        mine.setImageResource(R.mipmap.home_mine_white);
//        headLayout.setBackgroundColor(Color.TRANSPARENT);
        requestNotice();
    }

    private void requestNotice() {
        HttpProxy.obtain().post(PlatformContans.Notice.get, null, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("notice", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String data = object.getString("data");
                    initRv(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onLtFailure(String error) {
                initRv("");
            }
        });
    }

    private void initRv(String notice) {
        RVBaseAdapter<HomeOption> adapter = new RVBaseAdapter<HomeOption>() {
            @Override
            protected void onViewHolderBound(RVBaseViewHolder holder, int position) {

            }
        };
        List<HomeOption> list = new ArrayList<>();

        //添加banner
        HomeOption homeBanner = new HomeOption();
        homeBanner.setLayoutType(1);
        list.add(homeBanner);

        //添加公告
        HomeOption homeBanner2 = new HomeOption();
        homeBanner2.setLayoutType(2);
        homeBanner2.setNotice(notice);
        list.add(homeBanner2);

        //添加酒店预定
        HomeOption homeOption = new HomeOption();
        homeOption.setBgRes(R.drawable.shape_rectangle_home_item_bg1);
        homeOption.setName(getResources().getString(R.string.home_option_name1));
        homeOption.setDes(getResources().getString(R.string.home_option_dex1));
        homeOption.setTagImgRes(R.mipmap.home_thehotel);
        list.add(homeOption);

        //添加其他
        HomeOption homeOption3 = new HomeOption();
        homeOption3.setName("其他");
        homeOption3.setLayoutType(3);
        list.add(homeOption3);

        adapter.setData(list);
        homeOptionRv.setLayoutManager(new LinearLayoutManager(getContext()));
        homeOptionRv.setAdapter(adapter);
    }


    @OnClick({R.id.mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine:
                startActivity(new Intent(getContext(), MineActivity.class));
                break;
        }
    }

}
