package com.example.easyservice;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class Autostart extends BroadcastReceiver {
    public void onReceive(Context context, Intent arg1) {
        Initializer.autoStartService((Application) context.getApplicationContext());
//        Intent intent = new Intent(context, SuperService.class);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            context.startForegroundService(intent);
//        } else {
//            context.startService(intent);
//        }
//        Log.i("Autostart", "started");
    }
}