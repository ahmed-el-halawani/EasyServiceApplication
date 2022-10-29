package com.example.easyservice;

import android.os.IBinder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Connection {
    private final boolean runOnStart;
    private final Map<String, Object> defaultParams;
    private final String key;
    private final BiConsumer<SuperService.SuperServiceBinder, Map<String, Object>> function;

    public Connection(String key, BiConsumer<SuperService.SuperServiceBinder, Map<String, Object>> function, boolean runOnStart, Map<String, Object> defaultParams) {
        this.key = key;
        this.function = function;
        this.runOnStart = runOnStart;
        this.defaultParams = defaultParams;
    }

    public Connection(String key, BiConsumer<SuperService.SuperServiceBinder, Map<String, Object>> function, boolean runOnStart) {
        this.key = key;
        this.function = function;
        this.runOnStart = runOnStart;
        this.defaultParams = new HashMap<>();
    }

    public Connection(String key, BiConsumer<SuperService.SuperServiceBinder, Map<String, Object>> function) {
        this.key = key;
        this.function = function;
        this.runOnStart = false;
        this.defaultParams = new HashMap<>();
    }

    Connection() {
        this.key = "key";
        this.function = (superServiceBinder, stringObjectMap) -> {
        };
        this.runOnStart = false;
        this.defaultParams = new HashMap<>();
    }

    public String getKey() {
        return key;
    }

    public BiConsumer<SuperService.SuperServiceBinder, Map<String, Object>> getFunction() {
        return function;
    }

    public boolean isRunOnStart() {
        return runOnStart;
    }

    public Map<String, Object> getDefaultParams() {
        return defaultParams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return runOnStart == that.runOnStart && Objects.equals(defaultParams, that.defaultParams) && key.equals(that.key) && function.equals(that.function);
    }

    @Override
    public int hashCode() {
        return Objects.hash(runOnStart, defaultParams, key, function);
    }
}
