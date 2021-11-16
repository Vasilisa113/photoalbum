package ua.vasilisa113.photoalbum.impl;

import com.gentics.mesh.core.rest.project.ProjectCreateRequest;
import com.gentics.mesh.core.rest.project.ProjectResponse;
import com.gentics.mesh.core.rest.schema.BinaryFieldSchema;
import com.gentics.mesh.core.rest.schema.FieldSchema;
import com.gentics.mesh.core.rest.schema.SchemaListResponse;
import com.gentics.mesh.core.rest.schema.StringFieldSchema;
import com.gentics.mesh.core.rest.schema.impl.BinaryFieldSchemaImpl;
import com.gentics.mesh.core.rest.schema.impl.SchemaCreateRequest;
import com.gentics.mesh.core.rest.schema.impl.SchemaResponse;
import com.gentics.mesh.core.rest.schema.impl.StringFieldSchemaImpl;
import com.gentics.mesh.rest.client.MeshRestClient;
import com.gentics.mesh.rest.client.MeshRestClientConfig;
import com.gentics.mesh.rest.client.MeshRestClientMessageException;
import com.gentics.mesh.rest.client.impl.MeshRestOkHttpClientImpl;
import ua.vasilisa113.photoalbum.Database;
import ua.vasilisa113.photoalbum.TemplateStorage;
import ua.vasilisa113.photoalbum.config.PhotoalbumConfig;

import javax.inject.Inject;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class MeshStorage implements TemplateStorage, Database {
    @Inject
    public MeshStorage(PhotoalbumConfig config) {
        MeshRestClientConfig.Builder builder = new MeshRestClientConfig.Builder();
        builder.setHost(config.getMeshStorage().getHost());
        builder.setPort(config.getMeshStorage().getPort());
        builder.setSsl(config.getMeshStorage().isSsl());
        MeshRestClient client = new MeshRestOkHttpClientImpl(builder.build());
        //client.setLogin("admin", "admin");
        client.setAPIKey(config.getMeshStorage().getApiKey());
        //GenericMessageResponse response = client.login().blockingGet();
        //UserResponse response = client.me().blockingGet();
        //System.out.println(response.toString());
        ProjectResponse project = null;
        try {
            project = client.findProjectByName(config.getMeshStorage().getProjectName()).blockingGet();
        } catch (Throwable e) {
            if (e.getCause() instanceof MeshRestClientMessageException) {
                MeshRestClientMessageException me = (MeshRestClientMessageException) e.getCause();
                if (HttpURLConnection.HTTP_NOT_FOUND == me.getStatusCode()) {
                    ProjectCreateRequest request = new ProjectCreateRequest();
                    request.setName(config.getMeshStorage().getProjectName());
                    request.setSchemaRef(config.getMeshStorage().getInitialSchemaName());
                    project = client.createProject(request).blockingGet();
                } else {
                    throw new RuntimeException("Error finding project name", e.getCause());
                }
            } else {
                throw new RuntimeException("Error requesting project name", e);
            }
        }
        SchemaListResponse schemasResponse = client.findSchemas().blockingGet();
        if (schemasResponse.getData().stream().noneMatch(schema -> schema.getName().equals("portfolio"))) {
            SchemaCreateRequest portfolioSchemaCreateRequest = new SchemaCreateRequest();
            portfolioSchemaCreateRequest.setName("portfolio");
            portfolioSchemaCreateRequest.setDescription("Portfolio Page");
            List<FieldSchema> portfolioFields = new ArrayList<>();
            StringFieldSchema nameField = new StringFieldSchemaImpl();
            nameField.setName("name");
            nameField.setLabel("Name of the page");
            portfolioFields.add(nameField);
            BinaryFieldSchema logoField = new BinaryFieldSchemaImpl();
            logoField.setName("logo");
            logoField.setLabel("Logo of the page");
            logoField.setAllowedMimeTypes("image/*");
            portfolioFields.add(logoField);
            BinaryFieldSchema backgroundField = new BinaryFieldSchemaImpl();
            backgroundField.setName("background");
            backgroundField.setLabel("Background image");
            backgroundField.setAllowedMimeTypes("image/*");
            portfolioFields.add(backgroundField);
            StringFieldSchema descriptionField = new StringFieldSchemaImpl();
            descriptionField.setName("description");
            descriptionField.setLabel("Description of a page");
            portfolioFields.add(descriptionField);

            portfolioSchemaCreateRequest.setFields(portfolioFields);
            SchemaResponse portfolioResponse = client.createSchema(portfolioSchemaCreateRequest).blockingGet();
            portfolioResponse = client.assignSchemaToProject(project.getName(),portfolioResponse.getUuid()).blockingGet();
        }
    }
}
