package il.co.appschool.firebasechatapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {
    public SharedPreferences sp;
    ListView contactsListView;
    ArrayList<Contact> contactlist;
    ContactsAdapter contactsAdapter;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        contactsListView = findViewById(R.id.lvContacts);
        contactlist = new ArrayList<Contact>();
        contactsAdapter = new ContactsAdapter(getApplicationContext(),0,0,contactlist);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contacts_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_Add_Contact){
            //
        }
        if(item.getItemId() == R.id.sign_out){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, MainActivity.class));
        }
        if(item.getItemId() == R.id.contact_menu_gotoProfile){
            startActivity(new Intent(this,ProfileActivity.class));
        }
        return true;
    }
}
