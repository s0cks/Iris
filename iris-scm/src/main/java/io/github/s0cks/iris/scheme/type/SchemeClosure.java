package io.github.s0cks.iris.scheme.type;

import io.github.s0cks.iris.scheme.SchemeEnvironment;
import io.github.s0cks.iris.scheme.SchemeInterpreter;

public class SchemeClosure
extends SchemeFunction{
    private final SchemeObject params;
    private final SchemeObject body;
    private final SchemeEnvironment env;

    public SchemeClosure(SchemeObject params, SchemeObject body, SchemeEnvironment env){
        this.params = params;
        if(body instanceof SchemeList && ((SchemeList) body).getCdr().isNil()){
            this.body = ((SchemeList) body).getCar();
        } else{
            this.body = SchemeList.of(new SchemeSymbol("begin"), body);
        }
        this.env = env;
    }

    public final SchemeEnvironment getEnvironment(){
        return this.env;
    }

    public final SchemeObject getParameters(){
        return this.params;
    }

    public final SchemeObject getBody(){
        return this.body;
    }

    @Override
    public SchemeObject apply(SchemeInterpreter scheme, SchemeObject args) {
        return scheme.eval(this.body, new SchemeEnvironment(this.params, args, this.env));
    }
}