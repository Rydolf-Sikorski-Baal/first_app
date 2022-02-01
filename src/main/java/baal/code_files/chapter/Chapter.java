package baal.code_files.chapter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class Chapter {
    @Getter
    private String entryPointFilePath;
    @Getter
    private String currLevelFilePath;
    @Getter
    volatile private Integer tickCount;
    @Getter
    volatile private Integer deathCount;

    public void startChapter(){
        Yaml yaml = new Yaml();
        InputStream inputStream = null;
        try{
            inputStream = new FileInputStream("src/main/resources/baal/code_files/chapter/forestChapter");
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        Map<String, Object> map = yaml.load(inputStream);

        entryPointFilePath = (String) map.get("entryPoint");
        currLevelFilePath = entryPointFilePath;
    }
    public void endChapter(){}
}