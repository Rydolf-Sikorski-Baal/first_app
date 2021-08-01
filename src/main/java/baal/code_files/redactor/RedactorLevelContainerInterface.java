package baal.code_files.redactor;

import baal.code_files.blocks.Blocks;
import baal.code_files.level_system.level.Level;

public interface RedactorLevelContainerInterface {
    void setLevelContainer(Level level);
    void changeThisCellTo(int x, int y, Blocks block);
    Blocks getThisCell(int x, int y);
}
