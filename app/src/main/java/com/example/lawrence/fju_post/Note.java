package com.example.lawrence.fju_post;

import java.util.HashMap;
import java.util.Map;

public class Note {
    private String id;
    private String todolistname;
    private String date;
    private String time;



    public Note() {
        //needed for firebase
    }

    //為了Update所需要的note
    public Note(String id, String todolistname, String date, String time) {
        this.id = id;
        this.todolistname = todolistname;
        this.date = date;
        this.time = time;
    }
    //為了添加進firestore所需要的note
    public Note(String todolistname, String date, String time) {
        this.todolistname = todolistname;
        this.date = date;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTodolistname() {
        return todolistname;
    }

    public void setTodolistname(String todolistname) {
        this.todolistname = todolistname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("todolistname", this.todolistname);
        result.put("date", this.date);
        result.put("time", this.time);

        return result;
    }
}
