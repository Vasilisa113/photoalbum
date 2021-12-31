package ua.vasilisa113.photoalbum;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.impl.HttpClientImpl;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import org.junit.*;
import ua.vasilisa113.photoalbum.component.DaggerPhotoalbumComponent;
import ua.vasilisa113.photoalbum.component.PhotoalbumComponent;
import ua.vasilisa113.photoalbum.config.PhotoalbumConfig;
import ua.vasilisa113.photoalbum.impl.Photoalbum;

public class RequestListenerTest extends AbstractMeshTest{

    private static Photoalbum photoalbum;
    private static PhotoalbumConfig config;
    @BeforeClass
    public static void setUpPhotoalbum (){
        PhotoalbumComponent.Builder builder = DaggerPhotoalbumComponent.builder();
        config = new PhotoalbumConfig();
        config.getMeshStorage().setApiKey(null);
        config.getMeshStorage().setLogin("admin");
        config.getMeshStorage().setPassword("admin");
        config.getMeshStorage().setPort(mesh.getMappedPort(8080));
        builder.setConfig(()->config);
        PhotoalbumComponent photoalbumComponent = builder.build();
        photoalbum = photoalbumComponent.photoalbum();
    }

    @Test
    public void testRequestListenerStarted () throws InterruptedException {
        WebClient client = WebClient.create(Vertx.vertx(), new WebClientOptions().setDefaultPort(config.getVertx().getPort()));
        Future<HttpResponse<Buffer>> maybeChecked = client.get("/")
                .send()
                .onSuccess(result -> Assert.fail("Should not return any data"))
                .onFailure(exception -> Assert.assertNotNull(exception));
        while (!maybeChecked.isComplete()) {
            Thread.sleep(50);
        }
    }
    @AfterClass
    public static void tearDownPhotoalbum(){
        if (photoalbum != null){
            photoalbum.stop();
        }
    }
}
