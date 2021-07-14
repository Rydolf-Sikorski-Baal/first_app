package code_files.level_system.event;

import code_files.entity_movement.PositionInfo;

public abstract class Trigger {
    public abstract <T extends PositionInfo> boolean isTrue(T );
}