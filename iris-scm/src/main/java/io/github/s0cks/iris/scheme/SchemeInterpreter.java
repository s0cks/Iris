package io.github.s0cks.iris.scheme;

import io.github.s0cks.iris.scheme.primitives.ListFunctions;
import io.github.s0cks.iris.scheme.primitives.MathFunctions;
import io.github.s0cks.iris.scheme.type.*;

public final class SchemeInterpreter{
    public static final SchemeEnvironment GLOBAL = new SchemeEnvironment();
    static{
        // Basic Maths Functions
        GLOBAL.definePrimitive("+", MathFunctions.ADD);
        GLOBAL.definePrimitive("-", MathFunctions.SUBTRACT);
        GLOBAL.definePrimitive("*", MathFunctions.MULTIPLY);
        GLOBAL.definePrimitive("/", MathFunctions.DIVIDE);

        // List Functions
        GLOBAL.definePrimitive("cons", ListFunctions.CONS);
    }

    private static final SchemeSymbol QUOTE = new SchemeSymbol("quote");
    private static final SchemeSymbol BEGIN = new SchemeSymbol("begin");
    private static final SchemeSymbol DEFINE = new SchemeSymbol("define");
    private static final SchemeSymbol LAMBDA = new SchemeSymbol("lambda");
    private static final SchemeSymbol SET = new SchemeSymbol("set!");
    private static final SchemeSymbol IF = new SchemeSymbol("if");
    private static final SchemeSymbol ELSE = new SchemeSymbol("else");
    private static final SchemeSymbol COND = new SchemeSymbol("cond");
    private static final SchemeSymbol MACRO = new SchemeSymbol("macro");
    private static final SchemeSymbol ARROW = new SchemeSymbol("=>");

    SchemeInterpreter(){}

    public SchemeObject eval(SchemeObject func){
        return this.eval(func, GLOBAL);
    }

    private SchemeObject car(SchemeObject obj){
        return obj instanceof SchemeList ?
                ((SchemeList) obj).getCar() :
                SchemeNil.NIL;
    }

    private SchemeObject cdr(SchemeObject obj){
        return obj instanceof SchemeList ?
                ((SchemeList) obj).getCdr() :
                SchemeNil.NIL;
    }

    private SchemeObject caar(SchemeObject obj){
        return this.car(this.car(obj));
    }

    private SchemeObject cadr(SchemeObject obj){
        return this.car(this.cdr(obj));
    }

    private SchemeObject cdar(SchemeObject obj){
        return this.cdr(this.car(obj));
    }

    private SchemeObject caddr(SchemeObject obj){
        return this.car(this.cdr(this.cdr(obj)));
    }

    public SchemeObject eval(SchemeObject func, SchemeEnvironment env){
        while(true){
            if(func instanceof SchemeSymbol){
                return env.lookup(((SchemeSymbol) func).getValue());
            } else if(!(func instanceof SchemeList)){
                return func;
            } else{
                SchemeObject fn = ((SchemeList) func).getCar();
                SchemeObject args = ((SchemeList) func).getCdr();

                if(fn.equals(QUOTE)){
                    return this.car(args);
                } else if(fn.equals(BEGIN)){
                    for(; !this.cdr(args).isNil(); args = this.cdr(args)){
                        this.eval(this.car(args), env);
                    }
                    func = this.car(args);
                } else if(fn.equals(DEFINE)){
                    if(this.car(args).isList()){
                        return env.define(this.caar(args), this.eval(SchemeList.of(LAMBDA, SchemeList.of(this.cdar(args), this.cdr(args))), env));
                    } else{
                        return env.define(this.car(args), this.eval(this.cadr(args), env));
                    }
                } else if(fn.equals(SET)){
                    return env.set(this.car(args), this.eval(this.cadr(args), env));
                } else if(fn.equals(IF)){
                    SchemeObject truth = this.eval(this.car(args), env);
                    func = truth.isTrue() ?
                            this.cadr(args) :
                            this.caddr(args);
                } else if(fn.equals(COND)){
                    func = this.reduce(args, env);
                } else if(fn.equals(LAMBDA)){
                    return new SchemeClosure(this.car(args), this.cdr(args), env);
                } else if(fn.equals(MACRO)){
                    return new SchemeMacro(this.car(args), this.cdr(args), env);
                } else{
                    fn = this.eval(fn, env);
                    if(fn instanceof SchemeMacro){
                        func = ((SchemeMacro) fn).expand(this, ((SchemeList) func), args);
                    } else if(fn instanceof SchemeClosure){
                        SchemeClosure cls = ((SchemeClosure) fn);
                        func = cls.getBody();
                        env = new SchemeEnvironment(cls.getParameters(), this.evalList(args, env), cls.getEnvironment());
                    } else{
                        return ((SchemeFunction) fn).apply(this, this.evalList(args, env));
                    }
                }
            }
        }
    }

    private SchemeObject evalList(SchemeObject list, SchemeEnvironment env){
        if(list.isNil() || !list.isList()){
            return SchemeNil.NIL;
        }
        return SchemeList.of(this.eval(this.car(list), env), this.evalList(this.cdr(list), env));
    }

    private SchemeObject reduce(SchemeObject clauses, SchemeEnvironment env){
        SchemeObject result = SchemeNil.NIL;
        for(;;){
            if(clauses.isNil()) return SchemeBoolean.FALSE;
            SchemeObject clause = this.car(clauses);
            clauses = this.cdr(clauses);
            if(this.car(clause).equals(ELSE) || (result = this.eval(this.car(clause), env)).isTrue()){
                if(this.cdr(clause).isNil()){
                    return SchemeList.of(QUOTE, SchemeList.of(result, SchemeNil.NIL));
                } else if(this.cadr(clause).equals(ARROW)){
                    return SchemeList.of(this.caddr(clause), SchemeList.of(SchemeList.of(QUOTE, SchemeList.of(result, SchemeNil.NIL), SchemeNil.NIL)));
                } else{
                    return SchemeList.of(BEGIN, this.cdr(clause));
                }
            }
        }
    }
}