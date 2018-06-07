package com.example.lawrence.fju_post;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentToDolist extends Fragment {
    private ListView lv;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> list;
    Button mOrder;
    TextView mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    View view;

    public FragmentToDolist() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_todolist,container,false);

        lv = (ListView) view.findViewById(R.id.listtodolist);    //实例化


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.addtodolistbutton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),AddToDoListActivity.class));

            }
        });


        return view;
    }
    //活動list
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new SimpleAdapter(getActivity(), getData(), R.layout.tab_todolist_item,
                new String[]{"img", "title", "date","time"},
                new int[]{R.id.itemimage, R.id.todolisttitle, R.id.todolistdate,R.id.todolisttime});      //配置适配器，并获取对应Item中的ID
        lv.setAdapter(adapter);
    }
    //数据的获取@！
    private List<? extends Map<String, ?>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

//将需要的值传入map中
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", " SA 報告 ");
        map.put("date", "2018 / 6 / 8");
        map.put("time", "9 : 00");
        map.put("img", R.drawable.memo);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", " 西洋美術巡禮小組報告 ");
        map.put("date", "2018 / 6 / 9");
        map.put("time", "23 : 55");
        map.put("img", R.drawable.memo);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", " 健康促進活動 ");
        map.put("date", "2018 / 6 / 10");
        map.put("time", "12 : 00");
        map.put("img", R.drawable.memo);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", " 職涯發展演講 ");
        map.put("date", "2018 / 6 / 22");
        map.put("time", "12 : 40");
        map.put("img", R.drawable.memo);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", " 找主任喝茶QQ ");
        map.put("date", "2018 / 6 / 23");
        map.put("time", "12 : 00");
        map.put("img", R.drawable.memo);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", " 填教學評量 ");
        map.put("date", "2018 / 6 / 30");
        map.put("time", "23 : 00");
        map.put("img", R.drawable.memo);
        list.add(map);



        return list;
    }


}
