package io.github.s0cks.iris.intent.scm;

import io.github.s0cks.iris.intent.*;
import io.github.s0cks.iris.scheme.SchemeInterpreter;
import io.github.s0cks.iris.scheme.type.*;

public final class DefineIntentFunction
extends SchemeFunction{
    private final Intents intents;

    public DefineIntentFunction(Intents intents){
        this.intents = intents;
    }

    @Override
    public SchemeObject apply(SchemeInterpreter scheme, SchemeObject args){
        System.out.println(args + " := " + args.getClass().getName());
        if(!args.isList()) throw new IllegalStateException("(defintent <name> <slots...>)");
        if(!this.car(args).isString()) throw new IllegalArgumentException("Name isn't of type string");
        SchemeString name = (SchemeString) this.car(args);
        args = this.cdr(args);

        Intent intent = new Intent(name.getValue());
        this.intents.define(intent);

        if(args.isList()){
            while(!args.isNil()){
                SchemeList s = ((SchemeList) this.car(this.car(args)));
                String slotName = ((SchemeString) this.car(s)).getValue();
                String slotType = ((SchemeString) this.cdr(s)).getValue();
                IntentSlotTypes.find(slotType)
                        .ifPresent((type)->intent.addSlot(new IntentSlot(slotName, type)));
                args = this.cdr(args);
            }
            return SchemeBoolean.TRUE;
        } else if(args.isNil()){
            return SchemeBoolean.TRUE;
        } else{
            return SchemeBoolean.FALSE;
        }
    }
}