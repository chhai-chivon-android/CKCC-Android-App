package kh.edu.rupp.ckccapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import kh.edu.rupp.ckccapp.R;

public class LoginActivity extends Activity {

    private final String APP_USERNAME = "ckcc";
    private final String APP_PASSWORD = "123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onLoginButtonClick(View view){

        EditText etxtUsername = (EditText) findViewById(R.id.etxt_username);
        EditText etxtPassword = (EditText) findViewById(R.id.etxt_password);

        String inputUsername = etxtUsername.getText().toString();
        String inputPassword = etxtPassword.getText().toString();

        if (inputUsername.equals(APP_USERNAME) && inputPassword.equals(APP_PASSWORD)){
            // Start main activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Notify user for logining fail
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Login Fail");
            alertDialog.setMessage("Incorrect username or password");
            alertDialog.setPositiveButton("OK", null);
            alertDialog.show();
        }

    }

}
