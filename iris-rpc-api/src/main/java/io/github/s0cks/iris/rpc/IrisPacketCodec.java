package io.github.s0cks.iris.rpc;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.protobuf.Message;
import io.github.s0cks.iris.rpc.message.MessageID;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Singleton
public final class IrisPacketCodec
extends ByteToMessageCodec<IrisPacket>{
    private final LoadingCache<MessageID<?>, MessageDecoder> decoderCache;

    @Inject
    private IrisPacketCodec(){
        this.decoderCache = CacheBuilder.newBuilder()
                .maximumSize(128)
                .build(new CacheLoader<MessageID<?>, MessageDecoder>() {
                    @Override
                    public MessageDecoder load(MessageID<?> key) throws Exception{
                        return new MessageDecoder(key);
                    }
                });
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, IrisPacket msg, ByteBuf out) throws Exception{
        byte[] header = msg.getMessageIDBytes();
        byte[] payload = msg.getMessageBodyBytes();

        out.writeInt(header.length);
        out.writeBytes(header, 0, header.length);
        out.writeInt(payload.length);
        out.writeBytes(payload, 0, payload.length);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int headerLength = in.readInt();
        byte[] header = new byte[headerLength];
        in.readBytes(header, 0, headerLength);

        int payloadLength = in.readInt();
        byte[] payload = new byte[payloadLength];
        in.readBytes(payload, 0, payloadLength);

        MessageID<?> msgID = MessageID.of(new String(header, StandardCharsets.UTF_8));
        MessageDecoder decoder = this.decoderCache.get(msgID);
        IrisPacket pck = new IrisPacket<>(msgID, decoder.decode(payload));
        out.add(pck);
    }

    private static final class MessageDecoder{
        private final Method parseFrom;

        MessageDecoder(MessageID<?> msgID){
            this.parseFrom = Arrays.stream(msgID.getMessageClass().getDeclaredMethods())
                .filter((m)->m.getName().equals("parseFrom") && m.getParameterTypes().length == 1 && m.getParameterTypes()[0].equals(byte[].class))
                .findFirst()
                .orElseThrow(()->new IllegalStateException("Cannot find parseFrom method"));
        }

        @SuppressWarnings("unchecked")
        public <T extends Message> T decode(byte[] bytes){
            try{
                return (T) this.parseFrom.invoke(null, new Object[]{ bytes });
            } catch(Exception e){
                throw new RuntimeException(e);
            }
        }
    }
}