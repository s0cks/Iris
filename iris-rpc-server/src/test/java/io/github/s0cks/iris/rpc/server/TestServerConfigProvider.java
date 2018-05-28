package io.github.s0cks.iris.rpc.server;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Provider;
import java.io.InputStream;
import java.io.InputStreamReader;

final class TestServerConfigProvider
implements Provider<IrisServerConfig>{
    private final Gson gson;

    @Inject
    private TestServerConfigProvider(Gson gson){
        this.gson = gson;
    }

    @Override
    public IrisServerConfig get(){
        try(InputStream input = getClass().getResourceAsStream("/config.json")){
            return this.gson.fromJson(new InputStreamReader(input), IrisServerConfig.class);
        } catch(Exception e){
            return IrisServerConfig.newBuilder()
                    .setPort(8080)
                    .build();
        }
    }
}