package baal.code_files.graphics_system;

import baal.code_files.entities.entities_tree.Entity;
import baal.code_files.level_system.level.Level;
import org.springframework.stereotype.Component;

// отвечает за всё изображение полотен

@Component
public class Drawer implements DrawerInterface{
    private javafx.scene.canvas.Canvas canvas;
    private Level level;

    private void drawThisLevel(){
        if (level.getLevelCellsSizes() == null)
            level.setLevelCellsSizes(getLevelCellsSizesForThisLevel(level));

        for (int row = 0; row < level.getLevelSettings().getRow(); row++)
            for (int column = 0; column < level.getLevelSettings().getColumn(); column++) {
                double top_left_corner_x = level.getLevelCellsSizes().getHeight() * row;
                double top_left_corner_y = level.getLevelCellsSizes().getWidth() * column;
                (level.getLevelMap()[row][column]).
                        drawYourself(canvas,
                                top_left_corner_x, top_left_corner_y,
                                level.getLevelCellsSizes().getHeight(),
                                level.getLevelCellsSizes().getWidth());
            }

        for (Entity entity : level.getLevelEntities().getEntityVector())
            entity.drawYourself(canvas,
                                level.getLevelCellsSizes().getHeight(),
                                level.getLevelCellsSizes().getWidth());
    }

    @Override
    public void drawThisLevel(javafx.scene.canvas.Canvas canvas, Level level) {
        this.canvas = canvas;
        this.level = level;

        drawThisLevel();
    }

    @Override
    public LevelCellsSizes getLevelCellsSizesForThisLevel(Level level) {
        double height = canvas.getHeight() / (double)level.getLevelSettings().getRow();
        double width = canvas.getWidth() / (double)level.getLevelSettings().getColumn();

        return new LevelCellsSizes(height, width);
    }
}