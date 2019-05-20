package com.tec.travelagency.common.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;


import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.utils.ToaskUtil;

import java.net.URLDecoder;

import butterknife.BindView;

public class AddressWebViewActivity extends BaseActivity {

    @BindView(R.id.webViewContainer)
    FrameLayout webViewContainer;

    private String mUrl;
    private int mRequestCode;
    private WebView webView;

    public static void startWebView(Activity context, String url, int requestCode) {
        Intent intent = new Intent(context, AddressWebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("requestCode", requestCode);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_address_web_view;
    }

    @Override
    protected void initView() {
        Log.e("build", Build.VERSION.SDK_INT+"");
        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
       // mUrl="https://apis.map.qq.com/tools/locpicker?search=1&type=0&backurl=http://3gimg.qq.com/lightmap/components/locationPicker2/back.html&key=2TIBZ-ZBMHF-PDOJ5-NSB36-VNTUJ-RUBSC" +
               // "&referer=myapp";
        //mUrl="https://m.amap.com/picker/?keywords=%E5%86%99%E5%AD%97%E6%A5%BC,%E5%B0%8F%E5%8C%BA,%E5%AD%A6%E6%A0%A1&zoom=15&center=113.401847,23.048944&radius=1000&total=20&key=eaa189e8ad892d2f7a08538d92efa1f1";
        mRequestCode = intent.getIntExtra("requestCode", -1);
        if (TextUtils.isEmpty(mUrl)) {
            ToaskUtil.showToast(this, "地址不能为空");
            finish();
            return;
        }
        if (mRequestCode < 0) {
            ToaskUtil.showToast(this, "请求码不能空");
            finish();
            return;
        }
        webView = new WebView(getApplicationContext());
        webViewContainer.addView(webView);
        webView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=getWz_json();
                Log.e("json",str);
            }
        });
        WebSettings webSettings = webView.getSettings();
        //允许javascript执行
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        //webView.addJavascriptInterface(new JavaScriptObject(this), "myObj");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("url",url);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }
            //开始加载网页时回调
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d("onPageFinished", "------------:" + url);
                if (url.startsWith("http://www.test.com/?loc=")) {
                    try {
                        String[] split = url.split("loc=");
                        String s = split[1];
                        String strUTF8 = URLDecoder.decode(s, "UTF-8");
                        Intent intent = new Intent();
                        intent.putExtra("address", strUTF8);
                        setResult(mRequestCode, intent);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    webView.stopLoading();
                }
            }

            //网页加载结束时回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("onPageFinished", "onPageFinished: " + url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                Log.e("url",message);
                AlertDialog.Builder b = new AlertDialog.Builder(AddressWebViewActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();

                return true;
            }

            //获取网页标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.d("google.sang", "onReceivedTitle: " + title);
            }
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                Log.e("aaa","aaa");
                callback.invoke(origin, true, true);
                //super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });
        if (Build.VERSION.SDK_INT >= 23) {
            int checkPermission = ContextCompat.checkSelfPermission(AddressWebViewActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
                if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AddressWebViewActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                ActivityCompat.requestPermissions(AddressWebViewActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else {
                //webView.loadUrl("file:///android_asset/map.html");
                webView.loadUrl(mUrl);
            }
        }else{
            webView.loadUrl(mUrl);
        }

    }
    String wz_json;

    public String getWz_json() {
        return wz_json;
    }

    public void setWz_json(String wz_json) {
        this.wz_json = wz_json;
    }

    public class JavaScriptObject {
        Context mContxt;
        public JavaScriptObject(Context mContxt) {
            this.mContxt = mContxt;
        }
        @JavascriptInterface //sdk17版本以上加上注解
        public void setLoc(String msg) {
            wz_json=msg;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0){
            webView.loadUrl(mUrl);
        }else{
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        //如果网页可以后退，则网页后退
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            // 如果先调用destroy()方法，则会命中
            // if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再 // destory()
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }
            webView.stopLoading(); // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();
            webView.destroy();
        }
        super.onDestroy();


    }
}
