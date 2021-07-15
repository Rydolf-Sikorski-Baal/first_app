package baal.code_files.graphics_system;

import baal.code_files.level_system.level.Level;

import java.awt.*;

public interface DrawerInterface {
    void drawThisLevel(javafx.scene.canvas.Canvas canvas, Level level);
    LevelCellsSizes getLevelCellsSizesForThisLevel(Level level);
}
