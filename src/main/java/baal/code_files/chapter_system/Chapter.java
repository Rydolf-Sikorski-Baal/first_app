package baal.code_files.chapter_system;

import baal.code_files.level_system.level.Level;
import baal.code_files.level_system.load_system.LevelsLoadController;
import baal.code_files.level_system.load_system.LevelsLoadControllerInterface;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Vector;

@Component
public class Chapter implements ChapterInterface {
    @Getter
    @NonNull private final LevelsLoadControllerInterface levelsLoadController;
    @Getter
    @NonNull private final ChapterSettings chapterSettings;

    @Getter private Vector<Level> levelVector = new Vector<>();

    Chapter(@Qualifier("levelsLoadController")
                    LevelsLoadController levelsLoadController,
            @Qualifier("chapterSettings")
                    ChapterSettings chapterSettings){
        this.levelsLoadController = levelsLoadController;
        this.chapterSettings = chapterSettings;
    }


}