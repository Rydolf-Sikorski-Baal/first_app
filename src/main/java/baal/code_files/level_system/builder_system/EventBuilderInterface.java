package baal.code_files.level_system.builder_system;

import baal.code_files.level_system.event.Event;

import java.util.Map;

public interface EventBuilderInterface {
    EventBuilderInterface loadTermsList(Map<String, Object> map);
    EventBuilderInterface loadIfTrue(Map<String, Object> map);
    EventBuilderInterface loadIfFalse(Map<String, Object> map);
    Event build();
}
