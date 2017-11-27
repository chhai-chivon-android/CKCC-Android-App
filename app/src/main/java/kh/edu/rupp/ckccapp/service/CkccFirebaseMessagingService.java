package kh.edu.rupp.ckccapp.service;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * CKCCApp
 * Created by leapkh on 11/21/17.
 */

public class CkccFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d("ckcc", "onMessageReceived: " + remoteMessage.getData().toString());
    }

}
