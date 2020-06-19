package brdevelopers.com.jobvibe.pushNotification;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

//import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import brdevelopers.com.jobvibe.Home;

public class FirebaseDataReceiver extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getData().isEmpty() == false) {
            try {
                JSONObject json =  new JSONObject(remoteMessage.getData());
                sendNotification(this,json);

            } catch (Exception e) {
             //
            }

        }
    }


    public void sendNotification(Context context,  JSONObject json ) {
        JSONObject data = null;
        try {
            data = json.getJSONObject("data");
            String title = data.getString("title");
            String message = data.getString("message");

            Intent intent = new Intent(this, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            MyNotificationManager myNotificationManager = new MyNotificationManager(context);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                myNotificationManager.showNotificationOreo(title, message, intent);
            } else {
                myNotificationManager.showNotification(title, message, intent);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
