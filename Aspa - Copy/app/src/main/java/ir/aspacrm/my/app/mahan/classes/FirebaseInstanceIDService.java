package ir.aspacrm.my.app.mahan.classes;

/**
 * Created by HaMiD on 1/15/2017.
 */


import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by Firoozian on 1/14/2017.
 */


public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = FirebaseInstanceIDService.class.getSimpleName();
    SharedPreferences pref;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

    }

}