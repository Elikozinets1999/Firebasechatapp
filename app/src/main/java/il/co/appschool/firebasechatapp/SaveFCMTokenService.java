package il.co.appschool.firebasechatapp;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    private void sendRegistrationToServer(final String token) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference fcmDatabaseRef = mDatabase.child("FCM_Device_Tokens").push();

        FCM_Device_Tokens obj = new FCM_Device_Tokens();
        obj.setToken(token);
        fcmDatabaseRef.setValue(obj);
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
