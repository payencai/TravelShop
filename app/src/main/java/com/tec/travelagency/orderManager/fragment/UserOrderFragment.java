package com.tec.travelagency.orderManager.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.widget.Toast;

import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseFragment;
import com.tec.travelagency.eventBusBean.PhoneCallBack;
import com.tec.travelagency.orderManager.adapter.OrderManagerVpAdapter;
import com.tec.travelagency.orderManager.fragment.user.UserCarOrderFragment;
import com.tec.travelagency.orderManager.fragment.user.UserHotelOrderFragment;
import com.tec.travelagency.orderManager.fragment.user.UserMeetingOrderFragment;
import com.tec.travelagency.orderManager.fragment.user.UserPathOrderFragment;
import com.tec.travelagency.orderManager.fragment.user.UserTicketsOrderFragment;
import com.tec.travelagency.utils.ToaskUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：凌涛 on 2018/9/25 11:30
 * 邮箱：771548229@qq..com
 */
public class UserOrderFragment extends BaseFragment {

    @BindView(R.id.order_viewpager)
    ViewPager orderViewpager;
    @BindView(R.id.lineTablayout)
    TabLayout lineTablelayout;

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;


    private OrderManagerVpAdapter mVpAdapter;

    //    private static final String[] titles = new String[]{"酒店", "租车", "门票", "路线", "会议室"};
    private static final String[] titles = new String[]{"酒店", "门票", "租车", "路线", "会议室"};


    private List<Fragment> mFragments;

    @Override
    protected int getContentId() {
        return R.layout.fragment_user_order;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        initFragment();

        mVpAdapter = new OrderManagerVpAdapter(getActivity().getSupportFragmentManager(), mFragments, titles);
        orderViewpager.setAdapter(mVpAdapter);
        orderViewpager.setOffscreenPageLimit(mFragments.size());
        lineTablelayout.setupWithViewPager(orderViewpager);
        orderViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            switch (i) {
                case 1:
                    mFragments.add(UserHotelOrderFragment.getInstance(i));
                    break;
                case 2:
                    mFragments.add(UserTicketsOrderFragment.getInstance(i));
                    break;
                case 3:
                    mFragments.add(UserCarOrderFragment.getInstance(i));
                    break;
                case 4:
                    mFragments.add(UserPathOrderFragment.getInstance(i));
                    break;
                case 5:
                    mFragments.add(UserMeetingOrderFragment.getInstance(i));
                    break;

            }
        }
    }


    private String mTel;

    @Subscribe
    public void CallTel(PhoneCallBack phoneCallBack) {
        mTel = phoneCallBack.getTel();

        if (TextUtils.isEmpty(mTel)) {
            ToaskUtil.showToast(getContext(), "号码为空");
            return;
        }
        checkPower();
    }

    private void checkPower() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            callPhone();
        }
    }

    @SuppressLint("MissingPermission")
    public void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + mTel);
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhone();
            } else {
                // Permission Denied
                Toast.makeText(getContext(), "权限拒绝", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
