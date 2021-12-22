package ua.vasilisa113.photoalbum;

import java.io.InputStream;

public interface TemplateStorage {
    public String getTemplate (String projectName, String templateName, String language);
    public InputStream getStaticResource (String projectName, String resourceName, String language);
}

