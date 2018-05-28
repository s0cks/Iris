package io.github.s0cks.iris;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.github.s0cks.iris.module.IrisCLIModule;
import io.github.s0cks.iris.rpc.client.IrisClient;
import io.github.s0cks.iris.rpc.client.IrisClientModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class IrisCommandLine{
    private static final Logger LOGGER = LoggerFactory.getLogger(IrisCommandLine.class);

    public static void main(String... args){
        Injector injector = Guice.createInjector(
                new IrisClientModule(),
                new IrisCLIModule()
        );

        IrisClient client = injector.getInstance(IrisClient.class);
        try {
            client.run();
        } catch (Exception e) {
            LOGGER.error("Error running client: {}", e);
        }
    }
}