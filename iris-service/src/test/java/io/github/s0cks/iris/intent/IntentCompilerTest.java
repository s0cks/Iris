package io.github.s0cks.iris.intent;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.github.s0cks.iris.module.IrisServiceModule;
import io.github.s0cks.iris.rpc.server.IrisServerModule;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

public class IntentCompilerTest {
    private void printIntent(Intent intent){
        System.out.println("## Intent:");
        System.out.println("\t+ Name := " + intent.getName());
        System.out.println("\t+ Slots :=");
        List<IntentSlot> slots = intent.getSlots();
        for(int i = 0; i < slots.size(); i++){
            IntentSlot slot = slots.get(i);
            System.out.println("\t\t* #" + (i + 1) + ": " + slot.getName() + " - " + slot.getType());
        }
        System.out.println("#######");
    }

    @Test
    public void compile() throws Exception{
        Injector injector = Guice.createInjector(
                new IrisServerModule(),
                new IrisServiceModule()
        );
        try(InputStream input = this.getClass().getResourceAsStream("/test_intent.scm")){
            IntentCompiler compiler = injector.getInstance(IntentCompiler.class);
            compiler.compile(input);

            printIntent(compiler.getIntent("Iris.Test"));
        }
    }
}