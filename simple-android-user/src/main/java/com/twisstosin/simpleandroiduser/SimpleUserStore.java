package com.twisstosin.simpleandroiduser;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by twisstosin on 2/10/2017.
 */

public class SimpleUserStore {

    private String USER_OBJECT = ".USER";
    private String SETTINGS_OBJECT = ".SETTINGD";
    private String USER_LOGGEDIN = ".LOGGEDIN";
    private String USER_FINISHED_INTRO = ".USER_INTRO";
    private String USER_HINTS = ".USER_HINTS";

    private static final String SP_NAME = "userDetails";
    private SharedPreferences userLocalDatabase;
    private Class<?> userClass;


    //Constructor
    public SimpleUserStore(Context context, Class<?> userClass) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME,0);
        this.userClass = userClass;

        USER_OBJECT = context.getApplicationContext().getPackageName()+USER_OBJECT;
        USER_LOGGEDIN = context.getApplicationContext().getPackageName()+USER_LOGGEDIN;
        USER_FINISHED_INTRO = context.getApplicationContext().getPackageName()+USER_FINISHED_INTRO;
        USER_HINTS = context.getApplicationContext().getPackageName()+USER_HINTS;
        SETTINGS_OBJECT = context.getApplicationContext().getPackageName()+SETTINGS_OBJECT;
    }



    //To Store A User On the phone Shared Preferences.
    //Returns true if action was successful.

    public Boolean storeUser(Object userObject)
    {
        SharedPreferences.Editor userEditor = userLocalDatabase.edit();

        Gson gson = new Gson();
        String gsonUser = gson.toJson(userObject);
        userEditor.putString(USER_OBJECT,gsonUser);

        Boolean checkPost = userEditor.commit();
        if(checkPost) {
            setUserLoggedin(true);
            setHints(true);
        }
        return checkPost;
    }

    //To store user and settings together.

    public Boolean storeUser(Object userObject, Object settingsObject)
    {
        SharedPreferences.Editor userEditor = userLocalDatabase.edit();

        Gson gson = new Gson();
        String gsonUser = gson.toJson(userObject);
        String gsonSettings = gson.toJson(settingsObject);
        userEditor.putString(USER_OBJECT,gsonUser);
        userEditor.putString(SETTINGS_OBJECT,gsonSettings);

        Boolean checkPost = userEditor.commit();
        if(checkPost) {
            setUserLoggedin(true);
            setHints(true);
        }
        return checkPost;
    }

    public Boolean storeSettings(Object userObject)
    {
        SharedPreferences.Editor userEditor = userLocalDatabase.edit();

        Gson gson = new Gson();
        String gsonSettings = gson.toJson(userObject);
        userEditor.putString(SETTINGS_OBJECT,gsonSettings);

        return userEditor.commit();
    }

    //If there is a saved user.
    //Returns the user object.

    public Object getLoggedInUser()
    {
        Gson gson = new Gson();

        String userGson = userLocalDatabase.getString(USER_OBJECT,null);

        return gson.fromJson(userGson,userClass);
    }


    //Set if Hints have been viewed by the user or not.

    public void setHints(boolean isViewed){
        SharedPreferences.Editor userEditor = userLocalDatabase.edit();
        userEditor.putBoolean(USER_HINTS, isViewed);
        userEditor.apply();
    }


    //Check if hints have been viewed by user or not.
    //Returns true if hints have been viewed.

    public boolean isHintsViewed()
    {
        return userLocalDatabase.getBoolean(USER_HINTS, false);
    }


    //Set if user has logged in to the app.
    //(Automatically Sets as true on User Stored).

    public void setUserLoggedin(boolean loggedin){
        SharedPreferences.Editor userEditor = userLocalDatabase.edit();
        userEditor.putBoolean(USER_LOGGEDIN, loggedin);
        userEditor.apply();
    }


    //Set if splash screen has been viewed or not.

    public void setSplashView(boolean isViewed){
        SharedPreferences.Editor userEditor = userLocalDatabase.edit();
        userEditor.putBoolean(USER_FINISHED_INTRO, isViewed);
        userEditor.apply();
    }


    //Get if splash screen has been viewed or not.
    //Returns true if viewed.

    public boolean getSplashView(){

        return userLocalDatabase.getBoolean(USER_FINISHED_INTRO, false);
    }


    //Checks if a user is logged in.
    //Returns true if user is logged in.

    public boolean isUserLoggedIn(){

        return userLocalDatabase.getBoolean(USER_LOGGEDIN, false);
    }


    //Removes logged in user.
    //Returns true if the operation was successful.

    public boolean clearUser() {
        SharedPreferences.Editor userEditor = userLocalDatabase.edit();
        userEditor.remove(USER_OBJECT);
        boolean hasUserBeenRemoved = userEditor.commit();
        setUserLoggedin(false);

        return hasUserBeenRemoved;
    }

}