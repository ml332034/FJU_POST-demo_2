package com.example.lawrence.fju_post;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class newnoticeFragment extends Fragment {
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
    Button mOrder;
    TextView mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    String type = "";



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newnotice, container, false);


        mOrder = (Button) view.findViewById(R.id.button6);//
        mItemSelected = (TextView) view.findViewById(R.id.checkboxSelected);
        listItems = getResources().getStringArray(R.array.shopping_item);
        checkedItems = new boolean[listItems.length];

        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(newnoticeFragment.super.getContext());
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
        send=(Button) view.findViewById(R.id.send);
        editNameText=(EditText)view.findViewById(R.id.editNameText);
        organizers = (EditText) view.findViewById(R.id.organizer);
        contentbox=(EditText)view.findViewById(R.id.contentbox);
        yearnumber=(EditText)view.findViewById(R.id.yearnumber);
        monthnum=(EditText)view.findViewById(R.id.monthnum);
        datenum=(EditText)view.findViewById(R.id.datenum);
        time=(EditText)view.findViewById(R.id.timehour);
        location=(EditText)view.findViewById(R.id.location);
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
                        Toast.makeText(getActivity(),"saved",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error =e.getMessage();
                        Toast.makeText(getActivity(),"error:"+error,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        return view;

    }




}