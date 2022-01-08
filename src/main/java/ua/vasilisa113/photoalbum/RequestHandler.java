package ua.vasilisa113.photoalbum;

import io.reactivex.Single;

import java.io.InputStream;

public interface RequestHandler {
    public Single<String> handlePortfolio();
    public void handleAlbum();
    public void handlePrice();
    public void handleBlog();
    public void handleContacts();

    public InputStream handleStaticResource(String path, String lang);
}
