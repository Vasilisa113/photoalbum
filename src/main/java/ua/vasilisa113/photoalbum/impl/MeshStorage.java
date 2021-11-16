package ua.vasilisa113.photoalbum.impl;

import com.gentics.mesh.core.rest.project.ProjectCreateRequest;
import com.gentics.mesh.core.rest.project.ProjectResponse;
import com.gentics.mesh.core.rest.schema.*;
import com.gentics.mesh.core.rest.schema.impl.*;
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
        SchemaListResponse schemasResponse = client.findSchemas().blockingGet();
        SchemaResponse photoalbumFolderResponse = schemasResponse.getData().stream().filter(schema -> schema.getName().equals("photoalbumFolder")).findAny().orElse(null);
        if (photoalbumFolderResponse == null) {
            SchemaCreateRequest photoalbumFolderSchemaCreateRequest = new SchemaCreateRequest();
            photoalbumFolderSchemaCreateRequest.setName(config.getMeshStorage().getInitialSchemaName());
            photoalbumFolderSchemaCreateRequest.setDescription("photoalbumFolder");
            photoalbumFolderSchemaCreateRequest.setContainer(true);
            List<FieldSchema> photoalbumFolderFields = new ArrayList<>();
            NodeFieldSchema defaultPageField = new NodeFieldSchemaImpl();
            defaultPageField.setName("defaultPage");
            defaultPageField.setLabel("Default page of Folder");
            photoalbumFolderFields.add(defaultPageField);

            photoalbumFolderSchemaCreateRequest.setFields(photoalbumFolderFields);
            photoalbumFolderResponse = client.createSchema(photoalbumFolderSchemaCreateRequest).blockingGet();
        }
        SchemaResponse portfolioResponse = schemasResponse.getData().stream().filter(schema -> schema.getName().equals("portfolio")).findAny().orElse(null);
        if (portfolioResponse == null) {
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
            portfolioResponse = client.createSchema(portfolioSchemaCreateRequest).blockingGet();
        }
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
                    photoalbumFolderResponse = client.assignSchemaToProject(project.getName(),photoalbumFolderResponse.getUuid()).blockingGet();
                    portfolioResponse = client.assignSchemaToProject(project.getName(),portfolioResponse.getUuid()).blockingGet();
                } else {
                    throw new RuntimeException("Error finding project name", e.getCause());
                }
            } else {
                throw new RuntimeException("Error requesting project name", e);
            }
        }
    }
}
