package io.github.s0cks.iris.rpc.server;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.github.s0cks.iris.rpc.side.Side;

public final class IrisServerModule
extends AbstractModule{
    @Override
    protected void configure(){}

    @Provides
    public Side getSide(){
        return Side.SERVER;
    }
}