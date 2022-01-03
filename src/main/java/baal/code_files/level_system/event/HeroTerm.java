package baal.code_files.level_system.event;

import baal.code_files.entities.entities_tree.Hero;
import baal.code_files.level_system.level.LevelInterface;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public abstract class HeroTerm<T extends Hero> extends Term<T>{
    private Predicate<T> predicate;

    protected HeroTerm() {

    }

    @Override
    @SuppressWarnings("all")
    T getRequiredInformationFromLevel(LevelInterface level) {
        return ((T)level.getLevelEntities().getEntityVector().get(0));
    }

    @Override
    boolean check(LevelInterface level) {
        return predicate.test(this.getRequiredInformationFromLevel(level));
    }
}
