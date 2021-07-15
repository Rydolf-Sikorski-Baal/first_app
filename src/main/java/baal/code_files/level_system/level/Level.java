package baal.code_files.level_system.level;

// отвечает за структуру и свойства уровней

import baal.code_files.blocks.Blocks;
import baal.code_files.graphics_system.LevelCellsSizes;
import lombok.Data;
import lombok.NonNull;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Data
public class Level {
    @NonNull private LevelSettingsInterface levelSettings;
    @NonNull private LevelEntitiesInterface levelEntities;
    @NonNull private LevelConnectionsInterface levelConnections;

    private Blocks[][] levelMap;

    private LevelCellsSizes levelCellsSizes;
}