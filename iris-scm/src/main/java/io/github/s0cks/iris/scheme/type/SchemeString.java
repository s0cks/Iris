package io.github.s0cks.iris.scheme.type;

public final class SchemeString
implements SchemeObject{
    private final String value;

    public SchemeString(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof SchemeString)) return false;
        SchemeString other = (SchemeString) obj;
        return other.getValue().equals(this.getValue());
    }

    @Override
    public String toString() {
        return this.value;
    }
}