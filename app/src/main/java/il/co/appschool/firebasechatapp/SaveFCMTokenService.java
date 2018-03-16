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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by elili on 3/14/2018.
 */

public class SaveFCMTokenService extends Service {

    public static final MediaType JSON =
            MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
     void post(String url, String json) throws IOException{
         RequestBody requestBody = RequestBody.create(JSON, json);
         final Request request = new Request.Builder()
                 .url(url)
                 .post(requestBody)
                 .build();
         Call call = client.newCall(request);
         call.enqueue(new Callback() {
             @Override
             public void onFailure(Call call, IOException e) {
                 Log.e("ERROR", "Failed receiving data");
             }

             @Override
             public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful())
                    Log.d("TOKEN", "Data sent successfully");
             }
         });
     }

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
        Log.d("TOKEN", "Sending token...");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("FCM_Device_Tokens");
        FCM_Device_Tokens fcm_device_tokens = new FCM_Device_Tokens();
        fcm_device_tokens.setToken(token);
        myRef.setValue(token);
        myRef.push();
        JSONObject jsonObject = new JSONObject();
//        String[] names = FirebaseAuth.getInstance().getCurrentUser().getDisplayName().split(" ");
        try {
            jsonObject.put("token", token);
//            jsonObject.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
            /*jsonObject.put("username", names[0]);
            jsonObject.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
            jsonObject.put("firstname", names[1]);
            jsonObject.put("lastname", names[2]);*/
            post("https://sleepy-springs-37359.herokuapp.com/fcm/regItem",jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
