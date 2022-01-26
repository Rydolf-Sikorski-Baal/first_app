package baal.code_files.level_system.event.position;

import baal.code_files.entities.entities_tree.Hero;

import java.util.stream.DoubleStream;

public class HeroPositionXEquals<T extends Hero> extends HeroPositionTerm<T>{
    protected HeroPositionXEquals(double[] args) {
        super(pointDouble -> pointDouble.getX() == args[0]);
    }
}
