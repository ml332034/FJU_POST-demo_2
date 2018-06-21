package com.example.lawrence.fju_post;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShowPostAdapter extends RecyclerView.Adapter<ShowPostAdapter.ViewHolder> {//implements View.OnClickListener


    private List<Post> usersList;


    public Context context;
    public OnItemClickListener mOnItemClickListener = null;
    public ShowPostAdapter(Context context,List<Post> usersList){
        this.usersList = usersList;
        this.context = context;
    }


    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }


    public ShowPostAdapter (ArrayList<Post>  exampleList){ usersList = exampleList;}


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int viewType) {


        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tab_listview_item, viewGroup, false);
        final ViewHolder vh = new ViewHolder(view);
        //将创建的View注册点击事件
        /*vh.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注意这里使用getTag方法获取position
                Context context = v.getContext();
                Intent intent = new Intent(context, ShowPostActivity.class);

                context.startActivity(intent);
            }
        });*/

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,  int position) {
        viewHolder.nameText.setText(usersList.get(position).getActivity_name());
        viewHolder.statusText.setText(usersList.get(position).getActivity_type_id());

        final String user_id =usersList.get(position).userId;

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"User ID: " +user_id,Toast.LENGTH_SHORT).show();

                Context context = v.getContext();
                Intent intent = new Intent(context, ShowPostActivity.class);
                intent.putExtra("user_id", user_id);
                context.startActivity(intent);
            }
        });




    }
    /*将点击事件转移给外面的调用者：
    @Override
    public void onClick(View v) {
        //注意这里使用getTag方法获取position
        Context context = v.getContext();
        Intent intent = new Intent(context, ShowPostActivity.class);
        intent.putExtra("activity_name",getTag(usersList).getName());
        context.startActivity(intent);

    }*/

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = (OnItemClickListener) listener;
    }


    //获取数据的数量
    @Override
    public int getItemCount() {
        return usersList.size();

    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView nameText;
        TextView statusText;


        LinearLayout view_container;

        public ViewHolder(View view){
            super(view);
            mView = view;
            view_container = view.findViewById(R.id.container);
            nameText = (TextView) mView.findViewById(R.id.name_text);
            statusText = (TextView)mView.findViewById(R.id.status_text);


        }
    }


    public void filterList(ArrayList<Post> filteredList){
        usersList =  filteredList;
        notifyDataSetChanged();
    }


}
