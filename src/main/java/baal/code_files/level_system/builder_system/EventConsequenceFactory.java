package baal.code_files.level_system.builder_system;

import baal.code_files.entities.movement_tree.AccordingToSpeed;
import baal.code_files.level_system.event.Event;
import baal.code_files.level_system.level.LevelInterface;
import baal.code_files.main_game.LevelChange;
import lombok.RequiredArgsConstructor;
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
    public static String arguments = "src/main/resources/baal/code_files/level_system/second";

    private Consumer<LevelInterface> createConsequence(ConsequenceType type,
                                                       String arguments){
        EventConsequenceFactory.arguments = arguments;
        Consumer<LevelInterface> consumer = null;
        switch(type) {
            case NOTHING:
                consumer = this::doNothing;
                break;
            case CHANGE_LEVEL:
                consumer = this::changeLevel;
                break;
            case CHANGE_HERO_SPEED:
                consumer = this::changeHeroSpeed;
                break;
            case SET_HERO_SPEED_X:
                consumer = this::setHeroSpeedX;
                break;
            case SET_HERO_SPEED_Y:
                consumer = this::setHeroSpeedY;
                break;
        }
        return consumer;
    }

    private void doNothing(LevelInterface level) {}
    private void changeLevel(LevelInterface level){
        levelChange.changeLevelByFlags(
                arguments,
                false, false);
    }
    private void changeHeroSpeed(LevelInterface level){
        ((AccordingToSpeed)level.getLevelEntities().getEntityVector().get(0).movement).changeSpeedX(-1);
        ((AccordingToSpeed)level.getLevelEntities().getEntityVector().get(0).movement).changeSpeedY( 1);
    }
    private void setHeroSpeedX(LevelInterface level){
        ((AccordingToSpeed)(level.getLevelEntities().getEntityVector().get(0)).movement).setSpeed_x(-0.01);
    }
    private void setHeroSpeedY(LevelInterface level){
        ((AccordingToSpeed)(level.getLevelEntities().getEntityVector().get(0)).movement).setSpeed_y(1);
    }
}