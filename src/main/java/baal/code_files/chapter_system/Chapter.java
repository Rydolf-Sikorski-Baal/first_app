package baal.code_files.chapter_system;

import baal.code_files.level_system.level.LevelInterface;
import baal.code_files.level_system.load_system.LevelsLoadController;
import baal.code_files.level_system.load_system.LevelsLoadControllerInterface;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Vector;

@Component
public class Chapter implements ChapterInterface {
    @NonNull private final LevelsLoadControllerInterface levelsLoadController;
    @NonNull private final ChapterSettings chapterSettings;

    @Getter(onMethod = @__(@Override))
    private Vector<LevelInterface> levelVector = new Vector<>();

    Chapter(@Qualifier("levelsLoadController") @NotNull
                    LevelsLoadController levelsLoadController,
            @Qualifier("chapterSettings") @NotNull
                    ChapterSettings chapterSettings){
        this.levelsLoadController = levelsLoadController;
        this.chapterSettings = chapterSettings;
    }


}