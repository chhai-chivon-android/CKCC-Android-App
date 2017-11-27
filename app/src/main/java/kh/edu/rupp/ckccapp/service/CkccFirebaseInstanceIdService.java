package kh.edu.rupp.ckccapp.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * CKCCApp
 * Created by leapkh on 11/21/17.
 */

public class CkccFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        Log.d("ckcc", "onTokenRefresh: " + FirebaseInstanceId.getInstance().getToken());
    }
}
