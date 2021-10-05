package ua.vasilisa113.photoalbum;

import ua.vasilisa113.photoalbum.component.DaggerPhotoalbumComponent;
import ua.vasilisa113.photoalbum.component.PhotoalbumComponent;
import ua.vasilisa113.photoalbum.impl.Photoalbum;

public class Main {
    public static void main(String[] args) {
        PhotoalbumComponent photoalbumComponent = DaggerPhotoalbumComponent.builder().build();
        Photoalbum photoalbum = photoalbumComponent.photoalbum();
        //Vertx vertx = Vertx.vertx();
        //vertx.deployVerticle(new CheckingVerticle());
    }
}
