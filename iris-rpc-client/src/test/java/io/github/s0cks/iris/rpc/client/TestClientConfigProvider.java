package io.github.s0cks.iris.rpc.client;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.io.InputStream;
import java.io.InputStreamReader;

@Singleton
final class TestClientConfigProvider
implements Provider<IrisClientConfig> {
    private final Gson gson;

    @Inject
    private TestClientConfigProvider(Gson gson){
        this.gson = gson;
    }

    @Override
    public IrisClientConfig get(){
        try(InputStream input = this.getClass().getResourceAsStream("/config.json")){
            return this.gson.fromJson(new InputStreamReader(input), IrisClientConfig.class);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}