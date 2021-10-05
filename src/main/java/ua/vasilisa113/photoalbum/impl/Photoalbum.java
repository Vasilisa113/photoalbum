package ua.vasilisa113.photoalbum.impl;

import ua.vasilisa113.photoalbum.RequestListener;

import javax.inject.Inject;

public class Photoalbum {
    private final RequestListener requestListener;
    @Inject
    public Photoalbum(RequestListener arg) {
        requestListener = arg;
    }
}
