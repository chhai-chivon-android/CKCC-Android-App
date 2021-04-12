package kh.edu.rupp.ckccapp.service;

import java.io.IOException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import kh.edu.rupp.ckccapp.utils.Constants;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyright (c) PRASAC MFI, Ltd. All rights reserved. (https://www.prasac.com.kh/)
 * Author	: Chhai Chivon (chivon.chhai@prasac.com.kh) on 2/10/2021 11:31 PM.
 * Position : Senior Application Development Officer
 */
public class ApiClient {

    private static ApiService apiService = null;

    public ApiService getApiService(){
        if(apiService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    //.baseUrl(Constants.BASE_URL)
                    .baseUrl("https://jsonplaceholder.typicode.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getUnsafeOkHttpClient().build())
                    .build();

            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    public static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain
                            .request()
                            .newBuilder()
                            .addHeader("Content-Type", "application/json;charset=UTF-8")
                            .addHeader("Language", "km")
                            .addHeader("Device", "ANDROID")
                            .addHeader("LoanId", "0")
                            .addHeader("Authorization","Basic TE9BX0NMSUVOVF9JRDpMT0FfQ0xJRU5UX1NFQ1JFVA==")
                            .build();
                    return chain.proceed(request);
                }
            });

            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
