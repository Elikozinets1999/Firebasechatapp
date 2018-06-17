package il.co.appschool.firebasechatapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SetUPActivity extends AppCompatActivity {
    public static final MediaType JSON =
            MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    // Sends Set-up data to DB server.
    void post(String url, String json) throws IOException {
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


    EditText etDisplayName, etFirstName, etLastName;
    Button btnSaveInfo;
    FirebaseAuth mAuth;
    public SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        mAuth = FirebaseAuth.getInstance();
        etDisplayName = findViewById(R.id.etDisplayname);
        etFirstName = findViewById(R.id.etFirstname);
        etLastName = findViewById(R.id.etLastname);
        btnSaveInfo = findViewById(R.id.btnSaveInfo);
        btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInformation();
                sendParameters();
                etDisplayName.setText("");
                etFirstName.setText("");
                etLastName.setText("");
            }
        });
    }
    // Saves user profile given to Firebase.
    private void saveUserInformation() {
        final String displayname = etDisplayName.getText().toString().trim();
        final String firstname = etFirstName.getText().toString().trim();
        final String lastname = etLastName.getText().toString().trim();
        if(displayname.isEmpty()){
            etDisplayName.setError("You must fill this field");
            etDisplayName.requestFocus();
            return;
        }
        if(firstname.isEmpty()){
            etFirstName.setError("You must fill this field");
            etDisplayName.requestFocus();
            return;
        }
        if(lastname.isEmpty()){
            etLastName.setError("You must fill this field");
            etLastName.requestFocus();
            return;
        }
        if(!checkNames(firstname, lastname)){
            etFirstName.setText("");
            etFirstName.setError("");
            etLastName.setText("");
            etLastName.setError("");
            Toast.makeText(SetUPActivity.this, "Invalid names", Toast.LENGTH_LONG).show();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy", Locale.ENGLISH);
        if(user != null){
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayname+" "+firstname+" "+lastname+" "+simpleDateFormat.format(date))
                    .build();
            Log.d("TAG", profileChangeRequest.getDisplayName());
            user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Profile updated", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SetUPActivity.this, ProfileActivity.class));
                    }
                }
            });
        }
    }
    // Calls for sending data given to server DB.
    private void sendParameters(){
        String displayName = etDisplayName.getText().toString();
        String fName = etFirstName.getText().toString();
        String lName = etLastName.getText().toString();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", FirebaseInstanceId.getInstance().getToken());
            jsonObject.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
            jsonObject.put("username", displayName);
            jsonObject.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
            jsonObject.put("firstname", fName);
            jsonObject.put("lastname", lName);
            Log.d("TOKEN", jsonObject.toString());
            post("https://sleepy-springs-37359.herokuapp.com/fcm/regItem",jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sp=getApplicationContext().getSharedPreferences("settings",0);
        String background = sp.getString("background", null);
        if (background != null) {
            getWindow().getDecorView().findViewById(android.R.id.content).setBackgroundColor(Color.parseColor(background));
        }
    }
    // Checks if conditions are answered for first name and last name.
    boolean checkNames(String name, String lName ){
        return name.length() >= 3 && lName.length() >= 3;
    }


}
