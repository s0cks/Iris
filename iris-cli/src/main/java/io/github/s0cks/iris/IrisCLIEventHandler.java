package io.github.s0cks.iris;

import com.google.common.eventbus.Subscribe;
import io.github.s0cks.iris.rpc.event.ConnectEvent;
import io.github.s0cks.iris.rpc.message.IrisMessages;

public final class IrisCLIEventHandler {
    @Subscribe
    public void onClientConnect(ConnectEvent.ClientConnectEvent e){
        e.send(
                IrisMessages.ComputeMessage.newBuilder()
                    .setValue("(+ 10 10)")
                    .build()
        );
    }
}