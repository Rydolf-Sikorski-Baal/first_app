package baal.code_files.level_system.level;

import baal.code_files.blocks.Blocks;
import baal.code_files.graphics_system.LevelCellsSizes;

public interface LevelInterface {
    LevelSettingsInterface getLevelSettings();
    LevelEntitiesInterface getLevelEntities();
    LevelConnectionsInterface getLevelConnections();
    Blocks[][] getLevelMap();
    LevelCellsSizes getLevelCellsSizes();
    Blocks getBlockByCoords(int x, int y);

    void setLevelCellsSizes(LevelCellsSizes levelCellsSizes);

    void changeThisCellTo(int x, int y, Blocks block);
}