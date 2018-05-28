package io.github.s0cks.iris.scheme.type;

import java.util.Objects;

public final class SchemeList
implements SchemeObject{
    private SchemeObject car;
    private SchemeObject cdr;

    private SchemeList(){
        this(null, null);
    }

    private SchemeList(SchemeObject car, SchemeObject cdr){
        this.car = car;
        this.cdr = cdr;
    }

    public void setCar(SchemeObject car){
        this.car = car;
    }

    public SchemeObject getCar(){
        return this.car;
    }

    public void setCdr(SchemeObject cdr){
        this.cdr = cdr;
    }

    public SchemeObject getCdr(){
        return this.cdr;
    }

    public void set(SchemeList other){
        this.car = other.getCar();
        this.cdr = other.getCdr();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof SchemeList)) return false;
        SchemeList other = (SchemeList) obj;
        return this.getCar().equals(other.getCar()) &&
                this.getCdr().equals(other.getCdr());
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.getCar(), this.getCdr());
    }

    @Override
    public String toString(){
        return String.format("(%s, %s)", this.getCar(), this.getCdr());
    }

    public static SchemeList of(SchemeObject... values){
        if(values.length < 2) throw new IllegalStateException("Cannot create list from a single value");
        SchemeList value = new SchemeList(values[0], values[1]);
        if(values.length > 2){
            for(int i = 2; i < values.length; i++){
                value = new SchemeList(values[i], value);
            }
        }
        return value;
    }

    public static SchemeList of(SchemeObject a, SchemeObject b){
        return new SchemeList(a, b);
    }
}