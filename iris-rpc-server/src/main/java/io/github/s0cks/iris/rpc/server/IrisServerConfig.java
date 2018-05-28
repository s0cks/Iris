package io.github.s0cks.iris.rpc.server;

import com.google.common.base.Preconditions;

public final class IrisServerConfig{
    private final int port;

    private IrisServerConfig(int port) {
        this.port = port;
    }

    private IrisServerConfig(Builder builder){
        this(builder.port);
    }

    public int getPort(){
        return this.port;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static final class Builder{
        private int port = -1;

        private Builder(){}

        public Builder setPort(int port){
            this.port = port;
            return this;
        }

        public IrisServerConfig build(){
            Preconditions.checkArgument(this.port == -1, "Port not assigned");
            return new IrisServerConfig(this);
        }
    }
}