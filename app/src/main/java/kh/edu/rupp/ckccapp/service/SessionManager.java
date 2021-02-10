package kh.edu.rupp.ckccapp.service;

import android.content.Context;
import android.content.SharedPreferences;

import kh.edu.rupp.ckccapp.R;

/**
 * Copyright (c) PRASAC MFI, Ltd. All rights reserved. (https://www.prasac.com.kh/)
 * Author	: Chhai Chivon (chivon.chhai@prasac.com.kh) on 2/10/2021 11:35 PM.
 * Position : Senior Application Development Officer
 */
public class SessionManager {

    private String USER_TOKEN = "user_token";
    private SharedPreferences prefs = null;

    public SessionManager(Context context){
        prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    /**
     * Function to save auth token
     */
    void saveAuthToken(String token) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_TOKEN, token);
        editor.apply();
    }

    /**
     * Function to fetch auth token
     */
    String fetchAuthToken(){
        return prefs.getString(USER_TOKEN, null);
    }
}
