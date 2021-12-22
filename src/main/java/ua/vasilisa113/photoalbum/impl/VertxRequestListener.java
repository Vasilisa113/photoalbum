package ua.vasilisa113.photoalbum.impl;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import ua.vasilisa113.photoalbum.RequestHandler;
import ua.vasilisa113.photoalbum.RequestListener;

import javax.inject.Inject;
import java.net.HttpURLConnection;

public class VertxRequestListener extends AbstractVerticle implements RequestListener {
    private final RequestHandler requestHandler;
    private final Vertx vertx;
    private final Router router;
    @Inject
    public VertxRequestListener(RequestHandler arg0, Vertx arg1, Router arg2) {
        requestHandler = arg0;
        vertx = arg1;
        router = arg2;
        vertx.deployVerticle(this);
        router.route("/").handler(context ->{
            try {
                requestHandler.handlePortfolio(context);
            }
            catch (Throwable e){
                context.response().setStatusCode(HttpURLConnection.HTTP_INTERNAL_ERROR).end("Error!!!!!!!!!!!");
            }
        });
        router.route("/static/*").handler(context ->{
            try {
                requestHandler.handleStaticResource(context);
            }
            catch (Throwable e){
                context.response().setStatusCode(HttpURLConnection.HTTP_INTERNAL_ERROR).end("Error!!!!!!!!!!!");
            }
        });
    }
}
