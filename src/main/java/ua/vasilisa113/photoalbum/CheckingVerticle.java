package ua.vasilisa113.photoalbum;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class CheckingVerticle extends AbstractVerticle {
    @Override
    public void start() {
        // Create a Router
        Router router = Router.router(vertx);

        // Mount the handler for all incoming requests at every path and HTTP method
        router.route("/").handler(context -> {
            // Get the address of the request
            String address = context.request().connection().remoteAddress().toString();
            // Get the query parameter "name"
            MultiMap queryParams = context.queryParams();
            String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";
            // Write a json response
            String age = queryParams.contains("age") ? queryParams.get("age") : "-";
            String height = queryParams.contains("height") ? queryParams.get("height") : "-";
            context.json(
                    new JsonObject()
                            .put("name", name)
                            .put("address", address)
                            .put("age", age)
                            .put("heigh", height)
                            .put("message", "Hello " + name + " connected from " + address + ", " + age + " years old"
                            + ", " + height + " cm tall")
            );
        });

        // Mount the handler for all incoming requests at every path and HTTP method
        router.route("/secret").handler(context -> {
            // Get the address of the request
            String address = context.request().connection().remoteAddress().toString();
            // Get the query parameter "name"
            MultiMap queryParams = context.queryParams();
            String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";
            // Write a json response
            String age = queryParams.contains("age") ? queryParams.get("age") : "-";
            String height = queryParams.contains("height") ? queryParams.get("height") : "-";
            context.json(
                    new JsonObject()
                            .put("name", name)
                            .put("address", address)
                            .put("age", age)
                            .put("heigh", height)
                            .put("message", "Hello Alf")
            );
        });

        // Mount the handler for all incoming requests at every path and HTTP method
        router.route("/photos").handler(context -> {
            // Get the address of the request
            String address = context.request().connection().remoteAddress().toString();
            // Get the query parameter "name"
            MultiMap queryParams = context.queryParams();
            String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";
            // Write a json response
            String age = queryParams.contains("age") ? queryParams.get("age") : "-";
            String height = queryParams.contains("height") ? queryParams.get("height") : "-";
            context.json(
                    new JsonObject()
                            .put("name", name)
                            .put("address", address)
                            .put("age", age)
                            .put("heigh", height)
                            .put("message", "Look at my photos")
            );
        });

        // Create the HTTP server
        vertx.createHttpServer()
                // Handle every request using the router
                .requestHandler(router)
                // Start listening
                .listen(8888)
                // Print the port
                .onSuccess(server ->
                        System.out.println(
                                "HTTP server started on port " + server.actualPort()
                        )
                );

    }

}
