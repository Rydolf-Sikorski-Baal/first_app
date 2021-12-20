package baal.code_files.level_system.event;

import baal.code_files.entities.entities_tree.Hero;
import baal.code_files.entity_movement.PositionInfo;
import baal.code_files.level_system.level.LevelInterface;
import javafx.scene.paint.Color;
import lombok.val;

public class PositionTrigger extends Trigger {
    private boolean checkChek(PositionInfo positionInfo){
        val vector = positionInfo.getCurrentInnerCoords();
        return (vector.get(0)).x < (vector.get(0).y);
    }

    private void changeColorToRed(LevelInterface level){
        ((Hero)level.getLevelEntities().getEntityVector().get(0)).setMyColor(Color.RED);
    }
    private void changeColorToGreen(LevelInterface level){
        ((Hero)level.getLevelEntities().getEntityVector().get(0)).setMyColor(Color.GREEN);
    }

    public PositionTrigger(){
        this.predicate = this::checkChek;
        this.ifTrue = this::changeColorToGreen;
        this.ifFalse = this::changeColorToRed;
    }
}
