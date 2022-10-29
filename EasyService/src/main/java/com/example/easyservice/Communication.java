package com.example.easyservice;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

class Communication {

    Communication() {
    }

    public final List<Connection> map = new ArrayList<>();

    public final Map<String, MutableLiveData<Map<String, Object>>> liveDataMap = new HashMap<>();

    public void observeOn(String key, LifecycleOwner lifecycleOwner, Observer<Map<String, Object>> observer) {
        if (!liveDataMap.containsKey(key))
            liveDataMap.put(key, new MutableLiveData<>());

        Objects.requireNonNull(liveDataMap.get(key)).observe(lifecycleOwner, observer);
    }

    public void postValue(String key) {
        if (liveDataMap.containsKey(key))
            Objects.requireNonNull(liveDataMap.get(key)).postValue(new HashMap<>() {{
                put(key, new HashMap<>());
            }});
    }

    public void postValue(String key, Map<String, Object> params) {
        if (liveDataMap.containsKey(key))
            Objects.requireNonNull(liveDataMap.get(key)).postValue(params);
    }

    public void setFunction(Connection connection) {
        map.add(connection);
    }


    public List<Connection> getConnection(String key) {
        return map.stream().filter(i -> i.getKey().equals(key)).collect(Collectors.toList());
    }

    public void invoke(String key, SuperService.SuperServiceBinder superServiceBinder, Map<String, Object> params) {
        postValue(key, params);

        if (getConnection(key) == null) return;
        for (Connection conn : getConnection(key)) {
            conn.getFunction().accept(superServiceBinder, params);
        }
    }

    public void invoke(String key, SuperService.SuperServiceBinder superServiceBinder) {
        postValue(key);

        if (getConnection(key) == null) return;
        for (Connection conn : getConnection(key)) {
            conn.getFunction().accept(superServiceBinder, new HashMap<>());
        }
    }

    @Override
    public String toString() {
        return "Communication{" +
                "map=" + map +
                '}';
    }
}
