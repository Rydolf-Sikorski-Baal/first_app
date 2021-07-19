package baal.code_files.level_system.level;

import baal.code_files.blocks.Blocks;

public interface LevelInterface {
    Blocks getBlockByCoords(int x, int y);
}
