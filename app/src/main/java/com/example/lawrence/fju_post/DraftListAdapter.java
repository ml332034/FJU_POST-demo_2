package com.example.lawrence.fju_post;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DraftListAdapter extends RecyclerView.Adapter<DraftListAdapter.ViewHloder> {

    public List<Post> draftList;
    public Context context;
    public DraftListAdapter(List<Post> draftList){
        this.draftList = draftList;
    }

    @NonNull
    @Override
    public ViewHloder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.draft_list_item, parent ,false);
        return new ViewHloder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHloder holder, final int position) {

        holder.nameText.setText(draftList.get(position).getActivity_name());
        holder.statusText.setText(draftList.get(position).getActivities_content());


        final String user_id =draftList.get(position).userId;
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, DraftEditActivity.class);
                intent.putExtra("user_id", user_id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return draftList.size();
    }

    public class ViewHloder extends RecyclerView.ViewHolder{

        View mView;

        public TextView nameText;
        public TextView statusText;


        public ViewHloder(View itemView) {
            super(itemView);
            mView = itemView;

            nameText = (TextView) mView.findViewById(R.id.name_text);
            statusText = (TextView) mView.findViewById(R.id.status_text);

        }

    }
}
