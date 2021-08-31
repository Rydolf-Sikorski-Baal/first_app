package baal.code_files.entities.controllability_tree;

import baal.code_files.entities.entities_tree.Entity;
import baal.code_files.main_game.controls.ControlsCodes;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class Uncontrollable extends Controllability{
    @Override
    public void doThisOperation(ControlsCodes controlsCode) {}
}
