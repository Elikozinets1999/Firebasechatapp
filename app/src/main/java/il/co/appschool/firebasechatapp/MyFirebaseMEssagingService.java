package il.co.appschool.firebasechatapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by elili on 3/10/2018.
 */

public class MyFirebaseMEssagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("Message", remoteMessage.getFrom());
        //if the message contains data payload
        //It is a map of custom keyvalues
        //we can read it easily

        if (remoteMessage.getData().size() > 0) {
            Log.d("TOKEN",remoteMessage.getData().toString());
   //         sendNodification(remoteMessage.getNotification().getBody());
            boolean handler = new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    sendNodification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());
                }
            }, 1000);
        }

        if (remoteMessage.getNotification() != null) {
            Log.d("TOKEN", remoteMessage.getNotification().getBody());
        }
    }


        //then here we can use the title and body to build a notification

    private void sendNodification(String messagebody, String messagetitle) {
        Log.d("TOKEN", "Sending Notification...");
        Intent intent = new Intent(this, ChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = Constants.CHANNEL_ID;
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setContentTitle(messagetitle)
                        .setSmallIcon(R.drawable.fui_ic_mail_white_24dp)
                        .setContentText(messagebody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Channel title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, notificationBuilder.build());
        Log.d("TOKEN", "Notification delivered");
//        myNotificationManager.getInstance(getApplicationContext()).displayNotification(messagetitle, messagebody);
//        updateList(messagetitle, messagebody);

    }

    private void updateList(String title, String body){
        Intent i = new Intent();
        i.setAction("SendMessage");
        i.putExtra("Message Title",title);
        i.putExtra("Message body", body);
        this.sendBroadcast(i);
    }

}



