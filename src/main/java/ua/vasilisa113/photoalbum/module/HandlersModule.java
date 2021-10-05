package ua.vasilisa113.photoalbum.module;

import dagger.Binds;
import dagger.Module;
import ua.vasilisa113.photoalbum.RequestHandler;
import ua.vasilisa113.photoalbum.RequestListener;
import ua.vasilisa113.photoalbum.TemplateHandler;
import ua.vasilisa113.photoalbum.impl.HandlebarsTemplateHandler;
import ua.vasilisa113.photoalbum.impl.RequestHandlerImpl;
import ua.vasilisa113.photoalbum.impl.VertxRequestListener;

@Module
public interface HandlersModule {
    @Binds
    RequestListener bindsRequestListener(VertxRequestListener arg);
    @Binds
    RequestHandler bindsRequestHandler(RequestHandlerImpl arg1);
    @Binds
    TemplateHandler bindsTemplaeHandler(HandlebarsTemplateHandler arg2);
}
