package ua.vasilisa113.photoalbum.impl;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import ua.vasilisa113.photoalbum.TemplateHandler;
import ua.vasilisa113.photoalbum.TemplateStorage;
import ua.vasilisa113.photoalbum.config.PhotoalbumConfig;

import javax.inject.Inject;
import java.io.IOException;
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
    public String createPortfolio() throws IOException {
        Template portfolioTemplate = handlebars.compileInline(templateStorage.getTemplate(config.getMeshStorage().getProjectName(), "portfolioTemplate", "en"));
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("header", "Hi");
        parameterMap.put("body", "photoalbum");
        return portfolioTemplate.apply(parameterMap);
    }
}
