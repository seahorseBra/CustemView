package com.example.administrator.custemview;

import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class WebActivity extends BaseActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mWebView = (WebView) findViewById(R.id.web);
        try {
            WebSettings settings = mWebView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setAppCacheEnabled(true);
            mWebView.setWebViewClient(new WebViewClient());
            mWebView.setWebChromeClient(new WebChromeClient());
            settings.setDefaultTextEncodingName("utf-8");
            mWebView.addJavascriptInterface(new WebInterface(), "jsObj");
            mWebView.loadUrl("http://localhost:8080/chatProject/NewFile.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class WebInterface {

        @JavascriptInterface
        public String HtmlcallJava(){
            return "chedandan";
        }

        @JavascriptInterface
        public String HtmlcallJava2(String data){
            return "HtmlcallJava2:"+data;
        }

        @JavascriptInterface
        public void JavacallHtml(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadUrl("javascript: showFromHtml()");
                }
            });
        }

        @JavascriptInterface
        public void JavacallHtml2(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadUrl("javascript: showFromHtml2('武器')");
                }
            });
        }
    }
}
