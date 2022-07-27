package baal.code_files.level_system.builder_system;

import baal.code_files.level_system.event.Event;

import java.util.Map;

public interface EventBuilderDirectorInterface {
    Event build(Map<String, Object> map);
}