package code_files.level_system.level;

// отвечает за структуру и свойства уровней

import lombok.Data;

@Data
public class Level {
    private LevelSettings levelSettings;
    private LevelEntities levelEntities;
    private LevelConnections levelConnections;
}