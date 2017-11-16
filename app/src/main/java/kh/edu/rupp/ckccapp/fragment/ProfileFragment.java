package kh.edu.rupp.ckccapp.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;

import org.json.JSONException;
import org.json.JSONObject;

import kh.edu.rupp.ckccapp.R;
import kh.edu.rupp.ckccapp.activity.LoginHistoriesActivity;
import kh.edu.rupp.ckccapp.model.App;

/**
 * CKCCApp
 * Created by leapkh on 10/24/17.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView txtName;
    private NetworkImageView imgProfile;
    private TextView txtGender;
    private TextView txtEmail;
    private TextView txtBirthday;
    private TextView txtHometown;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        txtName = (TextView)rootView.findViewById(R.id.txt_name);
        imgProfile = (NetworkImageView)rootView.findViewById(R.id.img_profile);
        txtGender = (TextView)rootView.findViewById(R.id.txt_gender);
        txtEmail = (TextView)rootView.findViewById(R.id.txt_email);
        txtBirthday = (TextView)rootView.findViewById(R.id.txt_birthday);
        txtHometown = (TextView)rootView.findViewById(R.id.txt_hometown);

        // Load profile from Facebook
        Profile currentProfile = Profile.getCurrentProfile();
        txtName.setText(currentProfile.getName());
        Uri profileImageUri = currentProfile.getProfilePictureUri(180, 180);
        imgProfile.setImageUrl(profileImageUri.toString(), App.getInstance(getActivity()).getImageLoader());

        loadMoreUserInfo();

        Button btnLoginHistory = (Button) rootView.findViewById(R.id.btn_login_history);
        btnLoginHistory.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login_history) {
            startActivity(new Intent(getActivity(), LoginHistoriesActivity.class));
        }
    }

    private void loadMoreUserInfo(){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        if (response.getError() == null){
                            try {
                                String gender = object.getString("gender");
                                String email = object.getString("email");
                                String birthday = object.getString("birthday");
                                JSONObject hometownJson = object.getJSONObject("hometown");
                                String hometown = hometownJson.getString("name");
                                txtGender.setText(gender);
                                txtEmail.setText(email);
                                txtBirthday.setText(birthday);
                                txtHometown.setText(hometown);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error while loading more user info", Toast.LENGTH_LONG).show();
                            Log.d("ckcc", "Error: " + response.getError().getErrorMessage());
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "gender,email,birthday,hometown");
        request.setParameters(parameters);
        request.executeAsync();
    }

}
