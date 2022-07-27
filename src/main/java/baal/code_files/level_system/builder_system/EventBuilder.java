package baal.code_files.level_system.builder_system;

import baal.code_files.entities.entities_tree.Hero;
import baal.code_files.level_system.event.Event;
import baal.code_files.level_system.event.Term;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

@Component
public class EventBuilder implements EventBuilderInterface{
    private Event event = new Event();

    private final EventConsequenceFactory consequenceFactory;

    public EventBuilder(@Qualifier("eventConsequenceFactory")
                                EventConsequenceFactory consequenceFactory) {
        this.consequenceFactory = consequenceFactory;
    }

    private static Map<String, Object> argsMap;
    @Override
    @SuppressWarnings("all")
    public EventBuilderInterface loadTermsList(Map<String, Object> map) {
        ArrayList<Term<Hero>> termList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : map.entrySet()){
            argsMap = (Map<String, Object>) entry.getValue();

            Object obj = null;
            try {
                Class[] args = {double[].class};
                double[] params = {4.0, 4.0};
                obj = Class
                        .forName(entry.getKey())
                        .getConstructor(args)
                        .newInstance((Object) params);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }

            termList.add((Term<Hero>) obj);
        }
        event.setTermsList(termList);

        return this;
    }

    @Override
    @SuppressWarnings("all")
    public EventBuilderInterface loadConsequence(Map<String, Object> map){
        Map<String, String> ifTrueMap = (Map<String, String>) argsMap.get("ifTrue");
        Map<String, String> ifFalseMap = (Map<String, String>) argsMap.get("ifFalse");

        ConsequenceType ifTrueType = ConsequenceType.valueOf(ifTrueMap.get("Type"));
        String ifTrueArgs = ifTrueMap.get("Args");

        ConsequenceType ifFalseType = ConsequenceType.valueOf(ifFalseMap.get("Type"));
        String ifFalseArgs = ifFalseMap.get("Args");

        consequenceFactory.integrateConsequence(
                event,
                ifTrueType, ifTrueArgs,
                ifFalseType, ifFalseArgs);
        return this;
    }

    @Override
    public Event build() {
        return event;
    }
}