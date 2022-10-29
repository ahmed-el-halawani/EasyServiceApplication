package com.example.easyservice;

import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

abstract class Initializer extends Service {

    protected Configuration configuration;
    protected Communication communication;
    protected static SuperService.SuperServiceBinder service = null;
    protected static final List<Consumer<SuperService.SuperServiceBinder>> needToBeCreated = new ArrayList<>();

    private static final String TAG = "Initializer";
    private static final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Initializer.service = (SuperService.SuperServiceBinder) service;
            for (Consumer<SuperService.SuperServiceBinder> needToCreate : needToBeCreated) {
                Log.e(TAG, "onServiceConnected: " + needToCreate.toString());
                needToCreate.accept(Initializer.service);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Initializer.service = null;
        }
    };

    public static void init(Application application, Configuration configuration, Class<?> serviceClass) {
        createNotificationChannel(application, configuration);
        SharedPreferenceConfiguration sharedPreferenceConfiguration = SharedPreferenceConfiguration.getInstance(application);
        configuration.setServiceClass(serviceClass);
        sharedPreferenceConfiguration.setConfiguration(configuration);
    }

    public static void init(Application application, Configuration configuration, String serviceClassName) throws ClassNotFoundException {
        createNotificationChannel(application, configuration);
//        Communication communication = new Communication();
//        for (Connection connection : connectionList) {
//            communication.setFunction(connection);
//        }
        SharedPreferenceConfiguration sharedPreferenceConfiguration = SharedPreferenceConfiguration.getInstance(application);
        Class<?> serviceClass = Class.forName(serviceClassName);
        configuration.setServiceClass(serviceClass);
//        sharedPreferenceConfiguration.setConnections(communication);
        sharedPreferenceConfiguration.setConfiguration(configuration);
    }

    private static void createNotificationChannel(Application application, Configuration configuration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = configuration.getChannelName();
            String description = configuration.getChannelDescription();
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(configuration.getChannelId(), name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = application.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    public static void startService(Application application) {
        Configuration configuration = SharedPreferenceConfiguration.getInstance(application).getConfiguration();
        if (isServiceRunning(application, configuration.getServiceClass())) return;
        Intent intent = new Intent(application, configuration.getServiceClass());
        ComponentName x;
        if (configuration.isForeground() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            x = application.startForegroundService(intent);
        } else {
            x = application.startService(intent);
        }
        if (x != null) reBindService(application);
    }

    public static void autoStartService(Application application) {
        Configuration configuration = SharedPreferenceConfiguration.getInstance(application).getConfiguration();
        if (!configuration.isAutoStart()) return;
        if (isServiceRunning(application, configuration.getServiceClass())) return;
        Intent intent = new Intent(application, configuration.getServiceClass());
        if (configuration.isForeground() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            application.startForegroundService(intent);
        } else {
            application.startService(intent);
        }
    }


    public  static void stopService(Application application) {
        Configuration configuration = SharedPreferenceConfiguration.getInstance(application).getConfiguration();
        if (!isServiceRunning(application, configuration.getServiceClass())) return;
        Intent intent = new Intent(application, configuration.getServiceClass());
        application.unbindService(connection);
        application.stopService(intent);
    }

    public static void reBindService(Application application) {
        Configuration configuration = SharedPreferenceConfiguration.getInstance(application).getConfiguration();
        if (isServiceRunning(application, configuration.getServiceClass())) {
            Intent intent = new Intent(application, configuration.getServiceClass());
            application.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
    }

    public static void bindForeignService(Application application, String serviceClassName) throws ClassNotFoundException {
        Class<?> serviceClassObj;
        serviceClassObj = Class.forName(serviceClassName);

        if (isServiceRunning(application, serviceClassObj)) {
            Intent intent = new Intent(application, serviceClassObj);
            application.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
    }


    public static void unbindService(Application application) {
        application.unbindService(connection);
    }


    private static boolean isServiceRunning(Application application, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


}
