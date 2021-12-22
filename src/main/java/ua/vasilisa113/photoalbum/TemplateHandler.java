package ua.vasilisa113.photoalbum;

import java.io.IOException;
import java.io.InputStream;

public interface TemplateHandler {
    InputStream getStaticResource (String path, String language);
    String createPortfolio() throws IOException;
}
