package com.example.easyservice;

import android.util.Log;

import androidx.annotation.DrawableRes;

import java.util.Objects;

public class Configuration {
    private final String channelName;
    private final String channelDescription;
    private final String channelId;
    @DrawableRes
    private final int channelIcon;
    private final boolean foreground;
    private String serviceClass;
    private final boolean isAutoStart;

    public Configuration(String channelName, String channelDescription, String channelId, @DrawableRes int channelIcon, boolean foreground, boolean isAutoStart) {
        this.channelName = channelName;
        this.channelDescription = channelDescription;
        this.channelId = channelId;
        this.channelIcon = channelIcon;
        this.foreground = foreground;
        this.isAutoStart = isAutoStart;
    }


    public boolean isForeground() {
        return foreground;
    }

    public Configuration() {
        this.channelDescription = "EasyServiceChannelDescription";
        this.channelName = "EasyServiceChannelName";
        this.channelId = "EasyServiceChannelId";
        channelIcon = R.drawable.ic_easy_service;
        this.foreground = true;
        this.isAutoStart = true;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getChannelDescription() {
        return channelDescription;
    }

    public String getChannelId() {
        return channelId;
    }

    public int getChannelIcon() {
        return channelIcon;
    }

    public boolean isAutoStart() {
        return isAutoStart;
    }

    public Class<?> getServiceClass() {
        Class<?> serviceClassObj = null;
        try {
            serviceClassObj = Class.forName(serviceClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "this.serviceClassObj: " + serviceClassObj);

        return serviceClassObj;
    }

    public void setServiceClass(Class<?> serviceClass) {
        this.serviceClass = serviceClass.getName();
        Log.e(TAG, "this.serviceClass: " + this.serviceClass);
    }

    private static final String TAG = "Configuration";

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Configuration that = (Configuration) o;
        return channelIcon == that.channelIcon && Objects.equals(channelName, that.channelName) && Objects.equals(channelDescription, that.channelDescription) && Objects.equals(channelId, that.channelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channelName, channelDescription, channelId, channelIcon);
    }
}
