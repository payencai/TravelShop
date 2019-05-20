package com.tec.travelagency.common.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tec.travelagency.R;
import com.tec.travelagency.utils.X5WebView;
import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.net.URLDecoder;

public class X5WebviewActivity extends AppCompatActivity {
     X5WebView mX5WebView;
     TextView title;
     ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x5_webview);
        mX5WebView=findViewById(R.id.webview);
        title=findViewById(R.id.title);
        title.setText("选择地址");
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        WebSettings settings = mX5WebView.getSettings();
        settings.setJavaScriptEnabled(true);
        //设置WebView是否使用viewport
        settings.setUseWideViewPort(true);
        //设置WebView是否使用预览模式加载界面。
        settings.setLoadWithOverviewMode(true);
        //设置WebView是否支持使用屏幕控件或手势进行缩放
        settings.setSupportZoom(true);
        //设置WebView是否使用其内置的变焦机制，该机制集合屏幕缩放控件使用
        settings.setBuiltInZoomControls(true);
        mX5WebView.setWebViewClient(new WebViewClient(){
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
                        setResult(3, intent);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mX5WebView.stopLoading();
                }
            }

            //网页加载结束时回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("onPageFinished", "onPageFinished: " + url);
            }
        });
        mX5WebView.setWebChromeClient(new WebChromeClient(){

        });
        mX5WebView.loadUrl("http://120.79.176.228:8080/gaote-web/map/index.html");
       // mX5WebView.loadUrl("https://apis.map.qq.com/tools/locpicker?search=1&type=0&backurl=http://3gimg.qq.com/lightmap/components/locationPicker2/back.html&key=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77&referer=myapp");
    }
}
