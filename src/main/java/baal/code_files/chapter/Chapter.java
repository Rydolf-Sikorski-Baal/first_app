package baal.code_files.chapter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
    @Setter
    private String entryPointFilePath;
    @Getter
    private String currLevelFilePath;
    volatile public Integer tickCount = 0;
    volatile public Integer deathCount = 0;

    @Setter
    private volatile String chapterName = "forestChapter";

    public void startChapter(){
        Yaml yaml = new Yaml();
        InputStream inputStream = null;
        try{
            inputStream = new FileInputStream
                    (String.format("src/main/resources/baal/code_files/chapter/%s", chapterName));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        Map<String, Object> map = yaml.load(inputStream);

        entryPointFilePath = (String) map.get("entryPoint");
        currLevelFilePath = entryPointFilePath;
    }
    public void endChapter(){}
}