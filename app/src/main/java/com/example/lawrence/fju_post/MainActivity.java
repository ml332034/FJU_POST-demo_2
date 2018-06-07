package com.example.lawrence.fju_post;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler.sendEmptyMessageDelayed(GOTO_MAIN_ACTIVITY, 2000);
    }
    private static final int GOTO_MAIN_ACTIVITY = 0;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case GOTO_MAIN_ACTIVITY:
                    Intent intent = new Intent();
                    //將原本Activity的換成MainActivity
                    intent.setClass(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;

                default:
                    break;
            }
        }

    };

    private Spinner yearnum,monthnum,datenum;
    public void SelectDate(){

        yearnum = (Spinner)findViewById(R.id.yearnumber);
        ArrayAdapter<CharSequence> yearnumber = ArrayAdapter.createFromResource(MainActivity.this,
                R.array.yearnumber,
                android.R.layout.simple_spinner_dropdown_item);

        monthnum = (Spinner)findViewById(R.id.yearnumber);
        ArrayAdapter<CharSequence> monthnum = ArrayAdapter.createFromResource(MainActivity.this,
                R.array.monthnum,
                android.R.layout.simple_spinner_dropdown_item);

        datenum = (Spinner)findViewById(R.id.yearnumber);
        ArrayAdapter<CharSequence> datenum = ArrayAdapter.createFromResource(MainActivity.this,
                R.array.datenum,
                android.R.layout.simple_spinner_dropdown_item);

    }

}
