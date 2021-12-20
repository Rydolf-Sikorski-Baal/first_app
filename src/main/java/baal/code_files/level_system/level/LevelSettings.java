package baal.code_files.level_system.level;

import lombok.*;


@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class LevelSettings implements LevelSettingsInterface{
    @Getter(onMethod = @__(@Override))
    @NonNull private Integer row,
                             column;
    @Getter(onMethod = @__(@Override))
    @NonNull private Double heroStartX,
                            heroStartY;

    @Getter(onMethod = @__(@Override))
    @NonNull private Double defaultSpeedX,
                            defaultSpeedY;
    @Getter(onMethod = @__(@Override))
    @NonNull private Double defaultSpeedDeltaX,
                            defaultSpeedDeltaY;

    @Getter(onMethod = @__(@Override))
    @NonNull private Boolean isKeepSpeedFromPreviousLevel;
}