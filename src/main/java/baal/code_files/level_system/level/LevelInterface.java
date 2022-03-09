package baal.code_files.level_system.level;

import baal.code_files.blocks.Blocks;
import baal.code_files.graphics_system.LevelCellsSizes;
import baal.code_files.level_system.event.Event;

public interface LevelInterface {
    LevelSettingsInterface getLevelSettings();
    LevelEntitiesInterface getLevelEntities();
    LevelConnectionsInterface getLevelConnections();
    LevelEvents getLevelEvents();
    Blocks[][] getLevelMap();
    LevelCellsSizes getLevelCellsSizes();
    Blocks getBlockByCords(int x, int y);

    void setLevelCellsSizes(LevelCellsSizes levelCellsSizes);

    void changeThisCellTo(int x, int y, Blocks block);

    void addEvent(Event event);
}