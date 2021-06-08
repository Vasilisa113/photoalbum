package ua.vasilisa113.photoalbum;

import io.vertx.core.Vertx;

public class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new CheckingVerticle());
    }
}
