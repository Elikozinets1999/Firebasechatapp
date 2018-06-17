package il.co.appschool.firebasechatapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by elili on 3/10/2018.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
 //   private SharedPreferences sharedPreferences;
 //   private SharedPreferences.Editor editor;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("TOKEN", token);
        Intent intent = new Intent(MyFirebaseInstanceIdService.this, SaveFCMTokenService.class);
        intent.putExtra("TOKEN", token);
        MyFirebaseInstanceIdService.this.startService(intent);
    }
}
