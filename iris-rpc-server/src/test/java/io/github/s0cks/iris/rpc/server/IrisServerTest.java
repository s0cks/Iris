package io.github.s0cks.iris.rpc.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;

public final class IrisServerTest{
    public static void main(String... args) throws Exception{
        Injector injector = Guice.createInjector(
                new IrisServerModule(),
                new AbstractModule() {
                    @Override
                    protected void configure(){
                        this.bind(IrisServerConfig.class)
                                .toProvider(TestServerConfigProvider.class);
                    }

                    @Provides
                    public Gson getGson(){
                        return new GsonBuilder()
                                .setPrettyPrinting()
                                .create();
                    }
                }
        );

        IrisServer server = injector.getInstance(IrisServer.class);
        server.run();
    }
}