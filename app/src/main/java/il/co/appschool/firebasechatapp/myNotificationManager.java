package il.co.appschool.firebasechatapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by elili on 3/10/2018.
 */

public class myNotificationManager {
    private Context mCtx;
    private static myNotificationManager mInstance;
    private myNotificationManager(Context context){
        mCtx = context;
    }

    public static synchronized myNotificationManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new myNotificationManager(context);
        }
        return mInstance;
    }

    public void displayNotification(String title, String body){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mCtx, Constants.CHANNEL_ID)
                .setSmallIcon(R.drawable.fui_ic_phone_white_24dp)
                .setContentTitle(title)
                .setContentText(body);
        Intent resultIntent = new Intent(mCtx, ChatActivity.class);

        /*
        *  Now we will create a pending intent
        *  The method getActivity is taking 4 parameters
        *  All paramters are describing themselves
        *  0 is the request code (the second parameter)
        *  We can detect this code in the activity that will open by this we can get
        *  Which notification opened the activity
        * */

        PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        /*
        *  Setting the pending intent to notification builder
        * */

        mBuilder.setContentIntent(pendingIntent);
        NotificationManager mNotifyMgr = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        if(mNotifyMgr != null){
            mNotifyMgr.notify(1, mBuilder.build());
        }
    }
}
