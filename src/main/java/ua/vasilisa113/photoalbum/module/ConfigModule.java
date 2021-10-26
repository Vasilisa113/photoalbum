package ua.vasilisa113.photoalbum.module;

import dagger.Module;
import dagger.Provides;
import org.codehaus.jackson.map.ObjectMapper;
import ua.vasilisa113.photoalbum.config.PhotoalbumConfig;

import java.io.File;

@Module
public class ConfigModule {

    @Provides
    public PhotoalbumConfig getConfig(){
        File configFile = new File("config/photoalbum.json");
        PhotoalbumConfig config;
        try {
            config = new ObjectMapper().readValue(configFile, PhotoalbumConfig.class);
        } catch (Exception e) {
            e.printStackTrace();
            config = new PhotoalbumConfig();
        }
        return config;
    }
}
