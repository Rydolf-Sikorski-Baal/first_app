package baal.code_files.level_system.level;

public interface LevelSettingsInterface {
    Integer getRow();
    Integer getColumn();

    Double getHeroStartX();
    Double getHeroStartY();

    Double getDefaultSpeedX();
    Double getDefaultSpeedY();

    Double getDefaultSpeedDeltaX();
    Double getDefaultSpeedDeltaY();

    Boolean getIsKeepSpeedFromPreviousLevel();
}