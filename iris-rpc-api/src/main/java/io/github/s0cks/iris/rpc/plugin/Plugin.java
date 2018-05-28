package io.github.s0cks.iris.rpc.plugin;

import com.google.gson.annotations.SerializedName;

import java.util.concurrent.atomic.AtomicReference;

public final class Plugin{
    private final String name;
    private final String version;

    @SerializedName("class")
    private final String pluginClass;

    private transient AtomicReference<Object> instanceRef = new AtomicReference<>();

    private Plugin(String name, String version, String pluginClass) {
        this.name = name;
        this.version = version;
        this.pluginClass = pluginClass;
    }

    void setInstance(Object value){
        this.instanceRef.set(value);
    }

    public Object getInstance(){
        return this.instanceRef.get();
    }

    public String getName(){
        return this.name;
    }

    public String getVersion(){
        return this.version;
    }

    public String getPluginClass(){
        return this.pluginClass;
    }
}