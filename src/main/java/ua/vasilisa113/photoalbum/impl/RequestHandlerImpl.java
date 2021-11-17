package ua.vasilisa113.photoalbum.impl;

import io.vertx.ext.web.RoutingContext;
import ua.vasilisa113.photoalbum.Database;
import ua.vasilisa113.photoalbum.RequestHandler;
import ua.vasilisa113.photoalbum.TemplateHandler;

import javax.inject.Inject;
import java.io.IOException;
import java.net.HttpURLConnection;

public class RequestHandlerImpl implements RequestHandler {
    private final Database database;
    private final TemplateHandler templateHandler;

    @Inject
    public RequestHandlerImpl(Database arg0, TemplateHandler arg1) {
        database = arg0;
        templateHandler = arg1;
    }

    @Override
    public void handlePortfolio(RoutingContext context) throws IOException {
        context.response()
                .setStatusCode(HttpURLConnection.HTTP_OK)
                .putHeader("Content-Type", "text/html; charset=utf-8")
                .end(templateHandler.createPortfolio());
    }

    @Override
    public void handleAlbum(RoutingContext context) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void handlePrice(RoutingContext context) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void handleBlog(RoutingContext context) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void handleContacts(RoutingContext context) {
        throw new IllegalStateException("not implemented");
    }
}
