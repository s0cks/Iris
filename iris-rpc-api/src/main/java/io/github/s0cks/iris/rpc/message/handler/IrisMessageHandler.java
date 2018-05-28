package io.github.s0cks.iris.rpc.message.handler;

import com.google.protobuf.Message;
import io.github.s0cks.iris.rpc.message.Handle;
import io.github.s0cks.iris.rpc.message.MessageHandler;
import io.github.s0cks.iris.rpc.message.MessageID;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

@Singleton
public final class IrisMessageHandler{
    private final Logger logger = LoggerFactory.getLogger(IrisMessageHandler.class);
    private final Map<MessageID<?>, List<MessageHandlerRegistration<?>>> handlers;

    @Inject
    @SuppressWarnings("unchecked")
    IrisMessageHandler(Set<MessageHandler> handlers){
        this.handlers = new HashMap<>();
        handlers.forEach((handler)->{
            Arrays.stream(handler.getClass().getDeclaredMethods())
                    .filter((m)->m.isAnnotationPresent(Handle.class))
                    .forEach((m)->{
                        Class<?>[] args = m.getParameterTypes();
                        if(args.length != 2){
                            this.logger.error("@Handle function does not have the required arguments");
                            this.logger.error("@Handle: {}#{}", handler.getClass().getSimpleName(), m.getName());
                        }
                        if(!Channel.class.isAssignableFrom(args[0])){
                            this.logger.error("@Handle function requires argument 1 to be of type Channel");
                            this.logger.error("Expected: {}; Actual: {}", Channel.class.getName(), args[0].getName());
                        }
                        if(!Message.class.isAssignableFrom(args[1])){
                            this.logger.error("@Handle function requires argument 2 to be of type Message");
                            this.logger.error("Expected: {}; Actual: {}", Message.class.getName(), args[1].getName());
                        }

                        Class<? extends Message> msgClass = (Class<? extends Message>) args[1];
                        this.logger.info("Register method handler: {}#{}; for type: {}", handler.getClass().getSimpleName(), m.getName(), msgClass.getName());

                        MessageID<?> msgID = MessageID.of(msgClass);
                        List<MessageHandlerRegistration<?>> registrations = this.handlers.getOrDefault(msgID, new LinkedList<>());
                        registrations.add(new MessageHandlerRegistration<>(handler, m));
                        if(!this.handlers.containsKey(msgID)) this.handlers.put(msgID, registrations);
                    });
        });
    }

    @SuppressWarnings("unchecked")
    public <T extends Message> void post(final Channel channel, final T msg){
        this.logger.info("Posting message: {}", msg.getClass().getName());
        this.handlers.get(MessageID.of((Class<T>) msg.getClass()))
                .forEach((handler)->((MessageHandlerRegistration<T>) handler).handle(channel, msg));
    }
}