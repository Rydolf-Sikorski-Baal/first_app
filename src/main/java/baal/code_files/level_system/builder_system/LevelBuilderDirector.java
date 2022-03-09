package baal.code_files.level_system.builder_system;

import baal.code_files.level_system.level.LevelInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LevelBuilderDirector implements LevelBuilderDirectorInterface{
    private final BuilderInterface builder;

    public LevelInterface build(String levelFileName) throws ClassNotFoundException, IOException {
        return (((this.builder).loadJson(levelFileName)).loadTriggers(levelFileName)).build();
    }
}