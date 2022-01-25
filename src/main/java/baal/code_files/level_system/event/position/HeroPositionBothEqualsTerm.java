package baal.code_files.level_system.event.position;

import baal.code_files.PointDouble;
import baal.code_files.entities.entities_tree.Hero;

import java.util.function.Predicate;
import java.util.stream.DoubleStream;

public class HeroPositionBothEqualsTerm<T extends Hero> extends HeroPositionTerm<T>{
    protected HeroPositionBothEqualsTerm(DoubleStream args) {
        super(pointDouble -> (pointDouble.getX() == args.toArray()[0])
                && (pointDouble.getY() == args.toArray()[1]));
    }
}
