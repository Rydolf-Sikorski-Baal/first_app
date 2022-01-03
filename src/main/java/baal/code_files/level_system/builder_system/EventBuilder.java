package baal.code_files.level_system.builder_system;

import baal.code_files.level_system.event.Event;
import baal.code_files.level_system.event.Term;

import java.util.ArrayList;
import java.util.Map;

public class EventBuilder implements EventBuilderInterface{
    private Event event;

    @Override
    public EventBuilderInterface loadTermsList(Map<String, Object> map) {
        ArrayList<Term> termList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : map.entrySet()){
            Object obj = null;
            try {
                obj = Class
                        .forName("baal.code_files.level_system.event.position.HeroPositionXOver")
                        .newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            termList.add((Term) obj);
        }
        event.setTermsList(termList);
    }

    @Override
    public EventBuilderInterface loadIfTrue(Map<String, Object> map) {

    }

    @Override
    public EventBuilderInterface loadIfFalse(Map<String, Object> map) {

    }

    @Override
    public Event build() {
        return event;
    }
}
