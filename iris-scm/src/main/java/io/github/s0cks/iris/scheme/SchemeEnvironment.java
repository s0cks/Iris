package io.github.s0cks.iris.scheme;

import io.github.s0cks.iris.scheme.type.*;

public final class SchemeEnvironment{
    private final SchemeEnvironment parent;
    private SchemeObject vars;
    private SchemeObject vals;

    public SchemeEnvironment(SchemeEnvironment parent){
        this.parent = parent;
    }

    public SchemeEnvironment(SchemeObject args, SchemeObject vals, SchemeEnvironment parent){
        this.vars = args;
        this.vals = vals;
        this.parent = parent;
    }

    SchemeEnvironment(){
        this(null);
    }

    public SchemeEnvironment getParent(){
        return this.parent;
    }

    void definePrimitive(String name, SchemeFunction func){
        this.define(new SchemeSymbol(name), func);
    }

    private SchemeObject car(SchemeObject obj){
        return obj.isList() ?
                ((SchemeList) obj).getCar() :
                SchemeNil.NIL;
    }

    private SchemeObject cdr(SchemeObject obj){
        return obj.isList() ?
                ((SchemeList) obj).getCdr() :
                SchemeNil.NIL;
    }

    public SchemeObject lookup(String name){
        SchemeObject vars = this.vars;
        SchemeObject vals = this.vals;

        SchemeSymbol symbol = new SchemeSymbol(name);
        while(!vars.isNil()){
            if(this.car(vars).equals(symbol)){
                return this.car(vals);
            } else if(vars.equals(symbol)){
                return vals;
            } else{
                vars = this.cdr(vars);
                vals = this.cdr(vals);
            }
        }

        if(this.parent != null) return this.parent.lookup(name);
        return new SchemeString("Unbound variable: " + name);
    }

    public SchemeObject set(SchemeObject var, SchemeObject val){
        if(!var.isSymbol()) throw new IllegalArgumentException(var.toString() + " is not a symbol");

        String sym = ((SchemeSymbol) var).getValue();
        SchemeObject varList= this.vars;
        SchemeObject valList = this.vals;

        while(varList != null){
            if(varList.isList() && ((SchemeList) varList).getCar().isSymbol() && ((SchemeSymbol) ((SchemeList) varList).getCar()).is(sym)){
                ((SchemeList) valList).setCar(val);
                return val;
            } else if(varList.isSymbol() && ((SchemeSymbol) varList).is(sym)){
                ((SchemeList) valList).setCdr(val);
                return val;
            } else{
                varList = ((SchemeList) varList).getCdr();
                valList = ((SchemeList) valList).getCdr();
            }
        }

        if(this.parent != null) return this.parent.set(var, val);
        return new SchemeString("Unbound variable: " + sym);
    }

    public SchemeObject define(SchemeObject var, SchemeObject val){
        this.vars = SchemeList.of(var, this.vars);
        this.vals = SchemeList.of(val, this.vals);
        if(val instanceof SchemeFunction && ((SchemeFunction) val).isLambda()) ((SchemeFunction) val).setName(var.toString());
        return var;
    }
}