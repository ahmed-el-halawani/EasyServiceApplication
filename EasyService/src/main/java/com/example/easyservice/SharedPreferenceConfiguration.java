package com.example.easyservice;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

public class SharedPreferenceConfiguration {
    private final Application application;

    private static SharedPreferenceConfiguration sharedPreferenceConfiguration;

    public static SharedPreferenceConfiguration getInstance(Application application) {
        if (sharedPreferenceConfiguration == null)
            sharedPreferenceConfiguration = new SharedPreferenceConfiguration(application);

        return sharedPreferenceConfiguration;
    }


    private SharedPreferenceConfiguration(Application application) {
        this.application = application;
    }

    private SharedPreferences.Editor getEditor() {
        return getSharedPref().edit();
    }

    private SharedPreferences getSharedPref() {
        return application.getSharedPreferences(SharedPreferenceConfiguration.class.getName(), Context.MODE_PRIVATE);
    }

    public void setConfiguration(Configuration configuration) {
        getEditor().putString("configuration", new Gson().toJson(configuration)).apply();
    }

    public Configuration getConfiguration() {
        String config = getSharedPref().getString("configuration", null);
        Configuration configuration = new Configuration();
        if (config != null)
            configuration = new Gson().fromJson(config, Configuration.class);
        return configuration;
    }

    public void setConnections(Communication communication) {
        getEditor().putString("communication", new Gson().toJson(communication)).apply();
    }

    public Communication getConnections() {
        String config = getSharedPref().getString("communication", null);
        Communication communication = new Communication();

        Log.e("TAG", "getConnections: " + communication);
        Log.e("TAG", "getConnections: " + config);
//        if (config != null)
//            communication = new Gson().fromJson(config, Communication.class);
        return communication;
    }


}
