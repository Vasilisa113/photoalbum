package ua.vasilisa113.photoalbum.impl;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import ua.vasilisa113.photoalbum.TemplateHandler;
import ua.vasilisa113.photoalbum.TemplateStorage;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HandlebarsTemplateHandler implements TemplateHandler {
    private final TemplateStorage templateStorage;
    private final Handlebars handlebars;
    @Inject
    public HandlebarsTemplateHandler(TemplateStorage arg) {
        templateStorage = arg;
        handlebars = new Handlebars();
    }

    @Override
    public String createPortfolio() throws IOException {
        Template portfolioTemplate = handlebars.compileInline( "<!DOCTYPE html>" +
                "<html>" +
                "<body>" +
                "<h1>{{header}}</h1>" +
                "<p>{{body}}</p>" +
                "</body>" +
                "</html>");
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("header", "Hi");
        parameterMap.put("body", "photoalbum");
        return portfolioTemplate.apply(parameterMap);
    }
}
