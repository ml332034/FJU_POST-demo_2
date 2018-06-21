package com.example.lawrence.fju_post;

public class Post extends  UserId{
    String activity_ID,activity_name,user_ID,activity_type_id,
    organizer,activities_content,activity_time_year,activity_time_month,activity_time_date,activity_location,activity_time;

    public Post() {
    }

    public Post(String activity_ID, String activity_name, String user_ID, String activity_type_id, String organizer, String activities_content, String activity_time_year, String activity_time_month, String activity_time_date) {
        this.activity_ID = activity_ID;
        this.activity_name = activity_name;
        this.user_ID = user_ID;
        this.activity_type_id = activity_type_id;
        this.organizer = organizer;
        this.activities_content = activities_content;
        this.activity_time_year = activity_time_year;
        this.activity_time_month = activity_time_month;
        this.activity_time_date = activity_time_date;
        this.activity_location= activity_location;
        this.activity_time = activity_time;

    }

    public String getActivity_ID() {
        return activity_ID;
    }

    public void setActivity_ID(String activity_ID) {
        this.activity_ID = activity_ID;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }

    public String getActivity_type_id() {
        return activity_type_id;
    }

    public void setActivity_type_id(String activity_type_id) {
        this.activity_type_id = activity_type_id;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getActivities_content() {
        return activities_content;
    }

    public void setActivities_content(String activities_content) {
        this.activities_content = activities_content;
    }

    public String getActivity_time_year() {
        return activity_time_year;
    }

    public void setActivity_time_year(String activity_time_year) {
        this.activity_time_year = activity_time_year;
    }

    public String getActivity_time_month() {
        return activity_time_month;
    }

    public void setActivity_time_month(String activity_time_month) {
        this.activity_time_month = activity_time_month;
    }

    public String getActivity_time_date() {
        return activity_time_date;
    }

    public void setActivity_time_date(String activity_time_date) {
        this.activity_time_date = activity_time_date;
    }

    public String getActivity_time() {
        return activity_time;
    }

    public void setActivity_time(String activity_time) {
        this.activity_time_month = activity_time_month;
    }

    public String getActivity_location() {
        return activity_location;
    }

    public void setActivity_location(String activity_location) {
        this.activity_time_date = activity_time_date;
    }
}
