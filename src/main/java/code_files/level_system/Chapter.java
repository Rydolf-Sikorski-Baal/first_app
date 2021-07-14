package code_files.level_system;

import code_files.level_system.level.Level;
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