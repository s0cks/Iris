package io.github.s0cks.iris.scheme.type;

import java.util.Objects;

public final class SchemeSymbol
implements SchemeObject{
    private final String value;

    public SchemeSymbol(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public boolean is(String value){
        return this.getValue().equals(value);
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof SchemeSymbol)) return false;
        SchemeSymbol other = (SchemeSymbol) obj;
        return other.getValue().equals(this.getValue());
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.value);
    }

    @Override
    public String toString() {
        return this.value;
    }
}