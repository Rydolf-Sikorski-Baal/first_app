package baal.code_files.level_system.event.position;

import baal.code_files.entities.entities_tree.Hero;

import java.util.stream.DoubleStream;

public class HeroPositionBothEqualsTerm<T extends Hero> extends HeroPositionTerm<T>{
    protected HeroPositionBothEqualsTerm(double[] args) {
        super(pointDouble -> (pointDouble.getX() == args[0])
                && (pointDouble.getY() == args[1]));
    }
}
