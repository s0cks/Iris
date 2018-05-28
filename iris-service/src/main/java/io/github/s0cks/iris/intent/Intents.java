package io.github.s0cks.iris.intent;

import java.util.HashSet;
import java.util.Set;

public final class Intents{
    private final Set<Intent> intents = new HashSet<>();

    public void define(Intent intent){
        this.intents.add(intent);
    }

    public Intent get(String name){
        return this.intents.stream()
                .filter((intent)->intent.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("Cannot find intent: " + name));
    }
}