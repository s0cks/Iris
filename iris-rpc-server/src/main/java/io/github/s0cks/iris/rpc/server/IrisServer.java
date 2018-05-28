package io.github.s0cks.iris.rpc.server;

import io.github.s0cks.iris.rpc.channel.IrisChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Singleton
public final class IrisServer{
    private final Logger logger = LoggerFactory.getLogger(IrisServer.class);
    private final IrisServerConfig config;
    private final IrisChannelInitializer initializer;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Status status = Status.SHUTDOWN;
    private Lock statusLock = new ReentrantLock();

    @Inject
    private IrisServer(IrisServerConfig config,
                       IrisChannelInitializer initialize){
        this.config = config;
        this.initializer = initialize;
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
        this.bossGroup.shutdownGracefully();
        this.workerGroup.shutdownGracefully();
    }

    public void run() throws Exception{
        if(this.getStatus() == Status.RUNNING) return;
        this.setStatus(Status.RUNNING);
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(this.bossGroup, this.workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(this.initializer)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.SO_KEEPALIVE, true);
            Channel ch = sb.bind(this.config.getPort())
                    .sync()
                    .channel();
            ch.closeFuture().sync();
        } finally{
            this.shutdown();
        }
    }

    enum Status{
        RUNNING,
        SHUTDOWN;
    }
}