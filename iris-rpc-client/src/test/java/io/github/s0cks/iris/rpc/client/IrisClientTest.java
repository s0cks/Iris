package io.github.s0cks.iris.rpc.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import org.junit.Test;

import static org.junit.Assert.*;

public final class IrisClientTest{
    public static void main(String... args) throws Exception{
        Injector injector = Guice.createInjector(
                new IrisClientModule(),
                new AbstractModule() {
                    @Override
                    protected void configure(){
                        this.bind(IrisClientConfig.class)
                                .toProvider(TestClientConfigProvider.class);
                    }

                    @Provides
                    public Gson getGson(){
                        return new GsonBuilder()
                                .setPrettyPrinting()
                                .create();
                    }
                }
        );
        IrisClient client = injector.getInstance(IrisClient.class);
        client.run();
    }
}