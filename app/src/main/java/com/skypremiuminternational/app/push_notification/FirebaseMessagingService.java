package com.skypremiuminternational.app.push_notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.App;
import com.skypremiuminternational.app.app.features.estore.EstoreActivity;
import com.skypremiuminternational.app.app.features.splash.SplashActivity;
import com.skypremiuminternational.app.app.utils.CommonUtils;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.ObjectUtil;
import java.util.Map;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class FirebaseMessagingService {}/*extends com.google.firebase.messaging.FirebaseMessagingService {

    private final String TAG = Constants.FIREBASE_MSG;
    private String action_click = "";
    private String title = "";
    private String body = "";

    public void onNewToken (String token){
        super.onNewToken(token);
        Log.d("NEW_TOKEN",token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Data Message Body: " + remoteMessage.getData());
        if (!ObjectUtil.isEmptyMap(remoteMessage.getData())) {
            sendData(remoteMessage.getData());
        }
    }

    private void sendData(Map<String, String> dataMap) {
        this.title = dataMap.get(Constants.TITLE_KEY);
        this.body = dataMap.get(Constants.BODY_KEY);
        this.action_click = dataMap.get(Constants.ACTION_KEY);

        Intent intent;
        if (!ObjectUtil.isNull(action_click)){
            switch (action_click){
                case Constants.ACTION_KEY:
                    intent = new Intent(getApplicationContext(), SplashActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    break;
                default:
                    intent = new Intent(getApplicationContext(), App.isOpenningApp ? EstoreActivity.class : SplashActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    break;
            }
        } else {
            intent = new Intent(getApplicationContext(), App.isOpenningApp ? EstoreActivity.class : SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(Constants.CHANNEL_ID,
                    Constants.CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, Constants.CHANNEL_ID);
        notificationBuilder.setSmallIcon(R.drawable.ic_logo_tab);
        notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo_app));
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(body);
        notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(body));
        notificationBuilder.setSound(defaultSoundUri);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
        notificationBuilder.setDefaults(Notification.DEFAULT_ALL);
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH); // or NotificationCompat.PRIORITY_MAX
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(this.action_click, CommonUtils.getRandomId(0,999999999), notificationBuilder.build());
    }
}*/
