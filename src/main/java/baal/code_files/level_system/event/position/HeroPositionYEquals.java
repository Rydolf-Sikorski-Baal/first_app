package baal.code_files.level_system.event.position;

import baal.code_files.entities.entities_tree.Hero;

import java.util.stream.DoubleStream;

public class HeroPositionYEquals<T extends Hero> extends HeroPositionTerm<T>{
    protected HeroPositionYEquals(DoubleStream args) {
        super(pointDouble -> pointDouble.getY() == args.toArray()[0]);
    }
}
