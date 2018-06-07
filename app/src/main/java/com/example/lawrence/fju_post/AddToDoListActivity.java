package com.example.lawrence.fju_post;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class AddToDoListActivity extends AppCompatActivity {
    //datepicker
    private Button selectDate;
    private TextView date;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    //timepicker
    private Button selectTime;
    private TextView time;
    TimePickerDialog timePickerDialog;

    //add somthing to todolist
    private  Button addandcheck;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todolist);

        Button selectDate = findViewById(R.id.datepick);
        final TextView date = findViewById(R.id.tvSelectedDate);

        Button selectTime = findViewById(R.id.alarmpick);
        final TextView time = findViewById(R.id.tvSelectedTime);

        Button addandcheck = findViewById(R.id.CheckAndAdd);


        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AddToDoListActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date.setText(year + " / " + (month + 1) + " / " + day);
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

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay + " : " + minute);
                    }
                }, hour, minute, false).show();
            }

        });

    }
}