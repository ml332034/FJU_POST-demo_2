package com.example.lawrence.fju_post;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.support.constraint.Constraints.TAG;


public class MeFragment extends Fragment {
    private static  final String TAG = "FireLog";

    private FirebaseFirestore mFirestore;

    //userID
    private FirebaseAuth mAuth;
    private String user_ID;

    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        final TextView user_name = (TextView) view.findViewById(R.id.userName);
        final TextView user_email = (TextView) view.findViewById(R.id.userEmail);
        final ImageView user_image = (ImageView) view.findViewById(R.id.userPicture);



        mFirestore = FirebaseFirestore.getInstance();

        //get userID
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        user_ID = user.getUid();
        final String[] data = new String[3];

        mFirestore.collection("UserData").whereEqualTo("user_ID",user_ID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d(TAG,"Error" + e.getMessage());
                }
                for(DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){
                        data[0] = doc.getDocument().getString("name");
                        data[1] = doc.getDocument().getString("mail");
                        data[2] = doc.getDocument().getString("picture");

                        Log.d(TAG,"Name : " + data[0]);

                    }
                }
                user_name.setText(data[0]);
                user_email.setText(data[1]);
                Picasso.with(getActivity()).load(data[2]).into(user_image);
            }
        });







        Button nextPageBtn = (Button) view.findViewById(R.id.button3);
        Button nextPageBtn1 = (Button) view.findViewById(R.id.weather);
        Button nextPageBtn2 = (Button) view.findViewById(R.id.horoscope);
        Button nextPageBtn3 = (Button) view.findViewById(R.id.news);

        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),FragmentSetting.class));

            }
        });
        nextPageBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),WeatherActivity.class));

            }
        });
        nextPageBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),HoroscopeActivity.class));

            }
        });
        nextPageBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),NewsActivity.class));

            }
        });


        return view;


    }






}
