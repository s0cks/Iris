package io.github.s0cks.iris.intent;

import io.github.s0cks.iris.intent.scm.DefineIntentFunction;
import io.github.s0cks.iris.scheme.type.SchemeBoolean;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.script.ScriptEngine;
import java.io.InputStream;
import java.io.InputStreamReader;

@Singleton
public final class IntentCompiler{
    private final Intents intents = new Intents();
    private final ScriptEngine engine;

    @Inject
    private IntentCompiler(ScriptEngine engine){
        this.engine = engine;
        this.engine.put("defintent", new DefineIntentFunction(this.intents));
    }

    public Intent getIntent(String name){
        return this.intents.get(name);
    }

    public void compile(InputStream input) throws Exception{
        SchemeBoolean result = (SchemeBoolean) this.engine.eval(new InputStreamReader(input));
        if(!result.truth()){
            throw new IllegalStateException("Couldn't compile intent");
        }
    }
}