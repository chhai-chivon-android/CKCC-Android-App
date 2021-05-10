package kh.edu.rupp.ckccapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kh.edu.rupp.ckccapp.R;
import kh.edu.rupp.ckccapp.model.App;
import kh.edu.rupp.ckccapp.model.DbManager;
import kh.edu.rupp.ckccapp.model.LoginHistory;
import kh.edu.rupp.ckccapp.model.LoginRequest;
import kh.edu.rupp.ckccapp.model.LoginResponse;
import kh.edu.rupp.ckccapp.model.Photo;
import kh.edu.rupp.ckccapp.model.UserResponse;
import kh.edu.rupp.ckccapp.service.ApiClient;
import kh.edu.rupp.ckccapp.service.SessionManager;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity implements FacebookCallback<LoginResult>, OnCompleteListener<AuthResult> {

    private LoginButton btnFacebookLogin;
    private Button btnLogin;
    private ProgressBar pgrLoading;

    private CallbackManager callbackManager;

    private FirebaseAuth firebaseAuth;

    private SessionManager sessionManager;

    EditText etxtUsername;
    EditText etxtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this);

        // Check if user is already logged in
//        if(FirebaseAuth.getInstance().getCurrentUser() != null){
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }

        setContentView(R.layout.activity_login);

         etxtUsername = (EditText) findViewById(R.id.etxt_username);
         etxtPassword = (EditText) findViewById(R.id.etxt_password);

        btnLogin = (Button) findViewById(R.id.btn_login);
        pgrLoading = (ProgressBar) findViewById(R.id.pgr_loading);

        // Facebook authentication
        btnFacebookLogin = (LoginButton)findViewById(R.id.btn_facebook_login);
        btnFacebookLogin.setReadPermissions("email", "user_birthday", "user_hometown");

        callbackManager = CallbackManager.Factory.create();
        btnFacebookLogin.registerCallback(callbackManager, this);

        // Firebase authentication
        firebaseAuth = FirebaseAuth.getInstance();

//        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();

        ApiClient apiClient = new ApiClient();

        apiClient.getApiService(this).loadPhotoFromServer().enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                Log.d("Response ", response + "");
                Log.d("message ", response.message() + "");
                Log.d("body ", response.body().toString()+ "");
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                t.printStackTrace();
                Log.d(" Error ", t.getLocalizedMessage());
            }
        });

        sessionManager = new SessionManager(this);
        etxtUsername.setText("chivon.chhai");
        etxtPassword.setText("P@ssw0rd");

        btnLogin.setOnClickListener(v -> {
            final String inputUsername = etxtUsername.getText().toString();
            final String inputPassword = etxtPassword.getText().toString();
            Log.d("inputUsername ", inputUsername);
            Log.d("inputPassword ", inputPassword);
            LoginRequest loginRequest = new LoginRequest(inputUsername,inputPassword);



            String authorization = "Basic TE9BX0NMSUVOVF9JRDpMT0FfQ0xJRU5UX1NFQ1JFVA==";
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("grant_type", "password")
                    .addFormDataPart("username", inputUsername)
                    .addFormDataPart("password", inputPassword)
                    .addFormDataPart("client_id", "LOA_CLIENT_ID")
                    .addFormDataPart("client_secret", "LOA_CLIENT_SECRET")
                    .addFormDataPart("latitude", "11.5657889")
                    .addFormDataPart("longitude", "104.9202214")
                    .addFormDataPart("device", "ANDROID")
                    .addFormDataPart("deviceName", "iPhone")
                    .addFormDataPart("macAddress", "AC-D5-64-BF-CD-31")
                    .addFormDataPart("isForceLogin", "true")
                    .build();

            Map<String, String> login = new HashMap<>();
            login.put("grant_type", "password");
            login.put("username", inputUsername);
            login.put("password", inputPassword);
            login.put("client_id", "LOA_CLIENT_ID");
            login.put("client_secret", "LOA_CLIENT_SECRET");
            login.put("latitude", "11.5657889");
            login.put("longitude", "104.9202214");
            login.put("device", "ANDROID");
            login.put("deviceName", "iPhone");
            login.put("macAddress", "AC-D5-64-BF-CD-31");
            login.put("isForceLogin", "true");


            apiClient.getApiService(this).login("password",
                    inputUsername,
                    inputPassword,
                    "LOA_CLIENT_ID",
                    "LOA_CLIENT_SECRET",
                    "11.5657889",
                    "104.9202214",
                    "ANDROID",
                    "iPhone",
                    "AC-D5-64-BF-CD-31",
                    "true"
                    ).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                    Log.d("Response ", response + "");
                    Log.d("message ", response.message() + "");
                    Log.d("body ", response.body() + "");
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    t.printStackTrace();
                    Log.d(" Error ", t.getLocalizedMessage());
                }
            });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onLoginButtonClick(View view) {

        btnLogin.setVisibility(View.GONE);
        pgrLoading.setVisibility(View.VISIBLE);






        // Send username and password to Web Service
        //String loginUrl = "http://10.0.2.2/test/ckcc-api/login.php";
//        String loginUrl = "http://test.js-cambodia.com/ckcc/login.php";
//        StringRequest request = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                pgrLoading.setVisibility(View.GONE);
//                btnLogin.setVisibility(View.VISIBLE);
//
//                Gson gson = new Gson();
//                //UserResponse userResponse = gson.fromJson(response, UserResponse.class);
//                //if (userResponse.code < 0) {
//                if (1 < 0) {
//                    // Notify user for logining fail
//                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
//                    alertDialog.setTitle("Login Fail");
//                    //alertDialog.setMessage(userResponse.message);
//                    alertDialog.setPositiveButton("OK", null);
//                    alertDialog.show();
//                } else {
//                    // Start main activity
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//
//                // Insert login history
//                long currentTime = System.currentTimeMillis();
//                //boolean isSuccess = (userResponse.code == 0);
//                //LoginHistory history = new LoginHistory(0, inputUsername, currentTime, isSuccess);
//
//                //DbManager dbManager = new DbManager(LoginActivity.this);
//                //dbManager.insertLoginHistory(history);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("username", inputUsername);
//                params.put("password", inputPassword);
//                return params;
//            }
//        };
//        App.getInstance(this).addRequest(request);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        // Pass Facebook token to Firebase auth object
        AuthCredential authCredential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this);
    }

    @Override
    public void onCancel() {
        Toast.makeText(this, "Login cancel", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(FacebookException error) {
        Toast.makeText(this, "Login error", Toast.LENGTH_LONG).show();
        Log.d("ckcc", "Facebook login error: " + error.getMessage());
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Login error", Toast.LENGTH_LONG).show();
            Log.d("ckcc", "Firebaase auth error: " + task.getException());
        }
    }
}
