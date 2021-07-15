package baal.code_files.level_system;

import baal.code_files.level_system.level.Level;
import baal.code_files.level_system.load_system.LevelsLoadController;
import baal.code_files.level_system.load_system.LevelsLoadControllerInterface;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Vector;

@Component
@Scope("prototype")
public class Chapter implements ChapterInterface {
    private Vector<Level> levelVector;
    private final LevelsLoadControllerInterface levelsLoadController;
    private Level currentLevel;

    Chapter(LevelsLoadController levelsLoadController){
        this.levelsLoadController = levelsLoadController;
    }

    @PostConstruct
    private void loadChapter(){

    }
}