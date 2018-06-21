package com.example.lawrence.fju_post;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Map;

public class AddToDoListActivity extends AppCompatActivity {
    private static final String TAG = "AddToDoListActivity";

    private static final String KEY_TITLE = " todolistname";
    private static final String KEY_Date = " date";
    private static final String KEY_Time = " time";
    String id = "";

    private EditText editTodolist;
    String pickdate;
    String picktime;

    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    //timepicker
    private Button selectTime;

    TimePickerDialog timePickerDialog;

    private FirebaseFirestore safinal = FirebaseFirestore.getInstance();
    private DocumentReference noteRef = safinal.document("ToDoList/List");
    private CollectionReference notebookRef = safinal.collection("ToDoList");


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todolist);

        Button selectDate = findViewById(R.id.datepick);
        final TextView date = findViewById(R.id.tvSelectedDate);

        Button selectTime = findViewById(R.id.alarmpick);
        final TextView time = findViewById(R.id.tvSelectedTime);


        editTodolist = findViewById(R.id.todolisttext);

        Button check = findViewById(R.id.CheckAndAdd);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AddToDoListActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date.setText(year + " / " + (month + 1) + " / " + day);
                                pickdate = (year + " - " + (month + 1) + " - " + day);

                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });


        selectTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Use the current time as the default values for the picker
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                // Create a new instance of TimePickerDialog and return it
                new TimePickerDialog(AddToDoListActivity.this, new TimePickerDialog.OnTimeSetListener(){
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay + " : " + minute);
                        picktime = (hourOfDay + " : " + minute);
                    }
                }, hour, minute, false).show();

            }

        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todolistname = editTodolist.getText().toString();
                String date = pickdate;
                String time = picktime;
                if (todolistname.length() > 0) {
                    if (id.length() > 0) {
                        updateNote(id,todolistname, date,time);
                    } else {
                        saveNote(todolistname, date,time);
                    }
                }

                finish();
            }
        });


    }

    private void updateNote(String id, String todolistname, String date,String time) {
        Map<String, Object> note = (new Note(id, todolistname, date,time)).toMap();
        safinal.collection("ToDoList")
                .document(id)
                .set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "Note document update successful!");
                        Toast.makeText(getApplicationContext(), "Note has been updated!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error adding Note document", e);
                        Toast.makeText(getApplicationContext(), "Note could not be updated!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void saveNote(String todolistname, String date,String time) {
        Map<String, Object> note = new Note(todolistname, date,time).toMap();

        safinal.collection("ToDoList").add(note).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.e(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                Toast.makeText(AddToDoListActivity.this,"Todo List saved",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Error adding Note document", e);
                String error =e.getMessage();
                Toast.makeText(AddToDoListActivity.this,"Error:"+error,Toast.LENGTH_SHORT).show();
            }
        });

    }
}