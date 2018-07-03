package com.example.lawrence.fju_post;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class historyActivity extends android.support.v4.app.Fragment {

    private static final String TAG = "FireLog";
    private RecyclerView mMain_list;
    private FirebaseFirestore mFirestore;

    //userID
    private FirebaseAuth mAuth;
    private String user_ID;

    private List<Post> draftList;
    private HistoryListAdapter draftListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_history, container, false);

        draftList = new ArrayList<>();
        draftListAdapter = new HistoryListAdapter(draftList);

        mMain_list = (RecyclerView) view.findViewById(R.id.main_list);
        mMain_list.setHasFixedSize(true);
        mMain_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMain_list.setAdapter(draftListAdapter);
        mFirestore = FirebaseFirestore.getInstance();

        //get userID
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        user_ID = user.getUid();

        mFirestore.collection("Users").whereEqualTo("user_ID", user_ID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.d(TAG,"Error : " + e.getMessage());
                }
                for(DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED){

                        String user_id = doc.getDocument().getId();
                        Post users = doc.getDocument().toObject(Post.class).with(user_id);
                        draftList.add(users);
                        draftListAdapter.notifyDataSetChanged();

                        String name = doc.getDocument().getString("activity_Name");
                        Log.d(TAG,"Name : " + name);
                    }
                }
            }
        });
        return view;
    }
}
