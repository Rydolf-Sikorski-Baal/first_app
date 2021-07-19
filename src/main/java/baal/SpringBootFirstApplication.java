package baal;

import baal.code_files.level_system.ChapterSettings;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootFirstApplication {
    public static void main(String[] args) {
        Application.launch(JavaFxApplication.class, args);
    }

    @Bean
    public ChapterSettings chapterSettings(){
        return new ChapterSettings("first");
    }
}