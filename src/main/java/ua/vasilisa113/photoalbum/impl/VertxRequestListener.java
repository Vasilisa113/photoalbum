package ua.vasilisa113.photoalbum.impl;

import ua.vasilisa113.photoalbum.RequestHandler;
import ua.vasilisa113.photoalbum.RequestListener;

import javax.inject.Inject;

public class VertxRequestListener implements RequestListener {
    private final RequestHandler requestHandler;

    @Inject
    public VertxRequestListener(RequestHandler arg) {
        requestHandler = arg;
    }
}
