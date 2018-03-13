package il.co.appschool.firebasechatapp;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ContactsActivity extends AppCompatActivity {
    public SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
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
