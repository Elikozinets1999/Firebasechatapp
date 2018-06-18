package il.co.appschool.firebasechatapp.Shaked;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import il.co.appschool.firebasechatapp.MainShaked;
import il.co.appschool.firebasechatapp.R;

public class USBConnected extends AppCompatActivity {

    ArrayList<Broadcast> broadcasts;
    BroadcastHelper broadcastHelper;
    TextView tv;
    BroadCastUSBConnected broadCastUSBConnected;
    NotificationHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usbconnected);

        tv = (TextView)findViewById(R.id.tvDisplay);
        helper = new NotificationHelper(this);
        broadCastUSBConnected = new BroadCastUSBConnected();

        broadcastHelper = new BroadcastHelper(this);
    }
    private class BroadCastUSBConnected extends BroadcastReceiver //using the broadcast's details, checks whether or not the device is connected to any kind of power and does certain things accordingly.
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
            boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
            if(usbCharge||acCharge)
                tv.setText("TRUE");
            else
                tv.setText("FALSE");

            MakeNotification(context, "USBConnected", "Status", String.valueOf(usbCharge||acCharge));
            Broadcast br = new Broadcast("USBConnected", "USB connection changed to " + tv.getText() + ".", String.valueOf(Calendar.getInstance().getTime()));
            broadcastHelper.open();
            br = broadcastHelper.createBroadcast2(br);
            broadcasts = broadcastHelper.getAllBroadcasts();
            Log.d("data1", br.toString());
            Log.d("data1", broadcasts.toString());
            broadcastHelper.close();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadCastUSBConnected, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        EndNotification();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadCastUSBConnected);

        broadcastHelper.open();
        SharedPreferences prefs = getSharedPreferences("broadcasts_info2", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("key1", broadcastHelper.getAllBroadcasts().toString());
        edit.commit();
        broadcastHelper.close();
    }
    public void MakeNotification(Context context, String title, String ticker, String text) // creates a notification, can be used in order to show a change detected by the broadcast
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent(context, MainShaked.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            Notification.Builder builder1 = helper.getSHAKEDChannelNotification(title, text,pendingIntent, System.currentTimeMillis(), ticker);
            helper.getManager().notify(1, builder1.build());
        }
        else {
            Intent intent = new Intent(context, MainShaked.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
            Notification notification = builder
                    .setContentIntent(pendingIntent)
                    .setTicker(ticker)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true).setContentTitle(title)
                    .setSmallIcon(android.R.drawable.star_on)
                    .setContentText(text).build();
            notificationManager.notify(1, notification);
        }
    }
    public void EndNotification() //stops the notification service
    {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(1);
    }

}
