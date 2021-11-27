package baal.code_files.level_system.event;

import baal.code_files.entity_movement.PositionInfo;
import baal.code_files.level_system.level.Level;

import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class Trigger {
    protected Predicate<PositionInfo> predicate;
    protected Consumer<Level> ifTrue;
    protected Consumer<Level> ifFalse;

    public boolean check(PositionInfo positionInfo){return predicate.test(positionInfo);}
    public void ifTrue(Level level){ifTrue.accept(level);}
    public void ifFalse(Level level){ifFalse.accept(level);}
}