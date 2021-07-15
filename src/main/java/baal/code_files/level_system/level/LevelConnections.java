package baal.code_files.level_system.level;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Vector;

@Component
@Scope("prototype")
@Data
public class LevelConnections implements LevelConnectionsInterface {
    Vector<String> levelFileNamesVector;
}
