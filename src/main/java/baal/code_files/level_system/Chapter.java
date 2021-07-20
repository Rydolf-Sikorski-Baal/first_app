package baal.code_files.level_system;

import baal.code_files.level_system.level.Level;
import baal.code_files.level_system.load_system.LevelsLoadController;
import baal.code_files.level_system.load_system.LevelsLoadControllerInterface;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Vector;

@Component
public class Chapter implements ChapterInterface {
    @Getter private final LevelsLoadControllerInterface levelsLoadController;
    private final ChapterSettings chapterSettings;
    @Getter private Vector<Level> levelVector = new Vector<>();

    Chapter(LevelsLoadController levelsLoadController, ChapterSettings chapterSettings){
        this.levelsLoadController = levelsLoadController;
        this.chapterSettings = chapterSettings;
    }

    @PostConstruct
    private void loadChapter() throws IOException {
        levelsLoadController.loadThisLevel(chapterSettings.firstLevel, levelVector);
        levelsLoadController.updateFromThisLevel(levelVector.get(0), levelVector);
    }
}