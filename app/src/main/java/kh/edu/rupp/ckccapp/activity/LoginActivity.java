package kh.edu.rupp.ckccapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import kh.edu.rupp.ckccapp.R;
import kh.edu.rupp.ckccapp.model.App;
import kh.edu.rupp.ckccapp.model.UserResponse;

public class LoginActivity extends Activity {

    private Button btnLogin;
    private ProgressBar pgrLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button)findViewById(R.id.btn_login);
        pgrLoading = (ProgressBar)findViewById(R.id.pgr_loading);

    }

    public void onLoginButtonClick(View view) {

        btnLogin.setVisibility(View.GONE);
        pgrLoading.setVisibility(View.VISIBLE);

        EditText etxtUsername = (EditText) findViewById(R.id.etxt_username);
        EditText etxtPassword = (EditText) findViewById(R.id.etxt_password);

        final String inputUsername = etxtUsername.getText().toString();
        final String inputPassword = etxtPassword.getText().toString();

        // Send username and password to Web Service
        //String loginUrl = "http://10.0.2.2/test/ckcc-api/login.php";
        String loginUrl = "http://test.js-cambodia.com/ckcc/login.php";
        StringRequest request = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                pgrLoading.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);

                Gson gson = new Gson();
                UserResponse userResponse = gson.fromJson(response, UserResponse.class);
                if (userResponse.code < 0) {
                    // Notify user for logining fail
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
                    alertDialog.setTitle("Login Fail");
                    alertDialog.setMessage(userResponse.message);
                    alertDialog.setPositiveButton("OK", null);
                    alertDialog.show();
                } else {
                    // Start main activity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", inputUsername);
                params.put("password", inputPassword);
                return params;
            }
        };
        App.getInstance(this).addRequest(request);
    }

}
