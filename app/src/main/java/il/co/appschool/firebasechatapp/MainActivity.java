package il.co.appschool.firebasechatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    Button BtnShaked, BtnIlan;
    public static final MediaType JSON =
            MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    // Sends Login perimeters to server DB once login has started.
    void post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    Log.d("Message", "Credinals sent successfully");
                } else {
                    Log.e("Message","Error with credinals");
                }
            }
        });
    }



    private EditText etLoginEmail, etLoginPassword;
    private Button btnLogin, btnGoToReg;
    FirebaseAuth mAuth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BtnShaked = findViewById(R.id.btn_Shaked);
        BtnIlan = findViewById(R.id.btn_Ilan);

        BtnShaked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MainShaked.class);
                startActivity(i);
            }
        });
        BtnIlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MainIlan.class);
                startActivity(i);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        progressBar = findViewById(R.id.LoginProgress);
        progressBar.setVisibility(View.INVISIBLE);
        btnLogin = findViewById(R.id.btn_Login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLogin();
            }
        });
        btnGoToReg = findViewById(R.id.btnMoveToReg);
        btnGoToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
    }
    // Calls for 'post' method to send given perimeters.
    private void sendCredinals(String token, String email) {
        JSONObject jsonObject = new JSONObject();
        try {
            String[] names = mAuth.getCurrentUser().getDisplayName().split(" ");
            jsonObject.put("token", token);
            jsonObject.put("email", email);
            jsonObject.put("firstname", names[1]);
            jsonObject.put("lastname", names[2]);
            jsonObject.put("username", names[0]);
            Log.d("Message", jsonObject.toString());
            post("https://sleepy-springs-37359.herokuapp.com/fcm/logItem", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            finish();
            Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
            startActivity(intent);
        }

    }
    // Starts the login process once perimeters were given.
    private void startLogin() {
        final String email = etLoginEmail.getText().toString().trim();
        String password = etLoginPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
            Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_LONG)
                    .show();
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            Toast.makeText(getApplicationContext(), "Enter a valid email", Toast.LENGTH_LONG)
                    .show();
        else if(!checkPass(password)){
            Toast.makeText(getApplicationContext(), "Enter a valid password", Toast.LENGTH_LONG)
                    .show();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        sendCredinals(FirebaseInstanceId.getInstance().getToken(), email);
                        finish();
                        progressBar.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        etLoginEmail.setText("");
                        etLoginPassword.setText("");
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }
    // Checks if the given password is valid. Returns True if the password's length is 6 or more characters.
    private boolean checkPass(String password){
        return password.length() >= 6;
    }
}
