package io.github.s0cks.iris.rpc.event;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Event{
    private final boolean isCancellable;
    private final AtomicBoolean cancelled = new AtomicBoolean(false);

    protected Event(){
        this.isCancellable = this.getClass().isAnnotationPresent(Cancellable.class);
    }

    public final boolean isCancellable(){
        return this.isCancellable;
    }

    public final boolean isCancelled(){
        return this.cancelled.get();
    }

    public final void cancel(){
        if(!this.isCancellable()) throw new IllegalStateException("Cannot cancel event: " + this.getClass().getName());
        this.cancelled.set(true);
    }
}