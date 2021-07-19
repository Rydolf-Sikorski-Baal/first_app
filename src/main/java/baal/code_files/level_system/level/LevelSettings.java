package baal.code_files.level_system.level;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
public class LevelSettings implements LevelSettingsInterface{
    private final int row, column;
    private final double heroStartX, heroStartY;

    private final double defaultSpeedX, defaultSpeedY;
    private final double defaultSpeedDeltaX, defaultSpeedDeltaY;
    private final boolean isKeepSpeedFromPreviousLevel;
}