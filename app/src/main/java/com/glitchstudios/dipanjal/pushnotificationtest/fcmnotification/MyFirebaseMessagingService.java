package com.glitchstudios.dipanjal.pushnotificationtest.fcmnotification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.glitchstudios.dipanjal.pushnotificationtest.MainActivity;
import com.glitchstudios.dipanjal.pushnotificationtest.R;
import com.glitchstudios.dipanjal.pushnotificationtest.utils.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCMService";

//    public static final String FCM_PARAM = "picture";
//    private static final String CHANNEL_NAME = "FCM";
//    private static final String CHANNEL_DESC = "Firebase Cloud Messaging";
    private static final String FIREBASE_TOKEN = "fcm_token";
    private int numMessages = 0;

    NotificationUtils notificationUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationUtils = new NotificationUtils(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        RemoteMessage.Notification firebaseNotification = remoteMessage.getNotification();

        String title = firebaseNotification.getTitle();
        String body = firebaseNotification.getBody();

//        Map<String, String> data = remoteMessage.getData();
//        Log.d("FROM", remoteMessage.getFrom());
//        Notification.Builder nb =  notificationUtils
//                .getAndroidChannelNotification(title, body);
//        notificationUtils.getManager().notify(101, nb.build());


        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        notificationUtils.sendNotification(title,body,pendingIntent);

    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        String SHARED_PERF_INDEX = getString(R.string.fcm_shared_pref_token_index);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.edit().putString(SHARED_PERF_INDEX, token).apply();

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(token);
    }

}
