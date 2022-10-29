package com.example.easyserviceapplication;

import android.app.Application;
import android.util.Log;

import com.example.easyservice.Configuration;
import com.example.easyservice.Connection;
import com.example.easyservice.SuperService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        MyService.init(this,
                new Configuration("EasyServiceChannelDescription",
                        "EasyServiceChannelName",
                        "EasyServiceChannelId",
                        com.example.easyservice.R.drawable.ic_easy_service,
                        true,
                        true),
                MyService.class);
        MyService.startService(this);
//        MyService.startService(this, MyService.class);
    }
}

