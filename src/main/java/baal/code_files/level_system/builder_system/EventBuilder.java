package baal.code_files.level_system.builder_system;

import baal.code_files.entities.entities_tree.Hero;
import baal.code_files.level_system.event.Event;
import baal.code_files.level_system.event.Term;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@Component
public class EventBuilder implements EventBuilderInterface{
    private final Event event = new Event();

    private final EventConsequenceFactory consequenceFactory;

    public EventBuilder(@Qualifier("eventConsequenceFactory")
                                EventConsequenceFactory consequenceFactory) {
        this.consequenceFactory = consequenceFactory;
    }

    private static Map<String, Object> argsMap;
    @Override
    public EventBuilderInterface loadTermsList(Map<String, Object> map) {
        ArrayList<Term<Hero>> termList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : map.entrySet()){
            argsMap = (Map<String, Object>) entry.getValue();

            Object obj = null;
            try {
                Class[] args = {DoubleStream.class};
                obj = Class
                        .forName(entry.getKey())
                        .getConstructor(args)
                        .newInstance(DoubleStream.of(6.0, 5.0));
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }

            termList.add((Term<Hero>) obj);
        }
        event.setTermsList(termList);

        return this;
    }

    @Override
    public EventBuilderInterface loadConsequence(Map<String, Object> map){
        consequenceFactory.integrateConsequence(event,
                ConsequenceType.CHANGE_LEVEL, "",
                ConsequenceType.NOTHING, "");
        return this;
    }

    @Override
    public Event build() {
        return event;
    }
}