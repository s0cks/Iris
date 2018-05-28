package io.github.s0cks.iris.scheme.type;

public interface SchemeObject{
    default boolean isNil(){
        return this.equals(SchemeNil.NIL);
    }

    default boolean isSymbol(){
        return this instanceof SchemeSymbol;
    }

    default boolean isList(){
        return this instanceof SchemeList;
    }

    default boolean isString(){
        return this instanceof SchemeString;
    }

    default boolean isNumber(){
        return this instanceof SchemeNumber;
    }

    default boolean isBoolean(){
        return this instanceof SchemeBoolean;
    }

    default boolean isTrue(){
        return this.isBoolean() && this.equals(SchemeBoolean.TRUE);
    }

    default boolean isFalse(){
        return this.isBoolean() && this.equals(SchemeBoolean.FALSE);
    }
}