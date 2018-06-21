package com.example.lawrence.fju_post;

import android.support.annotation.NonNull;

public class UserId {

    public  String userId;
    public  <T extends  UserId> T with(@NonNull final  String id){
        this.userId =  id;
        return (T) this;
    }
}
