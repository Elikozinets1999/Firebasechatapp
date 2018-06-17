package il.co.appschool.firebasechatapp;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.Reference;
import java.security.Provider;
import java.util.List;
import java.util.Map;


/**
 * Created by elili on 3/14/2018.
 */

public class SaveFCMTokenService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent != null){
            Bundle b = intent.getExtras();
            if(b != null){
                String token = b.getString("TOKEN");
                sendRegistrationToServer(token);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
    // Sends token to Firebase DB.
    private void sendRegistrationToServer(final String token) {
        FCM_Device_Tokens fcm_device_tokens = new FCM_Device_Tokens();
        fcm_device_tokens.setToken(token);
        FirebaseDatabase.getInstance().getReference().child(fcm_device_tokens.getToken()).setValue(fcm_device_tokens);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
