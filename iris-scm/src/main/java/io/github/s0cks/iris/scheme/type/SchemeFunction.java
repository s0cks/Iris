package io.github.s0cks.iris.scheme.type;

import io.github.s0cks.iris.scheme.SchemeInterpreter;

public abstract class SchemeFunction
implements SchemeObject{
    private String name;

    protected SchemeFunction(String name){
        this.name = name;
    }

    protected SchemeFunction(){
        this("lambda");
    }

    public final void setName(String name){
        this.name = name;
    }

    public final String getName(){
        return this.name;
    }

    public final boolean isLambda(){
        return this.getName().equals("lambda");
    }

    protected final SchemeObject car(SchemeObject obj){
        return obj.isList() ? ((SchemeList) obj).getCar() : SchemeNil.NIL;
    }

    protected final SchemeObject cdr(SchemeObject obj){
        return obj.isList() ? ((SchemeList) obj).getCdr() : SchemeNil.NIL;
    }

    public abstract SchemeObject apply(SchemeInterpreter scheme, SchemeObject args);
}