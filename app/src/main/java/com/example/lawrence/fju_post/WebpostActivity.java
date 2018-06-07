package com.example.lawrence.fju_post;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("Registered")

public class  WebpostActivity extends AppCompatActivity {


    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webpost_info);

        WebView webpostview = (WebView) findViewById(R.id.webpostview);
        WebSettings webSettings = webpostview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        setContentView(webpostview);
        webpostview.setWebViewClient(new WebViewClient());
        webpostview.loadUrl("http://activity.dsa.fju.edu.tw/Activity.jsp?activityID=26495");

    }
}