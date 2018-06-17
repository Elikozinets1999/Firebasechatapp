package il.co.appschool.firebasechatapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatActivity extends AppCompatActivity {

    public static final MediaType JSON =
            MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    //Receives Message intent in order to insert object in list
    private BroadcastReceiver ll = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("Update List")){
                String title = intent.getStringExtra("title");
                String body = intent.getStringExtra("body");
                ChatMessage chatMessage = new ChatMessage(body, title);
                chatlist.add(chatMessage);
                chatAdapter.notifyDataSetChanged();
            }
        }
    };
    // Tells in the background if the message was sent successfully.
    void post(String url, String json) throws IOException{
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
                    Log.d("Message", "Message sent successfully");
                }
            }
        });
    }

    FirebaseAuth mAuth;
    ListView chatList;
    ArrayList<ChatMessage> chatlist;
    ChatAdapter chatAdapter;
    String fname, lname;
    TextView tvName;
    public SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Log.d("TOKEN", FirebaseInstanceId.getInstance().getToken());
        mAuth = FirebaseAuth.getInstance();
        tvName = findViewById(R.id.tvName);
        final Intent intent = getIntent();
        tvName.setText(intent.getStringExtra("contactFullName"));
        chatList = findViewById(R.id.list_of_messages);
        FloatingActionButton fab = findViewById(R.id.fab);
        chatlist = new ArrayList<ChatMessage>();
        chatAdapter = new ChatAdapter(ChatActivity.this, 0,0,chatlist);
        chatList.setAdapter(chatAdapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = findViewById(R.id.input);
                String[] names = mAuth.getCurrentUser().getDisplayName().split(" ");
                fname = names[1];
                lname = names[2];
                sendRequest(intent.getStringExtra("destinationEmail"),input.getText().toString(), fname, lname);
                ChatMessage chatMessage = new ChatMessage(input.getText().toString(),fname+" "+lname);
                chatlist.add(chatMessage);
                chatAdapter.notifyDataSetChanged();
                input.setText("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_sign_out) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ChatActivity.this, MainActivity.class));
        }
        if(item.getItemId() == R.id.action_gotoProfile ){
            Intent intent_toprofile = new Intent(ChatActivity.this, ProfileActivity.class);
            startActivity(intent_toprofile);

        }
        if(item.getItemId() == R.id.action_Clear_Chat){
            chatlist.clear();
            chatAdapter.notifyDataSetChanged();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        sp=getApplicationContext().getSharedPreferences("settings",0);
        String background = sp.getString("background", null);
        if (background != null) {
            getWindow().getDecorView().findViewById(android.R.id.content).setBackgroundColor(Color.parseColor(background));
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Update List");
        LocalBroadcastManager.getInstance(this).registerReceiver(ll, intentFilter);
    }
    // Sends a request to the server to send the message. Target is defined by email.
    public void sendRequest (String email, String msg, String fName, String lName ){
        JSONObject jsonObject = new JSONObject();
        if(msg.length() > 0){
            try {
                jsonObject.put("email", email);
                jsonObject.put("firstname", fName);
                jsonObject.put("lastname", lName);
                jsonObject.put("body", msg);
                Log.d("Message", email);
                Log.d("Message", "Content sent "+msg);
                post("https://sleepy-springs-37359.herokuapp.com/fcm/sendM", jsonObject.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
