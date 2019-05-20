package com.tec.travelagency.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tec.travelagency.Constant.Config;
import com.tec.travelagency.utils.ToaskUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    // IWXAPI 是第三方app和微信通信的openapi接口
    private static final int WX_LOGIN = 1;
    private static IWXAPI iwxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iwxapi = WXAPIFactory.createWXAPI(this, Config.WeiX.APP_ID, true);
        iwxapi.registerApp(Config.WeiX.APP_ID);
        iwxapi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        iwxapi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.d("BaseResp", "onReq: 发送。。。" + baseReq.transaction);
    }

    @Override
    public void onResp(BaseResp baseResp) {
        /*微信登录为getType为1，分享网页为2*/
        Log.d("BaseResp", "onResp: ?????");
        Log.d("BaseResp", "" + baseResp.getType());
        Log.d("BaseResp", "" + baseResp.errStr);
        Log.d("BaseResp", "" + baseResp.openId);
        Log.d("BaseResp", "" + baseResp.transaction);
        Log.d("BaseResp", "" + baseResp.errCode);
        Log.d("BaseResp", "" + baseResp.checkArgs());
        if (baseResp.getType() == WX_LOGIN) {
            SendAuth.Resp resp = (SendAuth.Resp) baseResp;
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    ToaskUtil.showToast(this, "授权成功");
                    String code = String.valueOf(resp.code);
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    ToaskUtil.showToast(this, "拒绝授权");
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    ToaskUtil.showToast(this, "用户取消");
                    finish();
                    break;
            }
        }
        if (baseResp.getType() == 2) {//网页分享
            if (baseResp.errCode == 0) {
                ToaskUtil.showToast(this, "分享成功");
            } else {
                ToaskUtil.showToast(this, "分享失败");
            }
            finish();
        }
    }




}
