package baal.code_files.entities.controllability_tree;

import baal.code_files.entities.movement_tree.AccordingToSpeed;
import baal.code_files.main_game.controls.ControlsCodes;

public class HeroControls extends Controllable {
    @Override
    public void doThisOperation(ControlsCodes controlsCode) {
        AccordingToSpeed accordingToSpeed = (AccordingToSpeed) entity.movement;
        if (controlsCode == ControlsCodes.Jump){
            accordingToSpeed.changeSpeedX(-0.12);
        }
        if (controlsCode == ControlsCodes.Left){
            accordingToSpeed.changeSpeedY(-0.12);
        }
        if (controlsCode == ControlsCodes.Right){
            accordingToSpeed.changeSpeedY(0.12);
        }
    }
}
