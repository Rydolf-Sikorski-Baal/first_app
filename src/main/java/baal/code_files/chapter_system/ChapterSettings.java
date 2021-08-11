package baal.code_files.chapter_system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ChapterSettings {
    String firstLevelFilePath;

    public ChapterSettings(@Value("${firstLevelFilePath}")
                                   String firstLevelFilePath){
        this.firstLevelFilePath = firstLevelFilePath;
    }
}