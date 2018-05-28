package io.github.s0cks.iris.scheme.type;

import java.util.Objects;

public final class SchemeNumber
extends Number
implements SchemeObject{
    private final double value;

    public SchemeNumber(double value){
        this.value = value;
    }

    public SchemeNumber(){
        this(0.0);
    }

    public double getValue(){
        return this.value;
    }

    public SchemeObject add(SchemeObject obj){
        return obj.isNumber() ?
                new SchemeNumber(this.getValue() + ((SchemeNumber) obj).getValue()) :
                new SchemeString(obj + " is not a number");
    }

    public SchemeObject subtract(SchemeObject obj){
        return obj.isNumber() ?
                new SchemeNumber(this.getValue() - ((SchemeNumber) obj).getValue()) :
                new SchemeString(obj + " is not a number");
    }

    public SchemeObject multiply(SchemeObject obj){
        return obj.isNumber() ?
                new SchemeNumber(this.getValue() * ((SchemeNumber) obj).getValue()) :
                new SchemeString(obj + " is not a number");
    }

    public SchemeObject divide(SchemeObject obj){
        return obj.isNumber() ?
                new SchemeNumber(this.getValue() / ((SchemeNumber) obj).getValue()) :
                new SchemeString(obj + " is not a number");
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(this.value);
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof SchemeNumber)) return false;
        SchemeNumber other = (SchemeNumber) obj;
        return other.getValue() == this.getValue();
    }

    @Override
    public String toString(){
        return String.valueOf(this.value);
    }

    @Override
    public int intValue() {
        return (int) this.value;
    }

    @Override
    public long longValue() {
        return (long) this.value;
    }

    @Override
    public float floatValue() {
        return (float) this.value;
    }

    @Override
    public double doubleValue() {
        return this.value;
    }
}