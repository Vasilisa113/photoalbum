package ua.vasilisa113.photoalbum.component;

import dagger.BindsInstance;
import dagger.Component;
import ua.vasilisa113.photoalbum.config.PhotoalbumConfig;
import ua.vasilisa113.photoalbum.impl.Photoalbum;
import ua.vasilisa113.photoalbum.module.ConfigModule;
import ua.vasilisa113.photoalbum.module.HandlersModule;
import ua.vasilisa113.photoalbum.module.StoragesModule;
import ua.vasilisa113.photoalbum.module.VertxModule;

import javax.annotation.Nullable;
import java.util.function.Supplier;

@Component(modules = {HandlersModule.class, StoragesModule.class, ConfigModule.class, VertxModule.class})
public interface PhotoalbumComponent {
    Photoalbum photoalbum();
    @Component.Builder
    interface Builder{
        @BindsInstance
        void setConfig (@Nullable Supplier<PhotoalbumConfig> config);
        PhotoalbumComponent build();
    }
}
