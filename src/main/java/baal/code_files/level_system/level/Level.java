package baal.code_files.level_system.level;

// отвечает за структуру и свойства уровней

import baal.code_files.blocks.Blocks;
import baal.code_files.graphics_system.LevelCellsSizes;
import lombok.Data;
import lombok.NonNull;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
public class Level implements LevelInterface{
    @NonNull private LevelSettingsInterface levelSettings;
    @NonNull private LevelEntitiesInterface levelEntities;
    @NonNull private LevelConnectionsInterface levelConnections;

    private Blocks[][] levelMap;

    private LevelCellsSizes levelCellsSizes;

    @Override
    public Blocks getBlockByCoords(int x, int y) {
        if (x < 0 || levelSettings.getRow() <= x) return Blocks.ExternalBlock;
        if (y < 0 || levelSettings.getColumn() <= y) return Blocks.ExternalBlock;
        return levelMap[x][y];
    }
}