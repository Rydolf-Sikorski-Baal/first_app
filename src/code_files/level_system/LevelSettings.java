package code_files.level_system;

import lombok.Data;

@Data
public class LevelSettings {
    private final int row, column;
    private final double heroStartX, heroStartY;

    private final double defaultSpeedX, defaultSpeedY;
    private final double defaultSpeedDeltaX, defaultSpeedDeltaY;
    private final boolean isKeepSpeedFromPreviousLevel;
}