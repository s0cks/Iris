package io.github.s0cks.iris.rpc.event;

import com.google.protobuf.Message;
import io.github.s0cks.iris.rpc.IrisPacket;
import io.netty.channel.Channel;

public abstract class ConnectEvent
extends Event{
    private final Channel channel;

    public ConnectEvent(Channel channel){
        this.channel = channel;
    }

    public Channel getChannel(){
        return this.channel;
    }

    public void send(Message msg){
        this.getChannel().writeAndFlush(new IrisPacket<>(msg));
    }

    @Cancellable
    public static final class ClientConnectEvent
    extends ConnectEvent{
        public ClientConnectEvent(Channel channel) {
            super(channel);
        }
    }

    @Cancellable
    public static final class ServerConnectEvent
    extends ConnectEvent{
        public ServerConnectEvent(Channel channel) {
            super(channel);
        }
    }
}