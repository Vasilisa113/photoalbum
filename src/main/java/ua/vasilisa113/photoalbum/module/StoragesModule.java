package ua.vasilisa113.photoalbum.module;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ua.vasilisa113.photoalbum.Database;
import ua.vasilisa113.photoalbum.TemplateStorage;
import ua.vasilisa113.photoalbum.impl.MeshStorage;

@Module
public interface StoragesModule {
    @Binds
    Database bindDatabase(MeshStorage arg);
    @Binds
    TemplateStorage bindTemplateStorage(MeshStorage arg1);
    @Provides static MeshStorage provideMeshStorage(){
        return new MeshStorage();
    }
}
