package io.github.s0cks.iris.intent;

import java.util.Arrays;
import java.util.Optional;

public enum IntentSlotTypes
implements IntentSlotType{
    NUMBER("Iris.Number");

    private final String value;

    IntentSlotTypes(String value){
        this.value = value;
    }

    public static Optional<IntentSlotType> find(String name){
        return Arrays.stream(values())
                .filter((type)->type.value.equalsIgnoreCase(name))
                .map((type)->(IntentSlotType) type)
                .findFirst();
    }

    @Override
    public String toString(){
        return this.value;
    }
}