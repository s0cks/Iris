package io.github.s0cks.iris.rpc.client;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.github.s0cks.iris.rpc.side.Side;

public final class IrisClientModule
extends AbstractModule{
    @Override
    protected void configure(){

    }

    @Provides
    public Side getSide(){
        return Side.CLIENT;
    }
}