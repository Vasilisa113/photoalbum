package ua.vasilisa113.photoalbum.impl;

import ua.vasilisa113.photoalbum.Database;
import ua.vasilisa113.photoalbum.RequestHandler;
import ua.vasilisa113.photoalbum.TemplateHandler;

import javax.inject.Inject;

public class RequestHandlerImpl implements RequestHandler {
    private final Database database;
    private final TemplateHandler templateHandler;

    @Inject
    public RequestHandlerImpl(Database arg0, TemplateHandler arg1) {
        database = arg0;
        templateHandler = arg1;
    }
}
