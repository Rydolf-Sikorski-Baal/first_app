package baal.code_files.level_system.event;

import baal.code_files.level_system.level.LevelInterface;

public abstract class Term<T> {
    abstract T getRequiredInformationFromLevel(LevelInterface level);
    abstract boolean check(LevelInterface level);
}
