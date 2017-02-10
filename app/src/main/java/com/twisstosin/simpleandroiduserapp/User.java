package com.twisstosin.simpleandroiduserapp;

import android.support.annotation.Nullable;

public class User {
    public String Id;
    public String Name;
    public String Email;
    public String Phone;
    public String Gender;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(@Nullable String id, String name, String email, String phone) {
        Name = name;
        Email = email;
        Id = id;
        Phone = phone;
    }
}
