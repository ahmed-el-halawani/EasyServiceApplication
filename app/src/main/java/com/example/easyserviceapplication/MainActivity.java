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
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyService.observeOn("zz", this, stringObjectMap -> {
            Log.e("MainActivity", "onServiceConnected: from mainActivity stringObjectMap: " + stringObjectMap);
        });

        MyService.observeOn("yy", this, stringObjectMap -> {
            Log.e("MainActivity", "onServiceConnected: from mainActivity stringObjectMap: " + stringObjectMap);
        });

        this.getLifecycle().addObserver(new LifecycleObserver() {
        });

        findViewById(R.id.start).setOnClickListener(v -> {
            Log.e(TAG, "onCreate: ");
            MyService.invoke("x");
            MyService.invoke("yy");
            MyService.invoke("mm");
        });


        findViewById(R.id.plus).setOnClickListener(v1 -> MyService.invoke("x"));

        findViewById(R.id.endThread).setOnClickListener(v1 -> MyService.invoke("endThread"));

        findViewById(R.id.startThread2).setOnClickListener(v1 -> MyService.invoke("ym"));

    }

    @Override
    protected void onDestroy() {
//        MyService.stopService(this.getApplication());
        super.onDestroy();
    }
}