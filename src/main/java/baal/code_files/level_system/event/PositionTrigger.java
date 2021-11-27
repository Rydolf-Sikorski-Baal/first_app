package baal.code_files.level_system.event;

import baal.code_files.entity_movement.PositionInfo;
import lombok.val;

public class PositionTrigger extends Trigger {
    private boolean checkChek(PositionInfo positionInfo){
        val vector = positionInfo.getCurrentInnerCoords();
        if ((vector.get(0)).x < (vector.get(0).y)) return true;
        return false;
    }

    public PositionTrigger(){
        this.predicate = this::checkChek;
    }
}
