package kh.edu.rupp.ckccapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import kh.edu.rupp.ckccapp.R;

/**
 * CKCCApp
 * Created by leapkh on 29/8/17.
 */

public class ProfileActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
    }

    public void onBackButtonClick(View view){
        finish();
    }

}
