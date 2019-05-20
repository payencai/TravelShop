package com.tec.travelagency.common.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.tec.travelagency.App;
import com.tec.travelagency.R;
import com.tec.travelagency.baiduMap.LocationService;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.dataManager.fragment.DataFragment;
import com.tec.travelagency.home.fragment.HomeFragment;
import com.tec.travelagency.orderManager.fragment.OrderManagerFragment;
import com.tec.travelagency.serviceManager.activity.ChatActivity;
import com.tec.travelagency.utils.LoginSharedUilt;
import com.tec.travelagency.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_iv_home)
    ImageView mainIvHome;
    @BindView(R.id.main_tv_home)
    TextView mainTvHome;

    @BindView(R.id.main_iv_order)
    ImageView mainIvOrder;
    @BindView(R.id.main_tv_order)
    TextView mainTvOrder;

    @BindView(R.id.main_iv_service)
    ImageView mainIvService;
    @BindView(R.id.main_tv_service)
    TextView mainTvService;

    @BindView(R.id.main_iv_data)
    ImageView mainIvData;
    @BindView(R.id.main_tv_data)
    TextView mainTvData;
    @BindView(R.id.my_unread_msg_number)
    TextView msgNumber;
    @BindView(R.id.homeBgImg)
    ImageView homeBgImg;
    @BindView(R.id.bottom_column)
    LinearLayout bottomColumn;

    private List<Fragment> fragments;
    private FragmentManager fm;
    public static final int CLEAR_UNREAD_MSG = 1 << 4;

    //未读的信息数量
    private int unReadMsg = 0;
    private LocationService locationService;

    @Override
    protected int getContentId() {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        locationService = App.getInstance().locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，
        // 然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(myListener);
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
//        Glide.with(this).load(R.mipmap.home_bg).into(homeBgImg);
        Log.d("initView", "initView: " + ScreenUtils.getStatusHeight(this));
        fm = getSupportFragmentManager();
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new OrderManagerFragment());
//        fragments.add(new UserOrderCategoryFragment());

        EaseConversationListFragment conversationListFragment = new EaseConversationListFragment();
        conversationListFragment.setHeadUrl(App.mServiceModel.getHeadingUrl());
        conversationListFragment.setUserId(App.mServiceModel.getUsername());
        conversationListFragment.setUsername(App.mServiceModel.getNickname());
        conversationListFragment.setOnChatListener(new EaseConversationListFragment.OnChatListener() {
            @Override
            public void chatclick() {
                Log.e("username",App.mServiceModel.getUsername());
                ChatActivity.startChatActivity(MainActivity.this, App.mServiceModel.getUsername(), CLEAR_UNREAD_MSG);
            }
        });
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
//                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
//                intent.putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId());
//                startActivity(intent);
                ChatActivity.startChatActivity(MainActivity.this, conversation.conversationId(), CLEAR_UNREAD_MSG);
            }
        });

