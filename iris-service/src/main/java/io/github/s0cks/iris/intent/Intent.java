package io.github.s0cks.iris.intent;

import com.google.common.collect.ImmutableList;

import java.util.LinkedList;
import java.util.List;

public final class Intent{
    private final String name;
    private final List<IntentSlot> slots = new LinkedList<>();

    public Intent(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public List<IntentSlot> getSlots(){
        return ImmutableList.copyOf(this.slots);
    }

    public void addSlot(IntentSlot slot){
        this.slots.add(slot);
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Intent)) return false;
        Intent other = (Intent) obj;
        return this.name.equalsIgnoreCase(other.name) &&
                this.slots.equals(other.slots);
    }
}