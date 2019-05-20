package com.tec.travelagency.common.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.JPush.JpushConfig;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.bean.ServiceModel;
import com.tec.travelagency.common.adapter.RvItemHolperAdapter;
import com.tec.travelagency.common.bean.UserEntity;
import com.tec.travelagency.common.rvItemHelper.MyCallBack;
import com.tec.travelagency.common.rvItemHelper.RecyListViewOnItemClick;
import com.tec.travelagency.common.rvItemHelper.SimpleItemTouchHelperCallback;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.utils.UserInfoSharedPre;
import com.tec.travelagency.widget.ItemRemoveRecyclerView;
import com.tec.travelagency.widget.KyLoadingBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.submit)
    Button submit;

    @BindView(R.id.find_password_text)
    TextView findPasswordBtn;
    @BindView(R.id.agreement)
    TextView agreement;
    @BindView(R.id.myparent)
    RelativeLayout myparent;
    private KyLoadingBuilder mLoginLoadView;

    private final int SDK_PERMISSION_REQUEST = 127;
    private String permissionInfo;

    @Override
    protected int getContentId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        boolean isUpdatePsw = intent.getBooleanExtra("isUpdatePsw", false);
        if (isUpdatePsw) {
            ToaskUtil.showToast(this, "修改密码成功，请重新登录");
        }
        UserInfoSharedPre intance = UserInfoSharedPre.getIntance(this);
        String username = (String) intance.getUserInfoFiledValue("username");
        String password = (String) intance.getUserInfoFiledValue("password");
        agreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        if (App.ISDEBUG) {
            username = "18408889500";
//            username = "13800138000";
//            username = "17688947788";
//            username = "13552689653";
        }
        Log.d("initView", "initView: " + username);
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            requestLogin(username, password);
        }

        getPersimmions();

    }

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
             */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }


    @OnClick({R.id.submit, R.id.find_password_text, R.id.apply_text, R.id.agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                String acc = account.getEditableText().toString().replace(" ", "");
                String psw = password.getEditableText().toString().replace(" ", "");
//                if (App.ISDEBUG) {
//                    acc = "18408889500";
//                    acc = "13800138000";
//            acc = "13552689653";
//                    psw = "123456";
//                }

                if (checkFrom(acc, psw)) {
                    requestLogin(acc, psw);
                }
                break;
            case R.id.find_password_text:
                startActivity(new Intent(this, FindPasswordActivity.class));
                break;
            case R.id.apply_text:
                WebViewActivity.starUi(this, "www.baidu.com", "垃圾");
                break;
            case R.id.agreement:
                startActivity(new Intent(this, AgreementActivity.class));
                break;
        }

    }

    private void requestLogin(String acc, final String psw) {
        Log.d("requestLogin", "requestLogin: " + acc);
        submit.setEnabled(false);
        mLoginLoadView = openLoadView("");
        Map<String, Object> parame = new HashMap<>();
        parame.put("username", acc);
        parame.put("password", psw);
        HttpProxy.obtain().post(PlatformContans.TravelAgencyUser.login, parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                submit.setEnabled(true);
                closeLoadView(mLoginLoadView);
                LogUtil.Log("loginInfo", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int resultCode = jsonObject.optInt("resultCode");
                    String message = jsonObject.getString("message");
                    if (resultCode == 0) {
                        App.isLogin=true;
                        String data = jsonObject.optJSONObject("data").toString();
                        Gson gson = new Gson();
                        UserEntity entity = gson.fromJson(data.toString(), UserEntity.class);
                        entity.setPassword(psw);
                        //设置标签

                        JpushConfig.getInstance().setTag(entity.getSystemId());
                        //设置别名
                        Log.e("alias",entity.getSystemId()+"");
                        JpushConfig.getInstance().setAlias(entity.getSystemId());
                        //JpushConfig.getInstance().resumeJPush();
                        UserInfoSharedPre intance = UserInfoSharedPre.getIntance(LoginActivity.this);
                        intance.saveUserInfo(entity);
                        getService();

                    } else {
                        ToaskUtil.showToast(LoginActivity.this, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {
                if (submit != null) {
                    submit.setEnabled(true);
                }
                closeLoadView(mLoginLoadView);
            }
        });
    }
    private void getService(){
        HttpProxy.obtain().get(PlatformContans.Service.getBackService, App.getInstance().getUserEntity().getToken(), new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                Log.e("userid",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    String date=jsonObject.getJSONObject("data").toString();
                    App.mServiceModel=new Gson().fromJson(date.toString(), ServiceModel.class);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {

            }
        });
    }
    private boolean checkFrom(String acc, String psw) {
        if (TextUtils.isEmpty(acc)) {
            ToaskUtil.showToast(this, "请输入账号！");
            return false;
        }
        if (TextUtils.isEmpty(psw)) {
            ToaskUtil.showToast(this, "请输入密码！");
            return false;
        }
        if(acc.length()<6){
            ToaskUtil.showToast(this, "账号长度至少6位！");
            return false;
        }
        if(psw.length()<6){
            ToaskUtil.showToast(this, "密码长度至少6位！");
            return false;
        }
        if(!acc.matches("[A-Za-z0-9]*")){
            ToaskUtil.showToast(this, "只能是数字或者字母！");
            return false;
        }

        return true;
    }

}
