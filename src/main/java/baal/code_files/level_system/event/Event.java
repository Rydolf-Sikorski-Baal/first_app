package baal.code_files.level_system.event;

import baal.code_files.entities.entities_tree.Hero;
import baal.code_files.level_system.level.LevelInterface;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Event {
    @Getter
    @Setter protected ArrayList<Term<Hero>> termsList;
    @Getter
    @Setter protected Consumer<LevelInterface> ifTrue;
    @Getter
    @Setter protected Consumer<LevelInterface> ifFalse;

    public Event() {}

    public boolean check(LevelInterface level){
        boolean res = true;
        for (Term<?> term : termsList)
            res = res && term.check(level);
        return true;
    }
    public void ifTrue(LevelInterface level){ifTrue.accept(level);}
    public void ifFalse(LevelInterface level){ifFalse.accept(level);}
}