package ua.vasilisa113.photoalbum;

import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import ua.vasilisa113.photoalbum.impl.RequestHandlerImpl;

import java.io.InputStream;

public class RequestHandlerTest {
    private static RequestHandler requestHandler;
    private static RoutingContext routingContext;
    @BeforeClass
    public static void setUp (){
        Database database = Mockito.mock(Database.class);
        TemplateHandler templateHandler = Mockito.mock(TemplateHandler.class);
        InputStream resourceStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("testPic.jpg");
        Mockito.when(templateHandler.getStaticResource(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(resourceStream);
        routingContext = Mockito.mock(RoutingContext.class);
        HttpServerResponse response = Mockito.mock(HttpServerResponse.class);
        Mockito.when(routingContext.response()).thenReturn(response);
        Mockito.when(routingContext.vertx()).thenReturn(Vertx.vertx());
        requestHandler = new RequestHandlerImpl(database, templateHandler);
    }
    @Test
    public void staticCall(){
        MultiMap queryParams = MultiMap.caseInsensitiveMultiMap().add("lang", "en");
        Mockito.when(routingContext.queryParams()).thenReturn(queryParams);
        Mockito.when(routingContext.pathParam(Mockito.anyString())).thenReturn("/");
        requestHandler.handleStaticResource(routingContext);
    }
}
