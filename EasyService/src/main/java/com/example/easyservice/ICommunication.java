package com.example.easyservice;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import java.util.Map;

public interface ICommunication {
    void setFunction(Connection connection);

    void invoke(String key, Map<String, Object> params);

    void invoke(String key);

    void observeOn(String key, LifecycleOwner lifecycleOwner, Observer<Map<String, Object>> observer);
}
