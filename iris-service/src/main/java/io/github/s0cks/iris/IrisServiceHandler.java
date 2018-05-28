package io.github.s0cks.iris;

import io.github.s0cks.iris.rpc.IrisPacket;
import io.github.s0cks.iris.rpc.message.Handle;
import io.github.s0cks.iris.rpc.message.IrisMessages;
import io.github.s0cks.iris.rpc.message.MessageHandler;
import io.netty.channel.Channel;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.script.ScriptEngine;

@Singleton
public final class IrisServiceHandler
implements MessageHandler{
    private final ScriptEngine engine;

    @Inject
    private IrisServiceHandler(ScriptEngine engine){
        this.engine = engine;
    }

    @Handle
    public void handle(Channel channel, IrisMessages.ComputeMessage msg){
        try{
            Object result = this.engine.eval(msg.getValue());
            channel.writeAndFlush(new IrisPacket<>(
                    IrisMessages.ResultMessage.newBuilder()
                        .setStatus(200)
                        .setValue(result.toString())
                        .build()
            ));
        } catch(Exception e){
            channel.writeAndFlush(new IrisPacket<>(
                    IrisMessages.ResultMessage.newBuilder()
                        .setStatus(500)
                        .setValue(e.getMessage())
                        .build()
            ));
        }
    }
}