package io.github.s0cks.iris.intent;

public final class IntentSlot{
    private final String name;
    private final IntentSlotType type;

    public IntentSlot(String name, IntentSlotType type) {
        this.name = name;
        this.type = type;
    }

    public String getName(){
        return this.name;
    }

    public IntentSlotType getType(){
        return this.type;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof IntentSlot)) return false;
        IntentSlot other = (IntentSlot) obj;
        return this.name.equals(other.name) &&
                this.type.equals(other.type);
    }
}