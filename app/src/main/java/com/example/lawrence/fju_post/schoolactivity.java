package com.example.lawrence.fju_post;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class schoolactivity extends Fragment {

    //篩選器
    Button mOrder;
    TextView mItemSelected, mgetname;

    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

   //RECYCER VIEW
    private String TAG = "FireLog";
    private RecyclerView mMainList;
    private  FirebaseFirestore mFirestore;
    private  ShowPostAdapter  usersListAdapter;
    private ArrayList<Post> usersList;


    // Click to activity
    public AdapterView.OnItemClickListener mListener;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_tab_schoolactivity, container, false);



        //BUTTON
        mOrder = (Button) view.findViewById(R.id.btnorder);
        listItems = getResources().getStringArray(R.array.shopping_item);
        checkedItems = new boolean[listItems.length];

        //RECYCER VIEW
        usersList = new ArrayList<>();
        usersListAdapter = new ShowPostAdapter(getActivity(),usersList);
        mMainList =(RecyclerView) view.findViewById(R.id.tab_listview);
        mMainList.setHasFixedSize(true);//如果可以确定每个item的高度是固定的，设置这个选项可以提高性能


        mMainList.setLayoutManager(new LinearLayoutManager(getActivity()));//创建默认的线性LayoutManager
        mMainList.setAdapter(usersListAdapter);


        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("Users").addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) { //QuerySnapshot查詢的結果 //透過addSnapshotListene獲取數據
                if (e != null) {
                    Log.d(TAG, "Error:" + e.getMessage());
                }
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {//對匹配查詢的文檔的更改
                    if(doc.getType()  == DocumentChange.Type.ADDED) {

                        String user_id = doc.getDocument().getId();
                        Post users = doc.getDocument().toObject(Post.class).with(user_id); //取得 User 物件
                        usersList.add(users);
                        usersListAdapter.notifyDataSetChanged();


                    }
                }

            }

              });






        //篩選button設定
        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(schoolactivity.super.getContext());
                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    //類別點選
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked) {
                            if (!mUserItems.contains(position)) {
                                mUserItems.add(position);
                            } else if (!mUserItems.contains(position)) {
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
                        String item ="";

                        for (int i = 0; i < mUserItems.size(); i++) {
                            item = item + listItems[mUserItems.get(i)];

                                filter(item);

                            if (i != mUserItems.size() - 1) ;
                            {
                                item = item+ ",";

                            }
                        }

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

                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
        return view;





    }

    private void filter(String text) {
        ArrayList<Post> filteredList = new ArrayList<>();
        for (Post item: usersList) {


                    if (item.getActivity_type_id()!=null && item.getActivity_type_id().contains(text)) {

                        filteredList.add(item);

                    }


        if (item.getActivity_type_id()!=null && text.contains(item.getActivity_type_id())) {

            filteredList.add(item);
            if(item.getActivity_type_id().contains(text)){
                filteredList.add(item);
            }

            }


        }

                usersListAdapter.filterList(filteredList);


    }

}
