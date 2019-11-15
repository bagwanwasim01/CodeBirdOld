package com.example.wasim.compileit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebviewOutput extends AppCompatActivity {
    WebView w;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_output);
        Intent i=getIntent();
        Bundle bundle=i.getExtras();
        String weboutput=bundle.getString("webdata");
        w=(WebView)findViewById(R.id.webview);
        WebSettings webSettings=w.getSettings();
        // webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        w.setWebChromeClient(new WebChromeClient());
        // w.Settings.DomStorageEnabled(true);
        w.loadDataWithBaseURL("file:///android_asset/","text/html","UTF-8",null,null);
        // webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        w.loadData(weboutput,null,null);
    }
}
