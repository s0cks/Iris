package io.github.s0cks.iris.rpc;

import com.google.protobuf.Message;
import io.github.s0cks.iris.rpc.message.MessageID;

import java.nio.charset.StandardCharsets;

public final class IrisPacket<T extends Message>{
    private final MessageID<T> msgID;
    private final T msg;

    IrisPacket(MessageID<T> msgID, T msg){
        this.msgID = msgID;
        this.msg = msg;
    }

    @SuppressWarnings("unchecked")
    public IrisPacket(T msg){
        this(MessageID.of((Class<T>) msg.getClass()), msg);
    }

    public MessageID<T> getMessageID(){
        return this.msgID;
    }

    public T getMessage(){
        return this.msg;
    }

    byte[] getMessageIDBytes(){
        return this.msgID.get().getBytes(StandardCharsets.UTF_8);
    }

    byte[] getMessageBodyBytes(){
        return this.msg.toByteArray();
    }

    @Override
    public String toString(){
        return this.msgID.get();
    }
}