package io.github.s0cks.iris.scheme.type;

import io.github.s0cks.iris.scheme.SchemeEnvironment;
import io.github.s0cks.iris.scheme.SchemeInterpreter;

public final class SchemeMacro
extends SchemeClosure{
    public SchemeMacro(SchemeObject params, SchemeObject body, SchemeEnvironment env){
        super(params, body, env);
    }

    public SchemeObject expand(SchemeInterpreter scheme, SchemeList old, SchemeObject args){
        SchemeObject expansion = this.apply(scheme, args);
        if(expansion.isList()){
            old.set((SchemeList) expansion);
        } else{
            old.setCar(new SchemeSymbol("begin"));
            old.setCdr(SchemeList.of(expansion, SchemeNil.NIL));
        }
        return old;
    }

    @Override
    public String toString(){
        return String.format(
                "(lambda (%s) (%s))",
                this.getParameters(),
                this.getBody()
        );
    }
}