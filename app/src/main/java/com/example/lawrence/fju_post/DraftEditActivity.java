package com.example.lawrence.fju_post;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class DraftEditActivity extends AppCompatActivity {

    private static final String TAG = "FireSend";
    private EditText name;

    private EditText editNameText;
    String item = "";
    private EditText organizers;
    private Button send;
    private EditText yearnumber;
    private EditText monthnum;
    private EditText datenum;
    private EditText time;
    private EditText contentbox;
    private EditText location;

    private FirebaseFirestore safinal;

    private FirebaseAuth mAuth;
    private String UID;
    Button mOrder;
    TextView mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft_edit);

        //get userID
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        UID = user.getUid();

        //postID
        Intent intent = getIntent();
        final String user_id = intent.getStringExtra("user_id");

        mOrder = (Button) findViewById(R.id.button6);//
        mItemSelected = (TextView) findViewById(R.id.checkboxSelected);
        listItems = getResources().getStringArray(R.array.shopping_item);
        checkedItems = new boolean[listItems.length];

        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(DraftEditActivity.this);

                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    //類別點選
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked) {
                            if (!mUserItems.contains(position)) {
                                mUserItems.add(position);
                            } else if(!mUserItems.contains(position)){
                                mUserItems.remove(position);
                            }
                        }
                    }
                });
                //確認
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        item="";
                        for (int i = 0; i < mUserItems.size(); i++) {

                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) ;
                            {
                                item = item + ",";
                            }
                        }
                        mItemSelected.setText(item);
                        type = item;
                    }
                });



                //離開
                mBuilder.setNegativeButton(R.string.dismiss, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                //清除
                mBuilder.setNeutralButton(R.string.clear, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            //mItemSelected.setText("");
                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });


        safinal = FirebaseFirestore.getInstance();
        send=(Button) findViewById(R.id.send);
        editNameText=(EditText)findViewById(R.id.editNameText);
        organizers = (EditText) findViewById(R.id.organizer);
        contentbox=(EditText)findViewById(R.id.contentbox);
        yearnumber=(EditText)findViewById(R.id.yearnumber);
        monthnum=(EditText)findViewById(R.id.monthnum);
        datenum=(EditText)findViewById(R.id.datenum);
        time=(EditText)findViewById(R.id.timehour);
        location=(EditText)findViewById(R.id.location);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String activity_Name=editNameText.getText().toString();
                String activities_content=contentbox.getText().toString();
                String organizer=organizers.getText().toString();
                String activity_type=type;
                String activity_time_year=yearnumber.getText().toString();
                String activity_time_month=monthnum.getText().toString();
                String activity_time_date=datenum.getText().toString();
                String activity_time=time.getText().toString();
                String activity_location=location.getText().toString();
                Map<String,String> userMap=new HashMap<>();
                userMap.put("activity_name",activity_Name);
                userMap.put("activity_type_id",activity_type);
                userMap.put("organizer",organizer);
                userMap.put("activities_content",activities_content);
                userMap.put("activity_time_year",activity_time_year);
                userMap.put("activity_time_month",activity_time_month);
                userMap.put("activity_time_date",activity_time_date);
                userMap.put("activity_time",activity_time);
                userMap.put("activity_location",activity_location);
                safinal.collection("Users").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Toast.makeText(this,"saved",Toast.LENGTH_SHORT).show();

                        safinal.collection("Draft").document(user_id)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error deleting document", e);
                                    }
                                });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error =e.getMessage();
                        //Toast.makeText(this,"error:"+error,Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });



        final EditText name_editText = (EditText) findViewById(R.id.editNameText);
        final EditText content_editText = (EditText) findViewById(R.id.contentbox);

        final EditText location_editText = (EditText) findViewById(R.id.location);
        final EditText year_editText = (EditText) findViewById(R.id.yearnumber);
        final EditText month_editText = (EditText) findViewById(R.id.monthnum);
        final EditText date_editText = (EditText) findViewById(R.id.datenum);
        final EditText time_editText = (EditText) findViewById(R.id.timehour);
        final EditText organizer_editText = (EditText) findViewById(R.id.organizer);
        final TextView type_editText = (TextView) findViewById(R.id.checkboxSelected);
        safinal.collection("Draft").document(user_id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                String name = documentSnapshot.getString("activity_name");
                String content = documentSnapshot.getString("activities_content");

                String location = documentSnapshot.getString("activity_location");
                String year = documentSnapshot.getString("activity_time_year");
                String month = documentSnapshot.getString("activity_time_month");
                String date = documentSnapshot.getString("activity_time_date");
                String time = documentSnapshot.getString("activity_time");
                String organizer = documentSnapshot.getString("organizer");
                String type = documentSnapshot.getString("activity_type_id");

                name_editText.setText(name);
                content_editText.setText(content);
                location_editText.setText(location);
                year_editText.setText(year);
                month_editText.setText(month);
                date_editText.setText(date);
                time_editText.setText(time);
                organizer_editText.setText(organizer);
                type_editText.setText(type);




            }
        });



    }
}
