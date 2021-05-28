package code_files.level_system;

// отвечает за структуру и свойства уровней

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Level {
    @NonNull
    public LevelSettings levelSettings;
    @NonNull
    public LevelEntities levelEntities;
    @NonNull
    public final String className;
}