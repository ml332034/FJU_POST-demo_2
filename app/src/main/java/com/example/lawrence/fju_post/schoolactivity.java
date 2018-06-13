package com.example.lawrence.fju_post;

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
    TextView mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

   //RECYCER VIEW
    private String TAG = "FireLog";
    private RecyclerView mMainList;
    private  FirebaseFirestore mFirestore;
    private UsersListAdapter usersListAdapter;
    private List<Users> usersList;
    // Click to activity
    public AdapterView.OnItemClickListener mListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_schoolactivity, container, false);


        //BUTTON
        mOrder = (Button) view.findViewById(R.id.btnorder);
        //調篩選可能用到 mItemSelected = (TextView) view.findViewById(R.id.tvItemSelected);
        listItems = getResources().getStringArray(R.array.shopping_item);
        checkedItems = new boolean[listItems.length];

        //RECYCER VIEW
        usersList = new ArrayList<>();
        usersListAdapter = new UsersListAdapter(getActivity().getApplicationContext(),usersList);
        mMainList =(RecyclerView) view.findViewById(R.id.tab_listview);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(getActivity()));//原本是this
        mMainList.setAdapter(usersListAdapter);

        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("Users").addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d(TAG, "Error:" + e.getMessage());
                }
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                    if(doc.getType()  == DocumentChange.Type.ADDED) {
                       String  user_Id = doc.getDocument().getId(); // 得到id

                       Users users = doc.getDocument().toObject(Users.class).withId(user_Id);//user_Id
                       usersList.add(users);

                       usersListAdapter.notifyDataSetChanged();
                    }
                }

            }
//////////////
            /*listview
            listView = (ListView)view.findViewById(R.id.tab_listview);//实例化
            new add

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_selectable_list_item);
              adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);*/
              });




                 /*.ItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?>  adapterView, View view, int pos, long id) {


                        Intent intent = new Intent(getActivity(), ShowPostActivity.class);//
                        intent.putExtra("Position", pos);

                        startActivity(intent);



                    }
                });*/


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
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) ;
                            {
                                item = item + ",";

                            }
                        }
                        mItemSelected.setText(item);
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
        return view;

    }


}
