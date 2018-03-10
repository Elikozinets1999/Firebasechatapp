package il.co.appschool.firebasechatapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    ListView chatList;
    ArrayList<ChatMessage> chatlist;
    ChatAdapter chatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatList = findViewById(R.id.list_of_messages);
        FloatingActionButton fab = findViewById(R.id.fab);
        chatlist = new ArrayList<ChatMessage>();
        chatAdapter = new ChatAdapter(ChatActivity.this, 0,0,chatlist);
        chatList.setAdapter(chatAdapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = findViewById(R.id.input);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel mChannel = new NotificationChannel(Constants.CHANNEL_ID,Constants.CHANNEL_NAME, importance);
                    mChannel.setDescription(Constants.CHANNEL_DESCRIPTION);
                    mChannel.enableLights(true);
                    mChannel.setLightColor(Color.RED);
                    mChannel.enableVibration(false);
                    mNotificationManager.createNotificationChannel(mChannel);
                }
                myNotificationManager.getInstance(ChatActivity.this).displayNotification(FirebaseAuth.getInstance().getCurrentUser().getEmail(),input.getText().toString());
                ChatMessage chatMessage = new ChatMessage(input.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getEmail());
                chatlist.add(chatMessage);
                input.setText("");
                chatAdapter.notifyDataSetChanged();
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
        return true;
    }
}
