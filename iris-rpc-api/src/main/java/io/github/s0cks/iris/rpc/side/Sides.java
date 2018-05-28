package io.github.s0cks.iris.rpc.side;

public final class Sides{
    private Sides(){}

    public static SideOnly client(){
        return new SideOnlyImpl(Side.CLIENT);
    }

    public static SideOnly server(){
        return new SideOnlyImpl(Side.SERVER);
    }
}