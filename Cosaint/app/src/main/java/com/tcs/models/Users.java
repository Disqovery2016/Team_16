package com.tcs.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Harsh on 11/5/2016.
 */
@IgnoreExtraProperties
public class Users {
    public String name;
    public String email;
    public String mobile;


    public Users(){

    }

    public Users(String name, String email, String mobile){
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }
}
