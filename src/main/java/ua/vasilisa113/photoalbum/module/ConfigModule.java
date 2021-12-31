package ua.vasilisa113.photoalbum.module;

import dagger.Module;
import dagger.Provides;
import org.codehaus.jackson.map.ObjectMapper;
import ua.vasilisa113.photoalbum.config.PhotoalbumConfig;
import ua.vasilisa113.photoalbum.impl.Photoalbum;

import javax.annotation.Nullable;
import java.io.File;
import java.util.function.Supplier;

@Module
public class ConfigModule {

    @Provides
    public PhotoalbumConfig getConfig(@Nullable Supplier<PhotoalbumConfig> configSupplier){
        PhotoalbumConfig config;
        if (configSupplier == null){
            File configFile = new File("config/photoalbum.json");
            try {
                config = new ObjectMapper().readValue(configFile, PhotoalbumConfig.class);
            } catch (Exception e) {
                e.printStackTrace();
                config = new PhotoalbumConfig();
            }
        }
        else {
            config = configSupplier.get();
        }
        return config;
    }
}
