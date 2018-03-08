package il.co.appschool.firebasechatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText etLoginEmail, etLoginPassword;
    private Button btnLogin, btnGoToReg;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
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

    //@Override
    /*protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(MainActivity.this, ChatActivity.class));
        }

    }*/

    private void startLogin() {
        String email = etLoginEmail.getText().toString().trim();
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
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        finish();
                        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        etLoginEmail.setText("");
                        etLoginPassword.setText("");
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private boolean checkPass(String password){
        return password.length() >= 6;
    }
}
