package com.example.easyservice;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import java.util.Map;
import java.util.function.Consumer;

public abstract class Communicator extends Initializer {
//    public static void setFunction(Connection connection) {
//        apply(superServiceBinder -> superServiceBinder.setFunction(connection));
//    }

    public static void invoke(String key, Map<String, Object> params) {
        apply(superServiceBinder -> superServiceBinder.invoke(key, params));
    }

    public static void invoke(String key) {
        apply(superServiceBinder -> superServiceBinder.invoke(key));
    }

    public static void observeOn(String key, LifecycleOwner lifecycleOwner, Observer<Map<String, Object>> observer) {
        apply(superServiceBinder -> superServiceBinder.observeOn(key, lifecycleOwner, observer));

    }

    protected static void apply(Consumer<SuperService.SuperServiceBinder> onServiceStart) {
        if (needToBeCreated.contains(onServiceStart)) return;
        if (Initializer.service == null) {
            needToBeCreated.add(onServiceStart);
            Log.e("apply", "apply: ");
        } else
            onServiceStart.accept(Initializer.service);
    }
}
