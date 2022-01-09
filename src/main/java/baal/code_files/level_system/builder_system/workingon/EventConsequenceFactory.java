package baal.code_files.level_system.builder_system.workingon;

import baal.code_files.level_system.builder_system.ConsequenceType;
import baal.code_files.level_system.event.Event;
import baal.code_files.main_game.LevelChange;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class EventConsequenceFactory {
    private Event event;

    private final LevelChange levelChange;

    public void integrateConsequence(ConsequenceType ifTrue,
                                     String ifTrueArguments,
                                     ConsequenceType ifFalse,
                                     String ifFalseArguments){
        event.setIfTrue(createConsequence(ifTrue, ifTrueArguments));
        event.setIfFalse(createConsequence(ifFalse, ifFalseArguments));
    }

    private <T> Consumer<T> createConsequence(ConsequenceType type,
                                              String arguments){
        Consumer<T> consumer = null;
        switch(type) {
            case NOTHING:
                consumer = ;
                break;
            case CHANGE_LEVEL:
                consumer  ;
                break;
        }
    }
}