package com.codehasy.chatg.Notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

import com.codehasy.chatg.MessageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String sented=remoteMessage.getData().get("sented");

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser!=null&&sented.equals(firebaseUser.getEmail().split("@")[0])){
            sendNotification(remoteMessage);
        }
    }

    private void sendNotification(RemoteMessage remoteMessage) {

        String user=remoteMessage.getData().get("user");
        String icon=remoteMessage.getData().get("icon");
        String title=remoteMessage.getData().get("title");
        String body=remoteMessage.getData().get("body");
        String chid="chatg1";
        String GroupKey="com.codehasy.chatg";
        RemoteMessage.Notification notification=remoteMessage.getNotification();
        String ss=user.replaceAll("[\\D]","");
        int j=0;
        if(!ss.equals(""))
        j=Integer.parseInt(ss);

        Intent intent=new Intent(this, MessageActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("userid",user);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent=PendingIntent.getActivity(this,j,intent,PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(
                    chid,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
        }


        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),chid)
                .setSmallIcon(Integer.parseInt(icon))
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setGroup(GroupKey)
                .setContentIntent(pendingIntent);


        NotificationManager noti=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        int i=0;
        if(j>=0)
            i=j;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            noti.createNotificationChannel(channel);
        }
        builder.setChannelId(chid);

        noti.notify((int)System.currentTimeMillis(),builder.build());

    }
}
