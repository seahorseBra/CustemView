package com.example.administrator.custemview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FileSystem extends BaseActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_system);
        webView = (WebView) findViewById(R.id.web);
        webView.loadUrl("http://www.tooopen.com/img/87_312.aspx");
    }

    @Override
    protected void onSetting() {

    }
}
