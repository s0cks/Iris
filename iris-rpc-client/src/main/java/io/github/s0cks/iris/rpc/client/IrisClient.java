package io.github.s0cks.iris.rpc.client;

import io.github.s0cks.iris.rpc.channel.IrisChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Singleton
public final class IrisClient{
    private final Logger logger = LoggerFactory.getLogger(IrisClient.class);
    private final IrisClientConfig config;
    private final IrisChannelInitializer initializer;
    private EventLoopGroup workerGroup;
    private Status status = Status.SHUTDOWN;
    private Lock statusLock = new ReentrantLock();

    @Inject
    private IrisClient(IrisClientConfig config, IrisChannelInitializer initializer){
        this.initializer = initializer;
        this.config = config;
    }

    public Status getStatus(){
        this.statusLock.lock();
        try{
            return this.status;
        } finally{
            this.statusLock.unlock();
        }
    }

    public void setStatus(Status status){
        this.statusLock.lock();
        try{
            this.status = status;
        } finally{
            this.statusLock.unlock();
        }
    }

    public void shutdown(){
        if(this.getStatus() != Status.RUNNING) return;
        this.setStatus(Status.SHUTDOWN);
        this.workerGroup.shutdownGracefully();
    }

    public void run() throws Exception{
        if(this.getStatus() == Status.RUNNING) return;
        this.setStatus(Status.RUNNING);
        this.workerGroup = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(this.workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(this.initializer)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_REUSEADDR, true);
            Channel ch = b.connect(this.config.getInetAddress())
                    .sync()
                    .channel();
            ch.closeFuture().sync();
        } finally{
            this.shutdown();
        }
    }

    public enum Status{
        RUNNING,
        SHUTDOWN;
    }
}