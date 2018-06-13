package com.example.lawrence.fju_post;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder>{

    public List<Users> usersList;
    public Context context;
    public UsersListAdapter(Context context,List<Users> usersList){
        this.usersList = usersList;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_listview_item,parent,false);

        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {

        holder.nameText.setText(usersList.get(position).getName());
        holder.statusText.setText(usersList.get(position).getStatus());
        final String user_id = usersList.get(position).userId;
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



               Toast.makeText(context, "User ID"+user_id, Toast.LENGTH_SHORT).show();



            }
        });
    }

    @Override
    public int getItemCount() {
        return  usersList.size();
    }

    public class  ViewHolder extends  RecyclerView.ViewHolder{

        View mView;

        public TextView nameText;
        public TextView statusText;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            nameText = (TextView) mView.findViewById(R.id.name_text);
            statusText = (TextView)mView.findViewById(R.id.status_text);
        }
    }
}
