package ua.vasilisa113.photoalbum;

import io.reactivex.Single;

import java.io.InputStream;

public interface TemplateStorage {
    public Single<String> getTemplate (String projectName, String templateName, String language);
    public InputStream getStaticResource (String projectName, String resourceName, String language);
}

