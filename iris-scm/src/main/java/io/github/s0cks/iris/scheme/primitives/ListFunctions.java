package io.github.s0cks.iris.scheme.primitives;

import io.github.s0cks.iris.scheme.SchemeInterpreter;
import io.github.s0cks.iris.scheme.type.SchemeFunction;
import io.github.s0cks.iris.scheme.type.SchemeList;
import io.github.s0cks.iris.scheme.type.SchemeNil;
import io.github.s0cks.iris.scheme.type.SchemeObject;

public final class ListFunctions{
    public static final SchemeFunction CONS = new SchemeCons();

    private ListFunctions(){}

    private static final class SchemeCons
    extends SchemeFunction{
        @Override
        public SchemeObject apply(SchemeInterpreter scheme, SchemeObject args){
            SchemeObject a = this.car(args);
            args = this.cdr(args);
            SchemeObject b = this.car(args);
            args = this.cdr(args);

            SchemeObject result = SchemeList.of(SchemeList.of(a, b), SchemeNil.NIL);
            while(!args.isNil()){
                result = SchemeList.of(result, this.car(args));
                args = this.cdr(args);
            }

            return result;
        }
    }
}