package kh.edu.rupp.ckccapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import kh.edu.rupp.ckccapp.R;
import kh.edu.rupp.ckccapp.model.App;

/**
 * CKCCApp
 * Created by leapkh on 29/8/17.
 */

public class ProfileActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        // Show profile image from server
        String profileImageUrl = "http://10.0.2.2/file/image/profile.jpg";
        ImageLoader imageLoader = App.getInstance(this).getImageLoader();
        NetworkImageView imgProfile = (NetworkImageView) findViewById(R.id.img_profile);
        imgProfile.setDefaultImageResId(R.drawable.ic_profile);
        imgProfile.setErrorImageResId(R.drawable.ic_error_image);
        imgProfile.setImageUrl(profileImageUrl, imageLoader);

    }

    public void onBackButtonClick(View view) {
        finish();
    }

}
