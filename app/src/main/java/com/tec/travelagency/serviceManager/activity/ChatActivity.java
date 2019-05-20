package com.tec.travelagency.serviceManager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;

import android.widget.RelativeLayout;
import android.widget.Toast;


import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.tec.travelagency.App;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.common.bean.UserEntity;
import com.tec.travelagency.manager.ActivityManager;
import com.tec.travelagency.utils.ToaskUtil;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class ChatActivity extends BaseActivity {
    protected static final int REQUEST_CODE_MAP = 1;

    @BindView(R.id.chatContainer)
    RelativeLayout chatContainer;
    private String mUserId;
    private String username;

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }

    private String headurl;
    private int mRequestCode;
    private EaseChatFragment mChatFragment;

    public static void startChatActivity(Activity activity, String userId, int requestCode) {
        Intent intent = new Intent(activity, ChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_USER_ID, userId);
        intent.putExtra("requestCode", requestCode);
        activity.startActivityForResult(intent, requestCode);

    }

    @Override
    protected void initView() {

//        getSupportFragmentManager().beginTransaction().add(R.id.chatContainer,new )
        //new出EaseChatFragment或其子类的实例

        Intent intent = getIntent();
        mUserId = intent.getStringExtra(EaseConstant.EXTRA_USER_ID);
        mRequestCode = intent.getIntExtra("requestCode", 0);
        if (TextUtils.isEmpty(mUserId)) {
            ToaskUtil.showToast(this, "当旅行社环信id为空");
            finish();
            return;
        }
        initFragment(mUserId);
    }

    private void initFragment(String userId) {
        UserEntity userInfo = App.getInstance().getUserEntity();
        String nickname = userInfo.getNickname();
        String image = userInfo.getLogoUrl();
        String imageKey = userInfo.getLogo();
        mChatFragment = new EaseChatFragment();
        mChatFragment.setLication(mLication);
        mChatFragment.setHeadimg(image);
        mChatFragment.setHeadimgKey(imageKey);
        mChatFragment.setNikename(nickname);
        EaseUserUtils.saveMeUserHeadUrl(image);
        EaseUserUtils.userName=nickname;
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, userId);
        mChatFragment.setArguments(args);
        try {
            getSupportFragmentManager().beginTransaction().add(R.id.chatContainer, mChatFragment).commit();
        } catch (Exception e) {
            finish();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(mRequestCode);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_chat;
    }

    private EaseChatFragment.ILaunchLication mLication = new EaseChatFragment.ILaunchLication() {
        @Override
        public void startEaseBaiduMapActivity() {
//            ToaskUtil.showToast(ChatActivity.this, "跳转定位界面");
//            startActivityForResult(new Intent(ChatActivity.this, EaseBaiduMapActivity2.class), REQUEST_CODE_MAP);
        }

        @Override
        public void checkMap(double lat, double lon) {
//            EaseBaiduMapActivity.onStartEaseBaiduMap(ChatActivity.this, lat, lon);
        }

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_MAP) { // location
                double latitude = data.getDoubleExtra("latitude", 0);
                double longitude = data.getDoubleExtra("longitude", 0);
                String locationAddress = data.getStringExtra("address");
//                ToaskUtil.showToast(this, locationAddress + "经度:" + longitude + ",维度:" + latitude);
                if (locationAddress != null && !locationAddress.equals("")) {
                    if (mChatFragment != null) {
                        mChatFragment.sendLocationMessage(latitude, longitude, locationAddress);
                    }
                } else {
                    Toast.makeText(this, com.hyphenate.easeui.R.string.unable_to_get_loaction, Toast.LENGTH_SHORT).show();
                }

            }
        }
    }



}
