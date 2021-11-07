package ua.vasilisa113.photoalbum.impl;

import ua.vasilisa113.photoalbum.RequestListener;

import javax.inject.Inject;
import java.util.concurrent.CountDownLatch;

public class Photoalbum {
    private final RequestListener requestListener;
    private final CountDownLatch waitForExit = new CountDownLatch(1);
    @Inject
    public Photoalbum(RequestListener arg) {
        requestListener = arg;
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
}
