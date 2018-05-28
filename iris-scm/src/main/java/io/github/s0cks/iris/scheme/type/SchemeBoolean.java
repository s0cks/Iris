package io.github.s0cks.iris.scheme.type;

import java.util.Objects;

public final class SchemeBoolean
implements SchemeObject{
    private final boolean value;

    private SchemeBoolean(boolean value){
        this.value = value;
    }

    public boolean truth(){
        return this.value;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof SchemeBoolean)) return false;
        SchemeBoolean other = (SchemeBoolean) obj;
        return other.truth() && this.truth();
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.value);
    }

    @Override
    public String toString(){
        return this.truth() ? "#t" : "#f";
    }

    public static final SchemeBoolean TRUE = new SchemeBoolean(true);
    public static final SchemeBoolean FALSE = new SchemeBoolean(false);

    public static SchemeBoolean of(boolean val){
        return val ? TRUE : FALSE;
    }
}