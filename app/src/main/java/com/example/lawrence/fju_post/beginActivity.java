package com.example.lawrence.fju_post;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class beginActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);


        final Button studentbutton = (Button) findViewById(R.id.button4);
        final Button posterbutton = (Button) findViewById(R.id.button5);
        studentbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(beginActivity.this, StudentActivity.class);
                startActivity(intent);
            }
        });

        posterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(beginActivity.this, posterActivity.class);
                startActivity(intent1);
            }
        });
    }
}
