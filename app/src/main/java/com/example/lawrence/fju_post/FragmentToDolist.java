package com.example.lawrence.fju_post;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class FragmentToDolist extends Fragment {


    private List<Map<String, Object>> list;


    private RecyclerView recyclerView;
    private NoteRecyclerViewAdapter mAdapter;


    private static final String KEY_TITLE = " todolistname";
    private static final String KEY_Date = " date";
    private static final String KEY_Time = " time";


    private FirebaseFirestore safinal = FirebaseFirestore.getInstance();
    private DocumentReference noteRef = safinal.document("ToDoList/List");
    private CollectionReference notebookRef = safinal.collection("ToDoList");

    View view;

    public FragmentToDolist() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_todolist,container,false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.addtodolistbutton);
        recyclerView = view.findViewById(R.id.rvNoteList);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),AddToDoListActivity.class));

            }
        });

        safinal.collection("ToDoList")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Note> notesList = new ArrayList<>();

                            for (DocumentSnapshot doc : task.getResult()) {
                                Note note = doc.toObject(Note.class);
                                assert note != null;
                                note.setId(doc.getId());
                                notesList.add(note);


                            }

                            mAdapter = new NoteRecyclerViewAdapter(notesList, getContext(), safinal);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(mAdapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return view;
    }

}