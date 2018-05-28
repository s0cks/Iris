package io.github.s0cks.iris.module;

import com.google.common.eventbus.EventBus;
import io.github.s0cks.iris.IrisCLIEventHandler;

import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
final class IrisCLIEventBusProvider
implements Provider<EventBus>{
    @Override
    public EventBus get(){
        EventBus bus = new EventBus();
        bus.register(new IrisCLIEventHandler());
        return bus;
    }
}