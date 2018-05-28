package io.github.s0cks.iris.rpc.channel;

import io.github.s0cks.iris.rpc.IrisPacketCodec;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class IrisChannelInitializer
extends ChannelInitializer<SocketChannel>{
    private final IrisPacketCodec codec;
    private final IrisChannelHandler handler;

    @Inject
    private IrisChannelInitializer(IrisPacketCodec codec, IrisChannelHandler handler){
        this.codec = codec;
        this.handler = handler;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception{
        ch.pipeline().addLast("codec", this.codec);
        ch.pipeline().addLast("handler", this.handler);
    }
}