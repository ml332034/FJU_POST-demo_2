package com.example.lawrence.fju_post;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowPostActivity extends AppCompatActivity {

    int pos=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpost);
        Intent intent = getIntent();
        pos = intent.getExtras().getInt("Position");


        final ShowPostAdapter adapter = new ShowPostAdapter(this);

        final TextView a =  (TextView) findViewById(R.id.class1);
        final TextView b =  (TextView) findViewById(R.id.class2);
        final TextView c =  (TextView) findViewById(R.id.class3);
        final TextView d =  (TextView) findViewById(R.id.class4);

        //set data
        a.setText("活動名稱:"+adapter.price[pos]);
        b.setText("活動類別:"+adapter.names[pos]);
        c.setText("活動時間:2018/03/19 星期一 ");
        d.setText("內容：「珍愛生命守門人」培訓課程能讓同學參與全校性自殺防治工作擔任守門人，" +
                "透過「一問、二應、三轉介」尋求適當資源協助有自殺意念的人。" +
                "珍愛生命守門人為學習當一位陪伴者，陪伴心情低落的人渡過低潮期。」 " +
                "***本活動搭配預約固定班級，成員有同班同學，有興趣的同學可自由入場毋須報名*********");


        Button btnnext = (Button)findViewById(R.id.btnnext);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowPostActivity.this,WebpostActivity.class));

            }
        });
    }


}

