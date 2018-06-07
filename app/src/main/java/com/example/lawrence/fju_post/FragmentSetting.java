package com.example.lawrence.fju_post;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentSetting extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_setting);

         firebaseAuth=FirebaseAuth.getInstance();
         logout =(Button)findViewById(R.id.buttonSignOut);

         logout.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View view){
                 firebaseAuth.signOut();
                 finish();
                 startActivity(new Intent(FragmentSetting.this,LoginActivity.class));
             }
         });

     }
}
