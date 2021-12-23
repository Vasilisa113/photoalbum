package ua.vasilisa113.photoalbum;

import io.vertx.ext.web.RoutingContext;

import java.io.IOException;

public interface RequestHandler {
    public void handlePortfolio(RoutingContext context);
    public void handleAlbum(RoutingContext context);
    public void handlePrice(RoutingContext context);
    public void handleBlog(RoutingContext context);
    public void handleContacts(RoutingContext context);

    public void handleStaticResource(RoutingContext context);
}
