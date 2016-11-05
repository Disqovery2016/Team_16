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
    public String emergency_contact1;
    public String emergency_contact2;


    public Users(){
    }

    public Users(String name, String email, String mobile, String emergency_contact1, String emergency_contact2){
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.emergency_contact1 = emergency_contact1;
        this.emergency_contact2 = emergency_contact2;
    }
}
