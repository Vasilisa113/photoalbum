package ua.vasilisa113.photoalbum;

import io.reactivex.Single;
import io.vertx.ext.web.RoutingContext;

import java.io.IOException;
import java.io.InputStream;

public interface TemplateHandler {
    InputStream getStaticResource (String path, String language);
    Single<String> createPortfolio();
}
