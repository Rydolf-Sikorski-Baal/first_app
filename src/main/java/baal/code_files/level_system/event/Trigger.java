package baal.code_files.level_system.event;

import baal.code_files.entity_movement.PositionInfo;
import baal.code_files.level_system.level.LevelInterface;

import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class Trigger {
    protected Predicate<PositionInfo> predicate;
    protected Consumer<LevelInterface> ifTrue;
    protected Consumer<LevelInterface> ifFalse;

    public boolean check(PositionInfo positionInfo){return predicate.test(positionInfo);}
    public void ifTrue(LevelInterface level){ifTrue.accept(level);}
    public void ifFalse(LevelInterface level){ifFalse.accept(level);}
}