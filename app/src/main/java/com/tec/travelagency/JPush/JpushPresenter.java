package com.tec.travelagency.JPush;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.tec.travelagency.R;
import com.tec.travelagency.common.activity.MainActivity;
import com.tec.travelagency.home.activity.NotifyActivity;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.NotifyUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

import static android.content.Context.NOTIFICATION_SERVICE;

public class JpushPresenter implements JpushContract {
    private Context mContext;
    public static final String TAG="JpushPresenter";
    public JpushPresenter() {
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    /**
     * 接收到自定义的消息，调用自定义的通知显示出来
     */

    @Override
    public void doProcessPushMessage(Bundle bundle) {

    }

    /**
     * 发送通知
     * @param bundle
     */
    @Override
    public void doProcessPusNotify(Bundle bundle) {
        String alert = bundle.getString(JPushInterface.EXTRA_EXTRA);  //扩展消息
        Log.e("alert", alert);
        String title = "新旅盟酒店";
        String content = "";
        String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        try {
            JSONObject jsonObject = new JSONObject(extra);
            JSONObject data=jsonObject.getJSONObject("Result");
            String sound=data.getString("sound");
            content=data.getString("content");
            showNotification(mContext,title,content,sound,bundle);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void showMsg(Bundle bundle){
        Notification.Builder builder =new Notification.Builder(mContext);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        NotifyUtil.showNotify(mContext,message,extras);
    }
    /**
     * 使用Jpush内置的样式构建通知
     */

    public void showNotify(Bundle bundle){
        int i=0;
        Log.e("not",++i+"");
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(mContext);
        builder.statusBarDrawable = R.drawable.app_logo;

        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
        builder.notificationDefaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setPushNotificationBuilder(0, builder);
        //LogUtil.e(TAG,"=====doProcessPusNotify=======");
    }
    /**
     * 点击通知之后的操作在这里
     * @param bundle
     */
    @Override
    public void doOpenPusNotify(Bundle bundle) {
        Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
        //打开自定义的Activity
        Intent i = new Intent(mContext, NotifyActivity.class);
        i.putExtras(bundle);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(i);
    }

    public static void showNotification(Context context, String title, String content, String sound, Bundle bundle) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.app_logo);
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE | NotificationCompat.DEFAULT_LIGHTS);
        Notification notification = null;
        try {
            Method method = Notification.Builder.class
                    .getDeclaredMethod("build");
            if (method != null) {
                notification = builder.build();
            } else {
                notification = builder.getNotification();
            }
        } catch (NoSuchMethodException e) {
            notification = builder.getNotification();
        }
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent openedIntent = new Intent(JPushInterface.ACTION_NOTIFICATION_OPENED);
        openedIntent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, openedIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.contentIntent = pendingIntent;
        String pkgPath = "android.resource://" + context.getPackageName() + "/";

        if("1.mp3".equals(sound)){
            notification.sound = Uri.parse(pkgPath + R.raw.jiedan);
        }
        if("2.mp3".equals(sound)){
            notification.sound = Uri.parse(pkgPath + R.raw.unhandle);
        }
        if("3.mp3".equals(sound)){
            notification.sound = Uri.parse(pkgPath + R.raw.s1);
        }
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);

    }
}
