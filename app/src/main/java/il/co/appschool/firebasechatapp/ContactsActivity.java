package il.co.appschool.firebasechatapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ContactsActivity extends AppCompatActivity {

    public static final MediaType JSON =
            MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    void post(String url, final String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Error", "No server response :(");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    Gson gson = new Gson();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String jsonInString = jsonObject.toString();
                        final Contact contact = gson.fromJson(jsonInString, Contact.class);
                        if(!contact.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    contactlist.add(contact);
                                    FirebaseDatabase.getInstance().getReference().child(FirebaseInstanceId.getInstance().getToken()).child("Contact: "+ contact.getFullName()).setValue(contact);
                                    contactsAdapter.notifyDataSetChanged();
                                    Toast.makeText(ContactsActivity.this, "Contact added successfully", Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ContactsActivity.this, "You can't add yourself!", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ContactsActivity.this, "Can't find requested email", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }


    public SharedPreferences sp;
    Dialog dialog;
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
        contactsAdapter = new ContactsAdapter(ContactsActivity.this,0,0,contactlist);
        contactsListView.setAdapter(contactsAdapter);
        contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ContactsActivity.this, ChatActivity.class);
                String email = contactlist.get(position).getEmail().trim();
                String fullname = contactlist.get(position).getFullName().trim();
                intent.putExtra("destinationEmail", email);
                intent.putExtra("contactFullName", fullname);
                startActivity(intent);
            }
        });
        contactsListView.setLongClickable(true);
        contactsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(ContactsActivity.this).setTitle("Contact removal").setMessage("Are you sure you want to remove this contact?").setCancelable(false).setPositiveButton("Yes I'm Sure!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,final int which) {
                        contactlist.remove(position);
                        contactsAdapter.notifyDataSetChanged();
                        Toast.makeText(ContactsActivity.this, "Contact removed", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("No, I've changed my mind!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,final int which) {
                        dialog.dismiss();
                    }
                }).create().show();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sp=getApplicationContext().getSharedPreferences("settings",0);
        String background = sp.getString("background", null);
        if (background != null) {
            getWindow().getDecorView().findViewById(android.R.id.content).setBackgroundColor(Color.parseColor(background));
        }
        /*if(FirebaseAuth.getInstance().getCurrentUser() != null){
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(FirebaseInstanceId.getInstance().getToken()).getValue(Contact.class).getEmail() != null && dataSnapshot.child(FirebaseInstanceId.getInstance().getToken()).getValue(Contact.class).getFullName() != null){
                        for(DataSnapshot child : dataSnapshot.getChildren()){
                            contactlist.add(new Contact(child.child(FirebaseInstanceId.getInstance().getToken()).getValue(Contact.class).getEmail(),  child.child(FirebaseInstanceId.getInstance().getToken()).getValue(Contact.class).getFullName()));
                            contactsAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("TAG", databaseError.getMessage());
                }
            };
            FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(valueEventListener);
        }*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(ContactsActivity.this, MainActivity.class));
        }
        /*if(FirebaseAuth.getInstance().getCurrentUser() != null){
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(FirebaseInstanceId.getInstance().getToken()).getValue(Contact.class).getEmail() != null && dataSnapshot.child(FirebaseInstanceId.getInstance().getToken()).getValue(Contact.class).getFullName() != null){
                        for(DataSnapshot child : dataSnapshot.getChildren()){
                            contactlist.add(new Contact(child.child(FirebaseInstanceId.getInstance().getToken()).getValue(Contact.class).getEmail(),  child.child(FirebaseInstanceId.getInstance().getToken()).getValue(Contact.class).getFullName()));
                            contactsAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("TAG", databaseError.getMessage());
                }
            };
            FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(valueEventListener);
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contacts_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_Add_Contact){
            addContactDialog();
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

    private void addContactDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.add_contact_dialog);
        final EditText editText = dialog.findViewById(R.id.etDisplaySearch);
        Button button = dialog.findViewById(R.id.btnAdd);
        if(!editText.getText().toString().isEmpty())
            button.setClickable(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmailValid(editText.getText().toString().trim())){
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("email", editText.getText().toString().trim());
                        editText.setText("");
                        post("https://sleepy-springs-37359.herokuapp.com/fcm/searchItem", jsonObject.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                } else {
                    editText.setText("");
                    editText.setError("Enter a valid email!");
                    editText.requestFocus();
                }
            }
        });
        dialog.show();
    }

    boolean isEmailValid(CharSequence charSequence){
        return Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
    }
}
