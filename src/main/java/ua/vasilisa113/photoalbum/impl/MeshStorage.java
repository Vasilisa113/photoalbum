package ua.vasilisa113.photoalbum.impl;

import com.gentics.mesh.core.rest.user.UserResponse;
import com.gentics.mesh.rest.client.MeshRestClient;
import com.gentics.mesh.rest.client.MeshRestClientConfig;
import com.gentics.mesh.rest.client.impl.MeshRestOkHttpClientImpl;
import ua.vasilisa113.photoalbum.Database;
import ua.vasilisa113.photoalbum.TemplateStorage;
import ua.vasilisa113.photoalbum.config.PhotoalbumConfig;

import javax.inject.Inject;

public class MeshStorage implements TemplateStorage, Database {
    @Inject
    public MeshStorage (PhotoalbumConfig config){
        MeshRestClientConfig.Builder builder = new MeshRestClientConfig.Builder();
        builder.setHost(config.getMeshStorage().getHost());
        builder.setPort(config.getMeshStorage().getPort());
        builder.setSsl(config.getMeshStorage().isSsl());
        MeshRestClient client = new MeshRestOkHttpClientImpl(builder.build());
        //client.setLogin("admin", "admin");
        client.setAPIKey(config.getMeshStorage().getApiKey());
        //GenericMessageResponse response = client.login().blockingGet();
        UserResponse response = client.me().blockingGet();
        System.out.println(response.toString());
    }
}
