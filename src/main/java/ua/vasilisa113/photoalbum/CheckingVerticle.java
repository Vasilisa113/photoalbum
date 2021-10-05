package ua.vasilisa113.photoalbum;

import com.gentics.mesh.core.rest.common.GenericMessageResponse;
import com.gentics.mesh.core.rest.user.UserResponse;
import com.gentics.mesh.rest.client.MeshRestClient;
import com.gentics.mesh.rest.client.MeshRestClientConfig;
import com.gentics.mesh.rest.client.impl.MeshRestOkHttpClientImpl;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpConnection;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class CheckingVerticle extends AbstractVerticle {
    @Override
    public void start() {
        // Create a Router
        Router router = Router.router(vertx);
        Handler<RoutingContext> rootHandler = context -> {
            MeshRestClientConfig.Builder builder = new MeshRestClientConfig.Builder();
            builder.setHost("localHost");
            builder.setPort(8080);
            builder.setSsl(false);
            MeshRestClient client = new MeshRestOkHttpClientImpl(builder.build());
            //client.setLogin("admin", "admin");
            client.setAPIKey("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyVXVpZCI6IjFlZmNkMDU0MGQ4NDQxNTk5ZjVhNTk0YmRkODU5OGI4IiwianRpIjoiNUJmM3lUQXhBelVqIiwiaWF0IjoxNjMyNjg1ODk2fQ.qkvrrXQCseNjC33yphyzVjGGILJakd0Qzko2skovhhQ");
            //GenericMessageResponse response = client.login().blockingGet();
            UserResponse response = client.me().blockingGet();
            System.out.println(response.toString());


            // Get the address of the request
            //String address = context.request().connection().remoteAddress().toString();
            HttpServerRequest serverRequest = context.request();
            HttpConnection httpConnection = serverRequest.connection();
            SocketAddress socketAddress = httpConnection.remoteAddress();
            String address = socketAddress.toString();
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
        };

        // Mount the handler for all incoming requests at every path and HTTP method
        router.route("/").handler(rootHandler);

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
