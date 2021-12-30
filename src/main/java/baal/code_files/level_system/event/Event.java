package baal.code_files.level_system.event;

import baal.code_files.level_system.level.LevelInterface;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Event {
    @Getter protected final ArrayList<Term<?>> termsList;
    @Getter protected final Consumer<LevelInterface> ifTrue;
    @Getter protected final Consumer<LevelInterface> ifFalse;

    public Event(@NonNull ArrayList<Term<?>> termsList,
                 @NonNull Consumer<LevelInterface> ifTrue,
                 @NonNull Consumer<LevelInterface> ifFalse) {
        this.termsList = termsList;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
    }

    public boolean check(LevelInterface level){
        boolean res = true;
        for (Term<?> term : termsList)
            res = res && term.check(level);
        return res;
    }
    public void ifTrue(LevelInterface level){ifTrue.accept(level);}
    public void ifFalse(LevelInterface level){ifFalse.accept(level);}
}