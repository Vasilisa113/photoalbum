package ua.vasilisa113.photoalbum;

import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import ua.vasilisa113.photoalbum.impl.RequestHandlerImpl;

import java.io.InputStream;

public class RequestHandlerTest {
    private static RequestHandler requestHandler;
    private static InputStream resourceStream;
    @BeforeClass
    public static void setUp (){
        Database database = Mockito.mock(Database.class);
        TemplateHandler templateHandler = Mockito.mock(TemplateHandler.class);
        resourceStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("testPic.jpg");
        Mockito.when(templateHandler.getStaticResource("testResource", "en"))
                .thenReturn(resourceStream);
        requestHandler = new RequestHandlerImpl(database, templateHandler);
    }
    @Test
    public void staticCall(){
        InputStream testResource = requestHandler.handleStaticResource("testResource", "en");
        Assert.assertNotNull("The resource should exist", testResource);
        Assert.assertEquals("Unexpected resource", testResource, resourceStream);
    }
    @Test
    public void inexistingStaticCall (){
        InputStream testResource = requestHandler.handleStaticResource("dsghjf", "en");
        Assert.assertNull("The resource should not exist", testResource);
    }
}
