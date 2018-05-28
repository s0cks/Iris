package io.github.s0cks.iris.module;

import com.google.gson.Gson;
import io.github.s0cks.iris.rpc.client.IrisClientConfig;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.io.InputStream;
import java.io.InputStreamReader;

@Singleton
final class IrisCLIConfigProvider
implements Provider<IrisClientConfig>{
    private final Gson gson;

    @Inject
    private IrisCLIConfigProvider(Gson gson){
        this.gson = gson;
    }

    @Override
    public IrisClientConfig get(){
        try(InputStream stream = this.getClass().getResourceAsStream("/rpc_config.json")){
            return this.gson.fromJson(new InputStreamReader(stream), IrisClientConfig.class);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}