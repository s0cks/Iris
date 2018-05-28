package io.github.s0cks.iris.module;

import javax.inject.Provider;
import javax.inject.Singleton;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

@Singleton
final class IrisServiceScriptEngineProvider
implements Provider<ScriptEngine> {
    private final ScriptEngineManager manager = new ScriptEngineManager();

    @Override
    public ScriptEngine get() {
        return this.manager.getEngineByExtension("scm");
    }
}