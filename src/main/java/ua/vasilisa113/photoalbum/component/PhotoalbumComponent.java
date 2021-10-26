package ua.vasilisa113.photoalbum.component;

import dagger.Component;
import ua.vasilisa113.photoalbum.impl.Photoalbum;
import ua.vasilisa113.photoalbum.module.ConfigModule;
import ua.vasilisa113.photoalbum.module.HandlersModule;
import ua.vasilisa113.photoalbum.module.StoragesModule;

@Component(modules = {HandlersModule.class, StoragesModule.class, ConfigModule.class})
public interface PhotoalbumComponent {
    Photoalbum photoalbum();
}
