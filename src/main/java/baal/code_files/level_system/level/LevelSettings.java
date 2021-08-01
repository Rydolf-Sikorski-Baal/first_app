package baal.code_files.level_system.level;

import lombok.Data;
import lombok.NonNull;

@Data
public class LevelSettings implements LevelSettingsInterface{
    @NonNull private int row, column;
    @NonNull private double heroStartX, heroStartY;

    @NonNull private double defaultSpeedX, defaultSpeedY;
    @NonNull private double defaultSpeedDeltaX, defaultSpeedDeltaY;

    @NonNull private boolean isKeepSpeedFromPreviousLevel;
}