package io.github.s0cks.iris.rpc.side;

import com.google.common.base.Preconditions;

import java.io.Serializable;
import java.lang.annotation.Annotation;

final class SideOnlyImpl
implements SideOnly, Serializable{
    private final Side value;

    SideOnlyImpl(Side value){
        this.value = Preconditions.checkNotNull(value, "value");
    }

    @Override
    public Side value() {
        return this.value;
    }

    @Override
    public int hashCode(){
        return (127 * "value".hashCode()) ^ this.value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof SideOnly)) return false;
        SideOnly other = (SideOnly) obj;
        return this.value().equals(other.value());
    }

    @Override
    public String toString(){
        return String.format(
                "@%s(value='%s')",
                SideOnly.class.getName(),
                this.value()
        );
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return SideOnly.class;
    }
}