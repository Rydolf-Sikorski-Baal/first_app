package baal.code_files.level_system.event.position;

import baal.code_files.entities.entities_tree.Hero;

import java.util.stream.DoubleStream;

public class HeroPositionYOver<T extends Hero> extends HeroPositionTerm<T> {
    protected HeroPositionYOver(DoubleStream args) {
        super(pointDouble -> pointDouble.getX() > args.toArray()[0]);
    }
}