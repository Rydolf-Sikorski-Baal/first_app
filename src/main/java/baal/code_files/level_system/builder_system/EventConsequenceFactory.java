package baal.code_files.level_system.builder_system;

import baal.code_files.level_system.builder_system.ConsequenceType;
import baal.code_files.level_system.event.Event;
import baal.code_files.level_system.level.LevelInterface;
import baal.code_files.main_game.LevelChange;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class EventConsequenceFactory {
    private final LevelChange levelChange;

    public void integrateConsequence(Event event,
                                     ConsequenceType ifTrue,
                                     String ifTrueArguments,
                                     ConsequenceType ifFalse,
                                     String ifFalseArguments){
        event.setIfTrue(createConsequence(ifTrue, ifTrueArguments));
        event.setIfFalse(createConsequence(ifFalse, ifFalseArguments));
    }

    private Consumer<LevelInterface> createConsequence(ConsequenceType type,
                                                       String arguments){
        Consumer<LevelInterface> consumer = null;
        switch(type) {
            case NOTHING:
                consumer = this::doNothing;
                break;
            case CHANGE_LEVEL:
                consumer = this::changeLevel;
                break;
        }
        return consumer;
    }

    private void doNothing(LevelInterface levelInterface) {
    }
    private void changeLevel(LevelInterface levelInterface){
        levelChange.changeLevelByFlags("src/main/resources/baal/code_files/level_system/second",
                true, true);
    }
}