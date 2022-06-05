package ua.vasilisa113.photoalbum.impl;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.apache.commons.lang.NotImplementedException;
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
        addRootListener();
        addStaticListener();
        addPortfolioListener();
        addAlbumListener();
        addPriceListener();
        addBlogAndReviewsListener();
        addContactsListener();
        addPhotoListener();
        addSearchResultsListener();
        addLoginListener();
    }
    private void addRootListener() {
        router.route("/").handler(context->{
            requestHandler.handlePortfolio().doOnError(error -> context.response().setStatusCode(HttpURLConnection.HTTP_INTERNAL_ERROR).end("Error!!!!!!!!!!!"))
                    .subscribe(portfolio -> context.response()
                            .setStatusCode(HttpURLConnection.HTTP_OK)
                            .putHeader("Content-Type", "text/html; charset=utf-8")
                            .end(portfolio)
                    );
        });
    }
    private void addStaticListener() {
        router.route("/static/*").handler(context ->{
            try {
                // we are not closing resource because it is closed by the context upon send
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
    private void addPortfolioListener() {
        router.route("/portfolio/*").handler(context->{
            throw new NotImplementedException();
        });
    }
    private void addAlbumListener() {
        router.route("/album/*").handler(context->{
            throw new NotImplementedException();
        });
    }
    private void addPriceListener() {
        router.route("/price/*").handler(context->{
            throw new NotImplementedException();
        });
    }
    private void addBlogAndReviewsListener() {
        router.route("/blog/*").handler(context->{
            throw new NotImplementedException();
        });
    }
    private void addContactsListener() {
        router.route("/contacts/*").handler(context->{
            throw new NotImplementedException();
        });
    }
    private void addPhotoListener() {
        router.route("/photo/*").handler(context->{
            throw new NotImplementedException();
        });
    }
    private void addSearchResultsListener() {
        router.route("/search/*").handler(context->{
            throw new NotImplementedException();
        });
    }
    private void addLoginListener() {
        router.route("/login/*").handler(context->{
            throw new NotImplementedException();
        });
    }
}
