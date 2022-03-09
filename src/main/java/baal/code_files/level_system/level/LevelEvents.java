package baal.code_files.level_system.level;

import baal.code_files.level_system.event.Event;
import lombok.Data;

import java.util.Vector;

@Data
public class LevelEvents {
    Vector<Event> levelEventsVector;
}
