package io.github.s0cks.iris.rpc.message;

import com.google.protobuf.Message;

import java.util.Objects;

public final class MessageID<T extends Message>
implements Comparable<MessageID>{
    private final String id;
    private final Class<T> msgClass;

    MessageID(Class<T> msgClass){
        this.id = msgClass.getName();
        this.msgClass = msgClass;
    }

    public String get(){
        return this.id;
    }

    public Class<T> getMessageClass(){
        return this.msgClass;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof MessageID)) return false;
        MessageID<?> msgID = (MessageID) obj;
        return this.get().equals(msgID.get()) &&
                this.getMessageClass().equals(msgID.getMessageClass());
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.msgClass);
    }

    @Override
    public int compareTo(MessageID o) {
        return this.id.compareTo(o.get());
    }

    public static <Type extends Message> MessageID<Type> of(Class<Type> msgClass){
        return new MessageID<>(msgClass);
    }

    @SuppressWarnings("unchecked")
    public static <Type extends Message> MessageID<Type> of(String msgClass){
        try{
            return of((Class<Type>) Class.forName(msgClass));
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}