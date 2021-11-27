package baal.code_files.level_system.event;

import baal.code_files.entity_movement.PositionInfo;

import java.util.function.Predicate;

public abstract class Trigger {
    protected Predicate<PositionInfo> predicate;

    public boolean check(PositionInfo positionInfo){return predicate.test(positionInfo);}
}