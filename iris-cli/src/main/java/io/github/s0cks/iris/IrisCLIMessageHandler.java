package io.github.s0cks.iris;

import io.github.s0cks.iris.rpc.message.Handle;
import io.github.s0cks.iris.rpc.message.IrisMessages;
import io.github.s0cks.iris.rpc.message.MessageHandler;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class IrisCLIMessageHandler
implements MessageHandler{
    private final Logger logger = LoggerFactory.getLogger(IrisCLIMessageHandler.class);

    @Handle
    public void handleResult(Channel channel, IrisMessages.ResultMessage msg){
        switch(msg.getStatus()){
            case 200: this.logger.info("Result := {}", msg.getValue()); break;
            case 500: this.logger.error("Error computing results: {}", msg.getValue()); break;
            default: throw new IllegalStateException("");
        }
    }
}