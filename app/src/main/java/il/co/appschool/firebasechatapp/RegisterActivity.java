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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText etRegisterEmail, etRegisterPassword, etConfirmPassword;
    private Button btnRegister, btnGoToLogin;
    private ProgressBar progressBar;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        etRegisterEmail = findViewById(R.id.etRegisterEmail);
        etRegisterPassword = findViewById(R.id.etRegisterPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        progressBar = findViewById(R.id.RegisterProgress);
        progressBar.setVisibility(View.INVISIBLE);
        btnRegister = findViewById(R.id.btn_Register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });
        btnGoToLogin = findViewById(R.id.btnMoveToLog);
        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
    }

    private void startRegister() {
        String email = etRegisterEmail.getText().toString().trim();
        String password = etRegisterPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(etConfirmPassword.getText().toString().trim()))
            Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_LONG)
                    .show();
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            Toast.makeText(getApplicationContext(), "Enter a valid E-mail", Toast.LENGTH_LONG)
                    .show();
        else if(!confirmPassword(password, etConfirmPassword.getText().toString().trim()))
            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_LONG)
                    .show();
        else if(!checkPassword(password))
            Toast.makeText(getApplicationContext(), "Enter a valid password!", Toast.LENGTH_LONG)
                    .show();
        else{
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        finish();
                        progressBar.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(RegisterActivity.this, SetUPActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        etRegisterEmail.setText("");
                        etRegisterPassword.setText("");
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

    private boolean confirmPassword(String pass1, String pass2){
        return pass1.equals(pass2);
    }

    private boolean checkPassword(String password){
        return password.length() >= 6;
    }
}
