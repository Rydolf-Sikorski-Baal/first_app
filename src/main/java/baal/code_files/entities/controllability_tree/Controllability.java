package baal.code_files.entities.controllability_tree;

import baal.code_files.entities.entities_tree.Entity;
import baal.code_files.main_game.controls.ControlsCodes;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Setter
public abstract class Controllability {
    Entity entity;

    abstract public void doThisOperation(ControlsCodes controlsCode);
}
