package kh.edu.rupp.ckccapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import kh.edu.rupp.ckccapp.R;
import kh.edu.rupp.ckccapp.model.App;

/**
 * CKCCApp
 * Created by leapkh on 10/24/17.
 */

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);


        // Show profile image from server
        //String profileImageUrl = "http://10.0.2.2/file/image/profile.jpg";
        String profileImageUrl = "http://test.js-cambodia.com/ckcc/image/profile.png";
        ImageLoader imageLoader = App.getInstance(getActivity()).getImageLoader();
        NetworkImageView imgProfile = (NetworkImageView) rootView.findViewById(R.id.img_profile);
        imgProfile.setDefaultImageResId(R.drawable.ic_profile);
        imgProfile.setErrorImageResId(R.drawable.ic_error_image);
        imgProfile.setImageUrl(profileImageUrl, imageLoader);


        return rootView;
    }
}
