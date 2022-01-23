package baal.code_files.level_system.event.position;

import baal.code_files.PointDouble;
import baal.code_files.entities.entities_tree.Hero;

import java.util.function.Predicate;

public class HeroPositionBothEqualsTerm<T extends Hero> extends HeroPositionTerm<T>{
    protected HeroPositionBothEqualsTerm(double x, double y) {
        super(new Predicate<PointDouble>() {
            @Override
            public boolean test(PointDouble pointDouble) {
                return (pointDouble.getX() == x) && (pointDouble.getY() == y);
            }
        });
    }
}
