package io.github.s0cks.iris.scheme;

import io.github.s0cks.iris.scheme.type.SchemeNumber;
import org.junit.Assert;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class SchemeInterpreterTest{
    @Test
    public void testExecution() throws ScriptException {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByExtension("scm");
        Assert.assertEquals(new SchemeNumber(20), engine.eval("(+ 10 10)"));
    }
}