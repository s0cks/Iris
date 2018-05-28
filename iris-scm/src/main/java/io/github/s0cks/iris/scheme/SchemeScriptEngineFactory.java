package io.github.s0cks.iris.scheme;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SchemeScriptEngineFactory
implements ScriptEngineFactory{
    private static final String NAME = "iScheme";
    private static final String ENGINE = "Scheme Interpreter";
    private static final String ENGINE_VERSION = "1.0";
    private static final String LANGUAGE = "scm";
    private static final String LANGUAGE_VERSION = "1.0";

    private static final Map<String, Object> params = new HashMap<>();
    static{
        params.put(ScriptEngine.NAME, NAME);
        params.put(ScriptEngine.ENGINE, ENGINE);
        params.put(ScriptEngine.ENGINE_VERSION, ENGINE_VERSION);
        params.put(ScriptEngine.LANGUAGE, LANGUAGE);
        params.put(ScriptEngine.LANGUAGE_VERSION, LANGUAGE_VERSION);
    }

    @Override
    public String getEngineName(){
        return NAME;
    }

    @Override
    public String getEngineVersion() {
        return ENGINE_VERSION;
    }

    @Override
    public List<String> getExtensions() {
        return Arrays.asList("scm", "lisp");
    }

    @Override
    public List<String> getMimeTypes() {
        return Arrays.asList("application/lisp");
    }

    @Override
    public List<String> getNames() {
        return Arrays.asList("scm", "ischeme");
    }

    @Override
    public String getLanguageName() {
        return LANGUAGE;
    }

    @Override
    public String getLanguageVersion() {
        return LANGUAGE_VERSION;
    }

    @Override
    public Object getParameter(String key) {
        return params.get(key);
    }

    @Override
    public String getMethodCallSyntax(String obj, String m, String... args) {
        StringBuilder buff = new StringBuilder();
        buff.append("(").append(m);
        for (int i = 0; i < args.length; i++){
            buff.append(args[i]);
            if(i < args.length - 1){
                buff.append(" ");
            }
        }
        return buff.append(")").toString();
    }

    @Override
    public String getOutputStatement(String toDisplay) {
        return toDisplay;
    }

    @Override
    public String getProgram(String... statements) {
        return statements[0];
    }

    @Override
    public ScriptEngine getScriptEngine() {
        return new SchemeScriptEngine(new SchemeInterpreter());
    }
}