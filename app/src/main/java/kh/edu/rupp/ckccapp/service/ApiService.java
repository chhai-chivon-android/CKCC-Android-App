package kh.edu.rupp.ckccapp.service;

import java.util.Map;

import kh.edu.rupp.ckccapp.model.LoginRequest;
import kh.edu.rupp.ckccapp.model.LoginResponse;
import kh.edu.rupp.ckccapp.utils.Constants;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

/**
 * Copyright (c) PRASAC MFI, Ltd. All rights reserved. (https://www.prasac.com.kh/)
 * Author	: Chhai Chivon (chivon.chhai@prasac.com.kh) on 2/10/2021 11:30 PM.
 * Position : Senior Application Development Officer
 */
public interface ApiService {

    @FormUrlEncoded
    @POST(Constants.LOGIN_URL)
    @Headers( { "Content-Type: application/json;charset=UTF-8","Language:km","Device:ANDROID","LoanId:0"})
    Call<LoginResponse> login(@FieldMap Map<String, String> login);
}
