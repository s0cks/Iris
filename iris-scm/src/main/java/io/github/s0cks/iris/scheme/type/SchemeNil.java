package io.github.s0cks.iris.scheme.type;

public final class SchemeNil
implements SchemeObject{
    public static final SchemeObject NIL = new SchemeNil();

    private SchemeNil(){}

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SchemeNil;
    }
}