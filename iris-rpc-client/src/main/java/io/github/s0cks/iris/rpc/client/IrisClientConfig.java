package io.github.s0cks.iris.rpc.client;

import java.net.InetSocketAddress;

public final class IrisClientConfig{
    private final String address;
    private final int port;

    private IrisClientConfig(String address, int port){
        this.address = address;
        this.port = port;
    }

    private IrisClientConfig(Builder builder){
        this(builder.address, builder.port);
    }

    public String getAddress(){
        return this.address;
    }

    public int getPort(){
        return this.port;
    }

    public InetSocketAddress getInetAddress(){
        return new InetSocketAddress(this.address, this.port);
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static final class Builder{
        private String address = null;
        private int port = -1;

        private Builder(){}

        public Builder setAddress(String address){
            this.address = address;
            return this;
        }

        public Builder setPort(int port){
            this.port = port;
            return this;
        }

        public IrisClientConfig build(){
            return new IrisClientConfig(this);
        }
    }
}