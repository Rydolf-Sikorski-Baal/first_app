package baal.code_files.redactor;

import baal.code_files.blocks.Blocks;
import baal.code_files.level_system.level.Level;
import org.springframework.stereotype.Component;

@Component
public class RedactorLevelContainer implements RedactorLevelContainerInterface {
    private Blocks[][] currentLevelMap;

    @Override
    public void setLevelContainer(Level level) {
        if (level == null) throw new RuntimeException("не загружен уровень");

        currentLevelMap = (level.getLevelMap()).clone();
    }

    @Override
    public void changeThisCellTo(int x, int y, Blocks block) {
        if (currentLevelMap == null) throw new RuntimeException("не установлена карта редактора");

        currentLevelMap[x][y] = block;
    }

    @Override
    public Blocks getThisCell(int x, int y) {
        if (currentLevelMap == null) throw new RuntimeException("не установлена карта редактора");

        return currentLevelMap[x][y];
    }
}