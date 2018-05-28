package io.github.s0cks.iris.rpc.message.handler;

import com.google.protobuf.Message;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

final class MessageHandlerRegistration<T extends Message>{
    private final Logger logger = LoggerFactory.getLogger(MessageHandlerRegistration.class);
    private final Object instance;
    private final Method invoke;

    MessageHandlerRegistration(Object instance, Method method){
        this.instance = instance;
        this.invoke = method;
        this.invoke.setAccessible(true);
    }

    void handle(Channel channel, T msg){
        try {
            this.invoke.invoke(this.instance, channel, msg);
        } catch(Exception e){
            e.printStackTrace(System.err);
            this.logger.error("Error invoking message handler: {}", e.getMessage());
        }
    }
}