package com.ljr.shoppingmall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends AppCompatActivity {
    @Bind(R.id.ib_good_info_back)
    ImageView ibGoodInfoBack;
    @Bind(R.id.webview)
    WebView webview;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        webview.loadUrl("https://github.com/ljrRookie");
        WebSettings settings = webview.getSettings();
        //支持双击页面变大小
        settings.setUseWideViewPort(true);
        //支持JavaScript
        settings.setJavaScriptEnabled(true);
        //优先使用缓存
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制区用Webview
                view.loadUrl(url);
                return true;
            }
        });
        webview.loadUrl("https://market.m.taobao.com/apps/abs/10/177/3fbd84?spm=a21bo.2017.201862-1.d1.7b7211d9QC7PEW&pos=1&_wvUseWKWebView=YES&psId=2120229&acm=20140506001.1003.2.5524277&scm=1003.2.20140506001.OTHER_1552555919375_5524277");
    }

    @OnClick(R.id.ib_good_info_back)
    public void onClick() {
        finish();
    }
}
