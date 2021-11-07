package ua.vasilisa113.photoalbum.module;

import dagger.Module;
import dagger.Provides;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import ua.vasilisa113.photoalbum.config.PhotoalbumConfig;

@Module
public class VertxModule {
    @Provides
    public Vertx getVertx() {
        Vertx vertx = Vertx.vertx();
        return vertx;
    }
    @Provides
    public Router getRouter(Vertx vertx, PhotoalbumConfig config){
        Router router = Router.router(vertx);
        vertx.createHttpServer()
                // Handle every request using the router
                .requestHandler(router)
                // Start listening
                .listen (config.getVertx().getPort())
                // Print the port
                .onSuccess(server ->
                        System.out.println(
                                "HTTP server started on port " + server.actualPort()
                        )
                );
        return router;
    }
}

