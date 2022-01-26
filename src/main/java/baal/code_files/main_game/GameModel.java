package baal.code_files.main_game;

import baal.code_files.chapter_system.ChapterInterface;
import baal.code_files.graphics_system.DrawerInterface;
import baal.code_files.level_system.level.LevelInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javafx.scene.canvas.Canvas;

@Component
public class GameModel {
    public volatile LevelInterface curr_level;
    public volatile boolean isStarted;
    public volatile ChapterInterface chapter;
    public final DrawerInterface drawer;

    public Canvas canvas;

    public String currLevelFilePath;
    public final String firstLevelFilePath;

    public GameModel(@Qualifier("drawer")
                             DrawerInterface drawer,
                     @Qualifier("chapter")
                             ChapterInterface chapter,
                     @Value("${firstLevelFilePath}")
                             String firstLevelFilePath) {
        this.drawer = drawer;
        this.chapter = chapter;
        this.firstLevelFilePath = firstLevelFilePath;

        currLevelFilePath = firstLevelFilePath;
    }
}
