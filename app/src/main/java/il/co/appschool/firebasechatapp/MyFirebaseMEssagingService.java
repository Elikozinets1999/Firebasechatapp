package il.co.appschool.firebasechatapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by elili on 3/10/2018.
 */

public class MyFirebaseMEssagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("Message", remoteMessage.getFrom());
        //if the message contains data payload
        //It is a map of custom keyvalues
        //we can read it easily

        if(remoteMessage.getData().size() > 0){
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
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
            myNotificationManager.getInstance(getApplicationContext()).displayNotification(title,body);
            ChatMessage chatMessage = new ChatMessage(body,title);
        }



        //then here we can use the title and body to build a notification
    }
}
