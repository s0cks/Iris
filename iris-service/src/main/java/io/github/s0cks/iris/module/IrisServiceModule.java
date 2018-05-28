package io.github.s0cks.iris.module;

import com.google.common.eventbus.EventBus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import io.github.s0cks.iris.IrisServiceHandler;
import io.github.s0cks.iris.rpc.message.MessageHandler;
import io.github.s0cks.iris.rpc.server.IrisServerConfig;

import javax.script.ScriptEngine;

public final class IrisServiceModule
extends AbstractModule{
    @Override
    protected void configure(){
        this.bind(EventBus.class).toInstance(new EventBus());
        this.bind(IrisServerConfig.class).toProvider(IrisServiceConfigProvider.class);
        this.bind(ScriptEngine.class).toProvider(IrisServiceScriptEngineProvider.class);

        Multibinder<MessageHandler> handlers = Multibinder.newSetBinder(this.binder(), MessageHandler.class);
        handlers.addBinding().to(IrisServiceHandler.class);
    }

    @Provides
    public Gson getGson(){
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }
}