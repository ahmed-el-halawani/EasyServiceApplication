package com.example.easyserviceapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.Observer;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.easyservice.Connection;
import com.example.easyservice.SuperService;

import java.util.Map;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {
    SuperService.SuperServiceBinder superService;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyService.observeOn("zz", this, stringObjectMap -> {

            Log.e("MainActivity", "onServiceConnected: from mainActivity stringObjectMap: " + stringObjectMap);
        });

//        MyService.setFunction(new Connection("zz", (superServiceBinder1, stringObjectMap) -> {
//            Log.e("MainActivity", "onServiceConnected: from mainActivity stringObjectMap: " + stringObjectMap);
//        }));

        this.getLifecycle().addObserver(new LifecycleObserver() {
        });

        findViewById(R.id.start).setOnClickListener(v -> {
            Log.e(TAG, "onCreate: ");
            MyService.invoke("x");
        });


        findViewById(R.id.plus).

                setOnClickListener(v1 -> MyService.invoke("x"));

        findViewById(R.id.endThread).

                setOnClickListener(v1 ->

                        MyService.invoke("endThread"));

        findViewById(R.id.startThread2).

                setOnClickListener(v1 -> MyService.invoke("ym"));


//
//            Intent intent = new Intent(this, MyService.class);
//            bindService(intent, new ServiceConnection() {
//                @Override
//                public void onServiceConnected(ComponentName name, IBinder service) {
//                    SuperService.SuperServiceBinder superService = (SuperService.SuperServiceBinder) service;
//
//                    superService.setFunction(new Connection("zz", (superServiceBinder, stringObjectMap) -> {
//                        Log.e("MainActivity", "onServiceConnected: from mainActivity");
//                    }));
//
//                    superService.invoke("x");
//                }
//
//                @Override
//                public void onServiceDisconnected(ComponentName name) {
//
//                }
//            }, Context.BIND_AUTO_CREATE);
//
//            MyService.bindService(this.getApplication(), new ServiceConnection() {
//                @Override
//                public void onServiceConnected(ComponentName name, IBinder service) {
//                    SuperService.SuperServiceBinder superService = (SuperService.SuperServiceBinder) service;
//
//                    superService.setFunction(new Connection("zz", (superServiceBinder, stringObjectMap) -> {
//                        Log.e("MainActivity", "onServiceConnected: from mainActivity");
//                    }));
//
//                    superService.invoke("x");
//                }
//
//                @Override
//                public void onServiceDisconnected(ComponentName name) {
//
//                }
//            });


    }

    @Override
    protected void onDestroy() {
//        MyService.stopService(this.getApplication());

        super.onDestroy();
    }
}