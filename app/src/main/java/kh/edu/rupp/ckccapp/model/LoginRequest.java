package kh.edu.rupp.ckccapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright (c) PRASAC MFI, Ltd. All rights reserved. (https://www.prasac.com.kh/)
 * Author	: Chhai Chivon (chivon.chhai@prasac.com.kh) on 2/10/2021 11:21 PM.
 * Position : Senior Application Development Officer
 */
public class LoginRequest {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;


    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
