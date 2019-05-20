package com.tec.travelagency;

import android.app.Application;
import android.content.Context;

import android.app.ActivityManager;
import android.app.Service;
import android.content.pm.PackageManager;
import android.util.Log;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.tec.travelagency.Constant.Config;

import com.tec.travelagency.JPush.JpushConfig;
import com.tec.travelagency.baiduMap.LocationService;
import com.tec.travelagency.bean.ServiceModel;
import com.tec.travelagency.common.bean.UserEntity;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.processor.OkHttpProcessor;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.QbSdk;

import java.util.Iterator;
import java.util.List;

/**
 * Created by pengying on 2017/3/9.
 */
public class App extends Application {
    private static App instance;
    private static Context context;
    private UserEntity mUserEntity;



    public static  ServiceModel mServiceModel;
    private static final String TAG = "App--->>>";
    public static final boolean ISDEBUG = false;//是否开启调试模式
    public LocationService locationService;
    public static IWXAPI mWxApi;
    public static Boolean  isLogin=false;
    //在自己的Application中添加如下代码

//eb370995f87d48ff92d13c22a320ffd9
    //MD5   EB:37:09:95:F8:7D:48:FF:92:D1:3C:22:A3:20:FF:D9
    // SHA1: 5A:4B:3E:4D:6A:BD:C7:00:11:C3:D2:53:F6:08:1C:CB:91:B2:7E:14
    // SHA256: 3A:7B:26:2F:4D:6D:C0:75:2D:62:64:DF:BC:05:BA:D9:AB:46:35:70:C4:2F:42:0C:27:BB:E8:C7:5A:69:D9:58
    private void registerToWX() {
        //第二个参数是指你应用在微信开放平台上的AppID
        mWxApi = WXAPIFactory.createWXAPI(this, Config.WeiX.APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(Config.WeiX.APP_ID);

    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = this;
        HttpProxy.init(new OkHttpProcessor());
        initECChat();

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
        //初始化极光推送，先停止掉，在需要的地方注册别名，和开启推送
        JpushConfig.getInstance().initJpush();
        //JpushConfig.getInstance().stopJpush();
        locationService = new LocationService(this);
        registerToWX();
        //百度地图
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

    }

    private void initECChat() {
        //初始话环信环境
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        options.setAutoLogin(true);
        // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
        // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);
        //初始化
        EMClient.getInstance().init(this, options);
        EaseUI.getInstance().init(this, options);

        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        if (processAppName == null || !processAppName.equalsIgnoreCase(instance.getPackageName())) {
            android.util.Log.e(TAG, "enter the service process!");
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }

        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    //获取processAppName
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    public void setUserInfo(UserEntity entity) {
        this.mUserEntity = entity;
    }

    public UserEntity getUserEntity() {
        return mUserEntity;
    }

    public static App getInstance() {
        return instance;
    }

    public static Context getContext() {
        return context;
    }

}
