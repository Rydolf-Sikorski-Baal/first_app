package baal.code_files.level_system.level;

import baal.code_files.level_system.event.Trigger;
import lombok.Data;

import java.util.Vector;

@Data
public class LevelTriggers {
    Vector<Trigger> levelTriggersVector;
}
