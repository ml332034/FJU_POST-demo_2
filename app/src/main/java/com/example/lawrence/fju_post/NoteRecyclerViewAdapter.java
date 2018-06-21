package com.example.lawrence.fju_post;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder> {

    private List<Note> notesList;
    private Context context;
    private FirebaseFirestore safinal;



    public NoteRecyclerViewAdapter(List<Note> list, Context ctx, FirebaseFirestore firestoreDB) {
        this.notesList = list;
        this.context = ctx;
        this.safinal = firestoreDB;
    }
    @Override
    public int getItemCount() {
        return notesList.size();
    }
    //Context是一個用于訪問全局信息的接口，如應用程序的資源（如图片，字符串等），一些常用的组件繼承自Context，如Activity和Service等
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        ViewHolder viewHolder =
                new ViewHolder(view);

        return new ViewHolder(view);
    }

    @Override//物件顯示於畫面時被調用，可利用此方法更新該物件之內容。
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int itemPosition = position;
        final Note note = notesList.get(position);//itemPosition

        holder.todolistname.setText(note.getTodolistname());
        holder.date.setText(note.getDate());
        holder.time.setText(note.getTime());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNote(note);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote(note.getId(), itemPosition);
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView todolistname, date,time;
        ImageView edit;
        ImageView delete;

        ViewHolder(View view) {
            super(view);
            todolistname = view.findViewById(R.id.tvTitle);
            date = view.findViewById(R.id.todolistdate);
            time = view.findViewById(R.id.todolisttime);


            edit = view.findViewById(R.id.ivEdit);
            delete = view.findViewById(R.id.ivDelete);
        }
    }

    private void updateNote(Note note) {
        Intent intent = new Intent(context, AddToDoListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("UpdateNoteId", note.getId());
        intent.putExtra("UpdateNoteTitle", note.getTodolistname());
        intent.putExtra("UpdateNoteDate", note.getDate());
        intent.putExtra("UpdateNoteTime", note.getTime());
        context.startActivity(intent);
    }
// 刪除代辦事項
    private void deleteNote(String id, final int position) {
        safinal.collection("ToDoList")
                .document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        notesList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, notesList.size());
                        Toast.makeText(context, "Note has been deleted!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
