package baal.code_files.graphics_system;

import baal.code_files.entities.entities_tree.Entity;
import baal.code_files.level_system.level.LevelInterface;
import org.springframework.stereotype.Component;

@Component
public class Drawer implements DrawerInterface{
    private javafx.scene.canvas.Canvas canvas;
    private LevelInterface level;

    private void drawThisLevel(){
        if (level.getLevelCellsSizes() == null)
            level.setLevelCellsSizes(getLevelCellsSizesForThisLevel(level));

        for (int row = 0; row <= level.getLevelSettings().getRow(); row++)
            for (int column = 0; column <= level.getLevelSettings().getColumn(); column++) {
                (level.getBlockByCoords(row, column))
                        .drawYourself(canvas,
                                row, column,
                                level.getLevelCellsSizes().getHeight(),
                                level.getLevelCellsSizes().getWidth());
            }

        if (level.getLevelEntities().getEntityVector() != null){
            for (Entity entity : level.getLevelEntities().getEntityVector())
                entity.drawYourself(canvas,
                        level.getLevelCellsSizes().getHeight(),
                        level.getLevelCellsSizes().getWidth());
        }
    }

    @Override
    public void drawThisLevel(javafx.scene.canvas.Canvas canvas, LevelInterface level) {
        this.canvas = canvas;
        this.level = level;

        drawThisLevel();
    }

    @Override
    public LevelCellsSizes getLevelCellsSizesForThisLevel(LevelInterface level) {
        double height = canvas.getHeight() / (double)level.getLevelSettings().getRow();
        double width = canvas.getWidth() / (double)level.getLevelSettings().getColumn();

        return new LevelCellsSizes(height, width);
    }
}