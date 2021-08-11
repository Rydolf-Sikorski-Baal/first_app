package baal.code_files.graphics_system;

import baal.code_files.level_system.level.LevelInterface;

public interface DrawerInterface {
    void drawThisLevel(javafx.scene.canvas.Canvas canvas, LevelInterface level);
    LevelCellsSizes getLevelCellsSizesForThisLevel(LevelInterface level);
}
