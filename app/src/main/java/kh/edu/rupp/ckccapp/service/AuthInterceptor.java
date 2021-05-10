package kh.edu.rupp.ckccapp.service;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Copyright (c) PRASAC MFI, Ltd. All rights reserved. (https://www.prasac.com.kh/)
 * Author	: Chhai Chivon (chivon.chhai@prasac.com.kh) on 4/13/21.
 * Position : Senior Application Development Officer
 */
class AuthInterceptor implements Interceptor {

    private Context context;

    public AuthInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        SessionManager sessionManager = new SessionManager(context);
        Request.Builder requestBuilder = chain.request().newBuilder();

        // If token has been saved, add it to the request
        if(!sessionManager.fetchAuthToken().isEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer "+sessionManager.fetchAuthToken());
        }
        requestBuilder.addHeader("Content-Type","application/json");

        return chain.proceed(requestBuilder.build());
    }
}