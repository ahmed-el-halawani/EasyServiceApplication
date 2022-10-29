package com.example.easyservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class SuperService extends Communicator {
    private static final String TAG = "SuperService";
    private final SuperServiceBinder binder = new SuperServiceBinder();

    protected abstract List<Connection> getCommunication();


    @Override
    public void onCreate() {
        super.onCreate();
        communication = new Communication();
        for (Connection connection : getCommunication()) {
            communication.setFunction(connection);
        }
        SharedPreferenceConfiguration sharedPreferenceConfiguration = SharedPreferenceConfiguration.getInstance(this.getApplication());
        configuration = sharedPreferenceConfiguration.getConfiguration();
//        communication = sharedPreferenceConfiguration.getConnections();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        for (Connection connection : communication.map) {
            if (connection.isRunOnStart())
                connection.getFunction().accept(binder, connection.getDefaultParams());
        }

        Log.e(TAG, "onStartCommand: ");
        Log.e(TAG, "onStartCommand: ");
        Log.e(TAG, "onStartCommand: ");
        Log.e(TAG, "onStartCommand: ");

        if (configuration.isForeground())
            runOnForeground();

        return START_STICKY;
    }


    private void runOnForeground() {

        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(Objects.requireNonNull(configuration.getServiceClass().getPackage()).getName());

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 200, launchIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification =
                new NotificationCompat.Builder(this, configuration.getChannelId())
                        .setContentTitle(configuration.getChannelName())
                        .setContentText(configuration.getChannelDescription())
                        .setSmallIcon(configuration.getChannelIcon())
                        .setOngoing(true)
                        .setContentIntent(pendingIntent)
                        .build();

        startForeground(1, notification);
    }


    public class SuperServiceBinder extends Binder implements ICommunication {
        @Override
        public void setFunction(Connection connection) {
            communication.setFunction(connection);
        }

        @Override
        public void invoke(String key, Map<String, Object> params) {
            communication.invoke(key, this, params);
        }

        @Override
        public void invoke(String key) {
            communication.invoke(key, this);
        }

        @Override
        public void observeOn(String key, LifecycleOwner lifecycleOwner, Observer<Map<String, Object>> observer) {
            communication.observeOn(key, lifecycleOwner, observer);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binder.invoke("endThread");
    }
}
