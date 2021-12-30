package baal.code_files.level_system.builder_system;

import baal.code_files.level_system.level.LevelInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LevelBuilderDirector implements LevelBuilderDirectorInterface{
    private final BuilderInterface builder;

    public LevelInterface build(String levelFileName){
        return this.builder.loadJson(levelFileName).loadTriggers(levelFileName).build();
    }
}