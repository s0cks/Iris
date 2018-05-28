package io.github.s0cks.iris.rpc.channel;

import com.google.common.eventbus.EventBus;
import io.github.s0cks.iris.rpc.IrisPacket;
import io.github.s0cks.iris.rpc.event.ConnectEvent;
import io.github.s0cks.iris.rpc.message.handler.IrisMessageHandler;
import io.github.s0cks.iris.rpc.side.Side;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import javax.inject.Inject;
import javax.inject.Singleton;

@ChannelHandler.Sharable
@Singleton
public final class IrisChannelHandler
extends SimpleChannelInboundHandler<IrisPacket>{
    private final EventBus events;
    private final Side side;
    private final IrisMessageHandler messageHandler;

    @Inject
    private IrisChannelHandler(Side side, EventBus bus, IrisMessageHandler msgHandler){
        this.events = bus;
        this.side = side;
        this.messageHandler = msgHandler;
    }

    public EventBus getEventBus(){
        return this.events;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        super.channelActive(ctx);
        ConnectEvent e = this.side == Side.SERVER ?
                new ConnectEvent.ServerConnectEvent(ctx.channel()) :
                new ConnectEvent.ClientConnectEvent(ctx.channel());
        this.getEventBus().post(e);
        if (e.isCancelled()){
            ctx.close();
        }
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, IrisPacket msg) throws Exception{
        this.messageHandler.post(ctx.channel(), msg.getMessage());
    }
}