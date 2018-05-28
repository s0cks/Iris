package io.github.s0cks.iris.module;

import com.google.gson.Gson;
import io.github.s0cks.iris.rpc.server.IrisServerConfig;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.io.InputStream;
import java.io.InputStreamReader;

@Singleton
final class IrisServiceConfigProvider
implements Provider<IrisServerConfig> {
    private final Gson gson;

    @Inject
    private IrisServiceConfigProvider(Gson gson){
        this.gson = gson;
    }

    @Override
    public IrisServerConfig get(){
        try(InputStream input = this.getClass().getResourceAsStream("/rpc_config.json")){
            return this.gson.fromJson(new InputStreamReader(input), IrisServerConfig.class);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}