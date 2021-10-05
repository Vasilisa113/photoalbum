package ua.vasilisa113.photoalbum.impl;

import ua.vasilisa113.photoalbum.TemplateHandler;
import ua.vasilisa113.photoalbum.TemplateStorage;

import javax.inject.Inject;

public class HandlebarsTemplateHandler implements TemplateHandler {
    private final TemplateStorage templateStorage;
    @Inject
    public HandlebarsTemplateHandler(TemplateStorage arg) {
        templateStorage = arg;
    }
}
