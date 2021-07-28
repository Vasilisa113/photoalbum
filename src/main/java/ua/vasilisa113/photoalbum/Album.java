package ua.vasilisa113.photoalbum;

public interface Album extends Page {
    byte[][] getPhotos();
    void setPhotos(byte[][] photos);
}
