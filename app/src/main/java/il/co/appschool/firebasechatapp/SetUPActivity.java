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

public class SetUPActivity extends AppCompatActivity {
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
                etDisplayName.setText("");
                etFirstName.setText("");
                etLastName.setText("");
            }
        });
    }

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

        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayname+" "+firstname+" "+lastname)
                    .build();
            Log.d("TAG", profileChangeRequest.getDisplayName());
            user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Profile updated", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SetUPActivity.this, ProfileActivity.class);
                        startActivity(intent);
                    }
                }
            });
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


}
