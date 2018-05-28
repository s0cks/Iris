package io.github.s0cks.iris;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.github.s0cks.iris.module.IrisServiceModule;
import io.github.s0cks.iris.rpc.server.IrisServer;
import io.github.s0cks.iris.rpc.server.IrisServerModule;
import joptsimple.OptionParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class IrisService{
    private static final Logger LOGGER = LoggerFactory.getLogger(IrisService.class);

    public static void main(String... args) throws Exception{
        OptionParser parser = new OptionParser();

        Injector injector = Guice.createInjector(
                new IrisServerModule(),
                new IrisServiceModule()
        );
        IrisServer server = injector.getInstance(IrisServer.class);
        server.run();
    }
}