package com.example.lawrence.fju_post;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CalendarView;
import android.widget.TextView;

public class FragmentCalendar extends Fragment {
    View view;
    private TabLayout tabLayout;
    private ViewPager firstViewPager;
    CalendarView calendarView;
    TextView myDate;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_calendar,container,false);
        WebView webView = (WebView) view.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://calendar.google.com/calendar/embed?src=secret%40gapp.fju.edu.tw&ctz=Asia%2FTaipei");



        return view;


    }



}


