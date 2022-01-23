package baal.code_files.level_system.event.position;
import baal.code_files.entities.entities_tree.Hero;

public class HeroPositionXOver<T extends Hero> extends HeroPositionTerm<T> {
    public HeroPositionXOver(double x) {
        super(pointDouble -> pointDouble.getX() > x);
    }
}