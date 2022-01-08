package ua.vasilisa113.photoalbum.impl;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import ua.vasilisa113.photoalbum.RequestHandler;
import ua.vasilisa113.photoalbum.RequestListener;

import javax.inject.Inject;
import java.io.InputStream;
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
        router.route("/").handler(context->{
            requestHandler.handlePortfolio().doOnError(error -> context.response().setStatusCode(HttpURLConnection.HTTP_INTERNAL_ERROR).end("Error!!!!!!!!!!!"))
                    .subscribe(portfolio -> context.response()
                    .setStatusCode(HttpURLConnection.HTTP_OK)
                    .putHeader("Content-Type", "text/html; charset=utf-8")
                    .end(portfolio)
            );
        });
        router.route("/static/*").handler(context ->{
            try {
                InputStream resource = requestHandler.handleStaticResource(context.pathParam("*"), context.queryParams().get("lang"));
                context.response().send(new AsyncInputStream(context.vertx(), context.vertx().getOrCreateContext(), resource), result -> {
                    if (result.failed()) {
                        context.fail(result.cause());
                    }
                });
            }
            catch (Throwable e){
                context.response().setStatusCode(HttpURLConnection.HTTP_INTERNAL_ERROR).end("Error!!!!!!!!!!!");
            }
        });
    }
}
