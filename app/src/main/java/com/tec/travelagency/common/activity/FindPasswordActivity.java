package com.tec.travelagency.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.manager.ActivityManager;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.utils.UserInfoSharedPre;
import com.tec.travelagency.widget.KyLoadingBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindPasswordActivity extends BaseActivity {


    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.old_password)
    EditText oldPassword;
    @BindView(R.id.new_password)
    EditText newPassword;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.title)
    TextView title;

    private String mTitle;
    private KyLoadingBuilder mFindPswLoadView;

    @Override
    protected int getContentId() {
        return R.layout.activity_find_password;
    }

    @Override
    protected void initView() {
        mTitle = "找回密码";
        title.setText(mTitle);
    }


    @OnClick({R.id.back, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                String accoutStr = account.getEditableText().toString().replace(" ", "");
                String oldPsw = oldPassword.getEditableText().toString().replace(" ", "");
                String newPsw = newPassword.getEditableText().toString().replace(" ", "");
                if (check(accoutStr, oldPsw, newPsw)) {
                    mFindPswLoadView = openLoadView("");
                    submit.setEnabled(false);
                    requestUpdatePsw(accoutStr, oldPsw, newPsw);
                }
                break;
        }

    }

    private void requestUpdatePsw(String accoutStr, String oldPsw, String newPsw) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("account", accoutStr);
        parame.put("oldPassword", oldPsw);
        parame.put("newPassword", newPsw);
        HttpProxy.obtain().post(PlatformContans.TravelAgencyUser.updatePassword, parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("updatePassword", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        ActivityManager.getInstance().finishAllActivity();
                        EMClient.getInstance().logout(true);
                        UserInfoSharedPre intance = UserInfoSharedPre.getIntance(FindPasswordActivity.this);
                        intance.clearUserInfo();
                        Intent intent = new Intent(FindPasswordActivity.this, LoginActivity.class);
                        intent.putExtra("isUpdatePsw", true);
                        startActivity(intent);
                        ToaskUtil.showToast(FindPasswordActivity.this, "修改密码成功");
                    } else {
                        ToaskUtil.showToast(FindPasswordActivity.this, message);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {

            }
        });
    }

    private boolean check(String accoutStr, String oldPsw, String newPsw) {
        if (TextUtils.isEmpty(accoutStr)) {
            ToaskUtil.showToast(this, "请输入账号");
            return false;
        }
        if (TextUtils.isEmpty(oldPsw)) {
            ToaskUtil.showToast(this, "请输入久密码");
            return false;
        }
        if (TextUtils.isEmpty(newPsw)) {
            ToaskUtil.showToast(this, "请输入新密码");
            return false;
        }
        if (oldPsw.equals(newPsw)) {
            ToaskUtil.showToast(this, "新密码不能和久密码相同");
            return false;
        }
        return true;
    }
}
