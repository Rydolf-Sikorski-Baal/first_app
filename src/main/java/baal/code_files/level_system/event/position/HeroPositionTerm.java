package baal.code_files.level_system.event.position;

import baal.code_files.PointDouble;
import baal.code_files.entities.entities_tree.Hero;
import baal.code_files.level_system.event.HeroTerm;

import java.util.function.Predicate;

public abstract class HeroPositionTerm<T extends Hero> extends HeroTerm<T> {
    protected HeroPositionTerm() {}
}