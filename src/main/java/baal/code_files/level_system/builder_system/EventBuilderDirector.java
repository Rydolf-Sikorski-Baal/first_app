package baal.code_files.level_system.builder_system;

import baal.code_files.level_system.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class EventBuilderDirector implements EventBuilderDirectorInterface {
    private final EventBuilderInterface eventBuilder;

    @Override
    public Event build(Map<String, Object> map){
        return eventBuilder.loadTermsList(map).loadConsequence(map).build();
    }
}