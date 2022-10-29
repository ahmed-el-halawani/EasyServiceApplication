package com.example.easyserviceapplication;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.easyservice.Autostart;
import com.example.easyservice.Connection;
import com.example.easyservice.SuperService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MyService extends SuperService {

    int z = 1;

    @Override
    protected List<Connection> getCommunication() {
        ArrayList<Connection> connectionArrayList = new ArrayList<>();

        connectionArrayList.add(firstServiceFunction());

        connectionArrayList.add(secondServiceFunction());
        return connectionArrayList;
    }

    @NonNull
    private Connection secondServiceFunction() {
        return new Connection("ym", (superServiceBinder, stringObjectMap) -> {
            Thread thread = new Thread(() -> {
                try {
                    int x = 10;
                    while (true) {
                        Thread.sleep(1000);
                        Log.e("ym", "number: " + x++);
                    }
                } catch (InterruptedException e) {
                    Log.e("TAG", "getCommunication: thread end");
                }
            });
            thread.start();
            superServiceBinder.setFunction(new Connection("endThread", (superServiceBinder1, stringObjectMap1) -> {
                thread.interrupt();
            }));

        }, true);
    }

    @NonNull
    private Connection firstServiceFunction() {
        return new Connection("x", (superServiceBinder, stringObjectMap) -> {
            Log.e("first connection", "onCreate: ");
            z++;
            superServiceBinder.invoke("zz", new HashMap<String, Object>() {{
                put("intData", z);
            }});
        }
        );
    }
}