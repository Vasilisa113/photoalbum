package ua.vasilisa113.photoalbum;

import org.codehaus.jackson.map.ObjectMapper;
import ua.vasilisa113.photoalbum.component.DaggerPhotoalbumComponent;
import ua.vasilisa113.photoalbum.component.PhotoalbumComponent;
import ua.vasilisa113.photoalbum.config.PhotoalbumConfig;
import ua.vasilisa113.photoalbum.impl.Photoalbum;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        PhotoalbumComponent photoalbumComponent = DaggerPhotoalbumComponent.builder().build();
        Photoalbum photoalbum = photoalbumComponent.photoalbum();
        photoalbum.start();
    }
}