//        fragments.add(new ServiceFragment());
        fragments.add(conversationListFragment);
        fragments.add(new DataFragment());


        for (Fragment fragment : fragments) {
            if (!fragment.isAdded())
                fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

        //显示主页
        resetStateForTagbar(R.id.main_home);
        hideAllFragment();
        showFragment(0);
        String userName="";
        String psw="";
        if(App.getInstance().getUserEntity()!=null)
        {
            userName = App.getInstance().getUserEntity().getSystemId();
            psw = App.getInstance().getUserEntity().getHxPassword();
        }
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(psw)) {
            LoginEM(userName, psw);
        }
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
        obtionUnReadMsg();
        setMsgNumber();

        locationService.start();
    }

    private void obtionUnReadMsg() {
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        for (String s : conversations.keySet()) {
            getUnreadMsg(s);
        }
        setMsgNumber();
    }

    private void resetStateForTagbar(int viewId) {
        selected();
        if (viewId == R.id.main_home) {
            mainIvHome.setSelected(true);
            mainTvHome.setSelected(true);
            return;
        }
        if (viewId == R.id.main_order) {
            mainIvOrder.setSelected(true);
            mainTvOrder.setSelected(true);
            return;
        }
        if (viewId == R.id.main_service) {
            mainIvService.setSelected(true);
            mainTvService.setSelected(true);
            return;
        }
        if (viewId == R.id.main_data) {
            mainIvData.setSelected(true);
            mainTvData.setSelected(true);
            return;
        }

    }

    //重置所有文本的选中状态
    public void selected() {
        mainIvHome.setSelected(false);
        mainIvOrder.setSelected(false);
        mainIvService.setSelected(false);
        mainIvData.setSelected(false);

        mainTvHome.setSelected(false);
        mainTvOrder.setSelected(false);
        mainTvService.setSelected(false);
        mainTvData.setSelected(false);

    }

    private void hideAllFragment() {
        for (Fragment fragment : fragments) {
            fm.beginTransaction().hide(fragment).commit();
        }
    }

    private void showFragment(int position) {
        fm.beginTransaction().show(fragments.get(position)).commit();
        setBottombarBg(position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);

        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        locationService.unregisterListener(myListener); //注销掉监听
        locationService.stop(); //停止定位服务
    }

    @OnClick({R.id.main_home, R.id.main_order, R.id.main_service, R.id.main_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_home:
                resetStateForTagbar(R.id.main_home);
                hideAllFragment();
                showFragment(0);
                break;
            case R.id.main_order:
                resetStateForTagbar(R.id.main_order);
                hideAllFragment();
                showFragment(1);
                break;
            case R.id.main_service:
                resetStateForTagbar(R.id.main_service);
                hideAllFragment();
                showFragment(2);
                break;
            case R.id.main_data:
//                if (isLogin()) {
                resetStateForTagbar(R.id.main_data);
                hideAllFragment();
                showFragment(3);
//                }
                break;
        }
    }

    /**
     * 根据点击的选项，修改背景颜色
     *
     * @param type
     */
    private void setBottombarBg(int type) {
        if (type == 0) {
//            bottomColumn.setBackgroundColor(Color.parseColor("#00f8f8f8"));
            bottomColumn.setBackgroundColor(Color.parseColor("#f8f8f8"));
        } else {
            bottomColumn.setBackgroundColor(Color.parseColor("#f8f8f8"));
        }
    }

    private void LoginEM(String userName, String password) {
        EMClient.getInstance().login(userName, password, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("lingtaoMain", "登录环信成功");

            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d("lingtaoMain", "onProgress: " + progress + "-----status:" + status);
            }

            @Override
            public void onError(int code, String message) {
                Log.d("lingtaoMain", "登录聊天服务器失败！");
            }
        });
    }

    private void getUnreadMsg(String username) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
        if(conversation==null)
            return;
        int unreadMsgCount = conversation.getUnreadMsgCount();
        unReadMsg += unreadMsgCount;
    }


    private void setMsgNumber() {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            if (unReadMsg <= 0) {
                msgNumber.setVisibility(View.GONE);

            } else {
                msgNumber.setVisibility(View.VISIBLE);
                msgNumber.setText(unReadMsg + "");
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (unReadMsg <= 0) {
                        msgNumber.setVisibility(View.GONE);

                    } else {
                        msgNumber.setVisibility(View.VISIBLE);
                        msgNumber.setText(unReadMsg + "");
                    }
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        msgNumber.setText("");
        msgNumber.setVisibility(View.GONE);
        unReadMsg = 0;
        obtionUnReadMsg();
    }

    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
//            getUnreadMsg(mCurrentHelpInfo.getGroupId());
            Log.d("onMessageReceived", "onMessageReceived: " + Thread.currentThread().getName());
            unReadMsg++;
            setMsgNumber();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            //消息被撤回
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };

    private String locationcity;
    private String locationprovince;
    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDAbstractLocationListener myListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            locationcity = location.getCity();
            locationprovince = location.getProvince();
            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            int LocType = location.getLocType();    //返回码


            lat = location.getLatitude();
            lon = location.getLongitude();

            Log.d("onReceiveLocation", "onReceiveLocation: " + lat);
            Log.d("onReceiveLocation", "onReceiveLocation: " + lon);

            saveLocation(city, addr);
            locationService.setLocationOption(locationService.getSingleLocationClientOption());
        }

    };

    private double lat;
    private double lon;

    public void saveLocation(String city, String addr) {
        LoginSharedUilt intance = LoginSharedUilt.getIntance(this);
        intance.saveLat(lat);
        intance.saveLon(lon);
        intance.saveCity(city);
        intance.saveAddr(addr);

    }


}
