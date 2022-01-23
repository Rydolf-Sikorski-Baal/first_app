package baal.code_files.main_game;

import baal.code_files.chapter_system.ChapterInterface;
import baal.code_files.graphics_system.DrawerInterface;
import baal.code_files.level_system.level.LevelInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GameModel {
    public volatile LevelInterface curr_level;
    public volatile boolean isStarted;
    public volatile ChapterInterface chapter;
    public final DrawerInterface drawer;

    public final LevelChange levelChange;

    public String currLevelFilePath;
    public final String firstLevelFilePath;

    public GameModel(@Qualifier("drawer")
                                        DrawerInterface drawer,
                                @Qualifier("chapter")
                                        ChapterInterface chapter,
                                @Qualifier("levelChange")
                                        LevelChange levelChange,
                                @Value("${firstLevelFilePath}")
                                        String firstLevelFilePath) {
        this.drawer = drawer;
        this.chapter = chapter;
        this.levelChange = levelChange;
        this.firstLevelFilePath = firstLevelFilePath;

        currLevelFilePath = firstLevelFilePath;
    }
}
