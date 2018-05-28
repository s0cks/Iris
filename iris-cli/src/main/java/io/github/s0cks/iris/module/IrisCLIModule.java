package io.github.s0cks.iris.module;

import com.google.common.eventbus.EventBus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import io.github.s0cks.iris.IrisCLIMessageHandler;
import io.github.s0cks.iris.rpc.client.IrisClientConfig;
import io.github.s0cks.iris.rpc.message.MessageHandler;

public final class IrisCLIModule
extends AbstractModule{
    @Override
    protected void configure(){
        this.bind(EventBus.class).toProvider(IrisCLIEventBusProvider.class);
        this.bind(IrisClientConfig.class).toProvider(IrisCLIConfigProvider.class);

        Multibinder<MessageHandler> messageHandlers = Multibinder.newSetBinder(this.binder(), MessageHandler.class);
        messageHandlers.addBinding()
                .to(IrisCLIMessageHandler.class);
    }

    @Provides
    public Gson getGson(){
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }
}