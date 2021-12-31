package ua.vasilisa113.photoalbum.impl;

import ua.vasilisa113.photoalbum.RequestListener;
import ua.vasilisa113.photoalbum.config.PhotoalbumConfig;

import javax.inject.Inject;
import java.util.concurrent.CountDownLatch;

public class Photoalbum {
    private final RequestListener requestListener;
    private final CountDownLatch waitForExit = new CountDownLatch(1);
    private final PhotoalbumConfig config;

    @Inject
    public Photoalbum(RequestListener listener, PhotoalbumConfig config) {
        this.requestListener = listener;
        this.config = config;
    }
    public void start(){
        try {
            waitForExit.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void stop(){
        waitForExit.countDown();
    }
    public PhotoalbumConfig getConfig() {
        return config;
    }
}
