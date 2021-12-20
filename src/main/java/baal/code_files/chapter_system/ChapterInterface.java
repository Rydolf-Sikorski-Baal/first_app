package baal.code_files.chapter_system;

import baal.code_files.level_system.level.LevelInterface;

import java.util.Vector;

public interface ChapterInterface {
    Vector<LevelInterface> getLevelVector();
}