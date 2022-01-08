package ua.vasilisa113.photoalbum.impl;

import io.reactivex.Single;
import ua.vasilisa113.photoalbum.Database;
import ua.vasilisa113.photoalbum.RequestHandler;
import ua.vasilisa113.photoalbum.TemplateHandler;

import javax.inject.Inject;
import java.io.InputStream;

public class RequestHandlerImpl implements RequestHandler {
    private final Database database;
    private final TemplateHandler templateHandler;

    @Inject
    public RequestHandlerImpl(Database arg0, TemplateHandler arg1) {
        database = arg0;
        templateHandler = arg1;
    }

    @Override
    public Single<String> handlePortfolio() {
        return templateHandler.createPortfolio();
    }

    @Override
    public void handleAlbum() {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void handlePrice() {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void handleBlog() {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void handleContacts() {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public InputStream handleStaticResource(String path, String lang) {
        return templateHandler.getStaticResource(path, lang);
    }
}
