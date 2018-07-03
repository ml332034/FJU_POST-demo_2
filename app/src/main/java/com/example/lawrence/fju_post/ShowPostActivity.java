package com.example.lawrence.fju_post;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import android.content.Intent;
import android.widget.TextView;

import java.util.List;
import javax.annotation.Nullable;


public class ShowPostActivity extends AppCompatActivity {

    int pos=0;

    private String TAG = "FireLog";
    private  FirebaseFirestore mFirestore;
    private TextView mclass1;
    private TextView mclass2;
    private TextView mclass3;
    private TextView mclass4;
    private TextView mclass5;
    private TextView mclass6;
    private TextView mclass7;
    private TextView mclass8;
    private TextView mclass9;

    private  Button btnnext;
    private List<Post> usersList;
    private ShowPostAdapter userListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpost);

        mclass1 = (TextView) findViewById(R.id.class1);
        mclass2 = (TextView) findViewById(R.id.class2);
        mclass3 = (TextView) findViewById(R.id.class3);
        mclass4 = (TextView) findViewById(R.id.class4);
        mclass5 = (TextView) findViewById(R.id.class5);
        mclass6 = (TextView) findViewById(R.id.class6);
        mclass7 = (TextView) findViewById(R.id.class7);
        mclass8 = (TextView) findViewById(R.id.class8);
        mclass9 = (TextView) findViewById(R.id.class9);
        userListAdapter = new  ShowPostAdapter (getApplicationContext(),usersList);
        Intent intent = getIntent();
        String user_id = intent.getStringExtra("user_id");
        mFirestore = FirebaseFirestore.getInstance();

                        mFirestore.collection("Users").document(user_id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                                String class1 = documentSnapshot.getString("activity_name");
                                String class2 = documentSnapshot.getString("activity_time_year");
                                String class3 = documentSnapshot.getString("activity_type_id");
                                String class4 = documentSnapshot.getString("activity_location");
                                String class5 = documentSnapshot.getString("organizer");
                                String class6 = documentSnapshot.getString("activities_content");
                                String class7 = documentSnapshot.getString("activity_time");
                                String class8 = documentSnapshot.getString("activity_time_month");
                                String class9 = documentSnapshot.getString("activity_time_date");

                                mclass1.setText("活動名稱 : "+class1);
                                mclass2.setText("活動時間 : "+class2+"年");
                                mclass3.setText("活動類別 : "+class3);
                                mclass4.setText("活動地點 : "+class4);
                                mclass5.setText("主辦單位 : "+class5);
                                mclass6.setText("內容 : "+class6);
                                mclass7.setText("開始時間 : "+class7);
                                mclass8.setText(class8+"月");
                                mclass9.setText(class9+"日");
                            }
                        });







        Button btnnext = (Button)findViewById(R.id.btnnext);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowPostActivity.this,WebpostActivity.class));

            }
        });

    }








}
