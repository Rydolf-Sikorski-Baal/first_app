package baal.code_files.level_system.level;

import lombok.Data;

import java.util.Vector;

@Data
public class LevelConnections implements LevelConnectionsInterface {
    Vector<String> levelFileNamesVector;
}
