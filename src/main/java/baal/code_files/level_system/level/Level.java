package baal.code_files.level_system.level;

import baal.code_files.blocks.Blocks;
import baal.code_files.graphics_system.LevelCellsSizes;
import baal.code_files.level_system.event.Event;
import lombok.*;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Setter
public class Level implements LevelInterface{
    @Getter(onMethod = @__(@Override))
    @NonNull private LevelSettings levelSettings;
    @Getter(onMethod = @__(@Override))
    @NonNull private LevelEntities levelEntities;
    @Getter(onMethod = @__(@Override))
    @NonNull private LevelConnections levelConnections;
    @Getter(onMethod = @__(@Override))
    private LevelEvents levelEvents;

    @Getter(onMethod = @__(@Override))
    @NonNull private Blocks[][] levelMap;

    @Getter(onMethod = @__(@Override))
    @NonNull private LevelCellsSizes levelCellsSizes;

    @Override
    public Blocks getBlockByCords(int x, int y) {
        if (x < 0 || levelSettings.getRow() <= x) return Blocks.ExternalBlock;
        if (y < 0 || levelSettings.getColumn() <= y) return Blocks.ExternalBlock;
        if (levelMap == null) throw new RuntimeException("карта уровня не загруженна");
        return levelMap[x][y];
    }

    @Override
    public void changeThisCellTo(int x, int y, Blocks block) {
        if (levelMap == null) throw new RuntimeException("не установлена карта редактора");

        levelMap[x][y] = block;
    }

    @Override
    public void addEvent(Event event) {
        this.getLevelEvents().getLevelEventsVector().add(event);
    }
}