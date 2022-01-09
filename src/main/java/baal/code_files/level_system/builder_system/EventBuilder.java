package baal.code_files.level_system.builder_system;

import baal.code_files.entities.movement_tree.AccordingToSpeed;
import baal.code_files.level_system.event.Event;
import baal.code_files.level_system.event.Term;
import baal.code_files.level_system.level.LevelInterface;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

@Component
public class EventBuilder implements EventBuilderInterface{
    private final Event event = new Event();

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

        return this;
    }

    private void doSmt(LevelInterface levelInterface){
        ((AccordingToSpeed)levelInterface.getLevelEntities().getEntityVector().get(0).movement).setSpeed_x(0);
    }

    @Override
    public EventBuilderInterface loadIfTrue(Map<String, Object> map) {
        event.setIfTrue(this::doSmt);
        return this;
    }

    @Override
    public EventBuilderInterface loadIfFalse(Map<String, Object> map) {
        return this;
    }

    @Override
    public Event build() {
        return event;
    }
}
