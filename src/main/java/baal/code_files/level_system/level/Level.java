package baal.code_files.level_system.level;

// отвечает за структуру и свойства уровней

import baal.code_files.blocks.Blocks;
import baal.code_files.graphics_system.LevelCellsSizes;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
public class Level implements LevelInterface{
    @NonNull private LevelSettings levelSettings;
    @NonNull private LevelEntities levelEntities;
    @NonNull private LevelConnections levelConnections;

    @NonNull private Blocks[][] levelMap;

   private LevelCellsSizes levelCellsSizes;

    @Override
    public Blocks getBlockByCoords(int x, int y) {
        if (x < 0 || levelSettings.getRow() <= x) return Blocks.ExternalBlock;
        if (y < 0 || levelSettings.getColumn() <= y) return Blocks.ExternalBlock;
        if (levelMap == null) throw new RuntimeException("карта уровня не загруженна");
        return levelMap[x][y];
    }
}