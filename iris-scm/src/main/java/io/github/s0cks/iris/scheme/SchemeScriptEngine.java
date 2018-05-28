package io.github.s0cks.iris.scheme;

import io.github.s0cks.iris.scheme.stream.SchemeParser;
import io.github.s0cks.iris.scheme.type.SchemeObject;
import io.github.s0cks.iris.scheme.type.SchemeSymbol;
import io.github.s0cks.iris.scheme.utils.ReaderInputStream;

import javax.script.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public final class SchemeScriptEngine
extends AbstractScriptEngine{
    private final SchemeInterpreter scheme;

    public SchemeScriptEngine(SchemeInterpreter scheme){
        this.scheme = scheme;
    }

    @Override
    public Object eval(String script, ScriptContext context) throws ScriptException {
        try(SchemeParser parser = new SchemeParser(new ByteArrayInputStream(script.getBytes(StandardCharsets.UTF_8)))){
            SchemeObject code = parser.parse();
            return this.scheme.eval(code);
        } catch(IOException e){
            throw new ScriptException("Cannot run script: " + e.getMessage());
        }
    }

    @Override
    public Object eval(Reader reader, ScriptContext context) throws ScriptException{
        try(SchemeParser parser = new SchemeParser(new ReaderInputStream(reader))){
            SchemeObject code = parser.parse();
            return this.scheme.eval(code);
        } catch(IOException e){
            throw new ScriptException("Cannot run script: " + e.getMessage());
        }
    }

    @Override
    public void put(String key, Object value){
        if(!(value instanceof SchemeObject)) throw new IllegalArgumentException("Not a scheme object");
        SchemeInterpreter.GLOBAL.define(new SchemeSymbol(key), (SchemeObject) value);
    }

    @Override
    public Bindings createBindings() {
        return new SimpleBindings();
    }

    @Override
    public ScriptEngineFactory getFactory() {
        return new SchemeScriptEngineFactory();
    }
}