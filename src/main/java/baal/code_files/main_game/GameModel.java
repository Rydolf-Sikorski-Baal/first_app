package baal.code_files.main_game;

import baal.code_files.chapter.Chapter;
import baal.code_files.level_system.level.LevelInterface;
import javafx.scene.canvas.Canvas;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class GameModel {
    public volatile LevelInterface curr_level;
    public volatile boolean isStarted;
    public volatile Chapter chapter;

    public Canvas canvas;

    public String currLevelFilePath;

    public GameModel(@Qualifier("chapter")
                             Chapter chapter) {
        this.chapter = chapter;
    }

    public void startChapter(){
        chapter.startChapter();
    }
}