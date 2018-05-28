package io.github.s0cks.iris.scheme.primitives;

import io.github.s0cks.iris.scheme.SchemeInterpreter;
import io.github.s0cks.iris.scheme.type.SchemeFunction;
import io.github.s0cks.iris.scheme.type.SchemeNil;
import io.github.s0cks.iris.scheme.type.SchemeNumber;
import io.github.s0cks.iris.scheme.type.SchemeObject;

public final class MathFunctions{
    public static final SchemeFunction ADD = new SchemeAdd();
    public static final SchemeFunction SUBTRACT = new SchemeSubtract();
    public static final SchemeFunction MULTIPLY = new SchemeMultiply();
    public static final SchemeFunction DIVIDE = new SchemeDivide();

    private MathFunctions(){}

    private static final class SchemeAdd
    extends SchemeFunction{
        @Override
        public SchemeObject apply(SchemeInterpreter scheme, SchemeObject args){
            SchemeObject result = new SchemeNumber(0);
            while(!args.isNil() && result.isNumber()){
                if(args.isList()){
                    result = ((SchemeNumber) result).add(this.car(args));
                    args = this.cdr(args);
                } else{
                    result = ((SchemeNumber) result).add(args);
                    args = SchemeNil.NIL;
                }
            }
            return result;
        }
    }

    private static final class SchemeSubtract
    extends SchemeFunction{
        @Override
        public SchemeObject apply(SchemeInterpreter scheme, SchemeObject args){
            SchemeObject result = new SchemeNumber(0);
            while (!args.isNil() && result.isNumber()){
                if(args.isList()){
                    result = ((SchemeNumber) result).subtract(this.car(args));
                    args = this.cdr(args);
                } else{
                    result = ((SchemeNumber) result).subtract(args);
                    args = SchemeNil.NIL;
                }
            }
            return result;
        }
    }

    private static final class SchemeMultiply
    extends SchemeFunction{
        @Override
        public SchemeObject apply(SchemeInterpreter scheme, SchemeObject args){
            SchemeObject result = new SchemeNumber(0);
            while(!args.isNil() && result.isNumber()){
                if(args.isList()){
                    result = ((SchemeNumber) result).multiply(this.car(args));
                    args = this.cdr(args);
                } else{
                    result = ((SchemeNumber) result).multiply(args);
                    args = SchemeNil.NIL;
                }
            }
            return result;
        }
    }

    private static final class SchemeDivide
    extends SchemeFunction{
        @Override
        public SchemeObject apply(SchemeInterpreter scheme, SchemeObject args){
            SchemeObject result = new SchemeNumber(0);
            while(!args.isNil() && result.isNumber()){
                if(args.isList()){
                    result = ((SchemeNumber) result).divide(this.car(args));
                    args = this.cdr(args);
                } else{
                    result = ((SchemeNumber) result).divide(args);
                    args = SchemeNil.NIL;
                }
            }
            return result;
        }
    }
}