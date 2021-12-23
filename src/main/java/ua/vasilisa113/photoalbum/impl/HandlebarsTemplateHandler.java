package ua.vasilisa113.photoalbum.impl;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import io.reactivex.Single;
import io.vertx.ext.web.RoutingContext;
import ua.vasilisa113.photoalbum.TemplateHandler;
import ua.vasilisa113.photoalbum.TemplateStorage;
import ua.vasilisa113.photoalbum.config.PhotoalbumConfig;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HandlebarsTemplateHandler implements TemplateHandler {
    private final TemplateStorage templateStorage;
    private final Handlebars handlebars;
    private final PhotoalbumConfig config;
    @Inject
    public HandlebarsTemplateHandler(TemplateStorage arg, PhotoalbumConfig config) {
        this.config = config;
        templateStorage = arg;
        handlebars = new Handlebars();
    }

    @Override
    public InputStream getStaticResource(String path, String language) {
        int pathStart = path.indexOf("/");
        return templateStorage.getStaticResource(path.substring(0, pathStart), path.substring(pathStart+1), language);
    }

    @Override
    public Single<String> createPortfolio() {
        return templateStorage.getTemplate(config.getMeshStorage().getProjectName(), "portfolioTemplate", "en")
                .flatMap(template -> {
                    Template portfolioTemplate = handlebars.compileInline(template);
                    Map<String, String> parameterMap = new HashMap<>();
                    parameterMap.put("header", "Hi");
                    parameterMap.put("body", "photoalbum");
                    return Single.just(portfolioTemplate.apply(parameterMap));
                });
    }
}
