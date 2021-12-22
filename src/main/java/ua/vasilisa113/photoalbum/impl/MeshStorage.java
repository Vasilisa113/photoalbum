package ua.vasilisa113.photoalbum.impl;

import com.gentics.mesh.core.rest.node.FieldMap;
import com.gentics.mesh.core.rest.node.FieldMapImpl;
import com.gentics.mesh.core.rest.node.NodeCreateRequest;
import com.gentics.mesh.core.rest.node.NodeResponse;
import com.gentics.mesh.core.rest.node.field.NodeField;
import com.gentics.mesh.core.rest.node.field.StringField;
import com.gentics.mesh.core.rest.project.ProjectCreateRequest;
import com.gentics.mesh.core.rest.project.ProjectResponse;
import com.gentics.mesh.core.rest.schema.*;
import com.gentics.mesh.core.rest.schema.impl.*;
import com.gentics.mesh.rest.client.MeshRestClient;
import com.gentics.mesh.rest.client.MeshRestClientConfig;
import com.gentics.mesh.rest.client.MeshRestClientMessageException;
import com.gentics.mesh.rest.client.MeshWebrootResponse;
import com.gentics.mesh.rest.client.impl.MeshRestOkHttpClientImpl;
import ua.vasilisa113.photoalbum.Database;
import ua.vasilisa113.photoalbum.TemplateStorage;
import ua.vasilisa113.photoalbum.config.PhotoalbumConfig;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MeshStorage implements TemplateStorage, Database {

    public static final String PHOTOALBUM_RESOURCE = "photoalbumResource";
    public static final String PORTFOLIO = "portfolio";
    public static final String PHOTOALBUM_DATA_STORAGE = "photoalbumDataStorage";
    public static final String NAME = "name";
    public static final String CONTENT = "content";
    public static final String PHOTOALBUM_DATA = "photoalbumData";

    protected final MeshRestClient client;

    @Inject
    public MeshStorage(PhotoalbumConfig config) {
        MeshRestClientConfig.Builder builder = new MeshRestClientConfig.Builder();
        builder.setHost(config.getMeshStorage().getHost());
        builder.setPort(config.getMeshStorage().getPort());
        builder.setSsl(config.getMeshStorage().isSsl());
        client = new MeshRestOkHttpClientImpl(builder.build());
        //client.setLogin("admin", "admin");
        client.setAPIKey(config.getMeshStorage().getApiKey());
        //GenericMessageResponse response = client.login().blockingGet();
        //UserResponse response = client.me().blockingGet();
        //System.out.println(response.toString());
        SchemaListResponse schemasResponse = client.findSchemas().blockingGet();
        SchemaResponse photoalbumFolderResponse = schemasResponse.getData().stream().filter(schema -> schema.getName().equals(PHOTOALBUM_DATA_STORAGE)).findAny().orElse(null);
        if (photoalbumFolderResponse == null) {
            SchemaCreateRequest photoalbumFolderSchemaCreateRequest = new SchemaCreateRequest();
            photoalbumFolderSchemaCreateRequest.setName(PHOTOALBUM_DATA_STORAGE);
            photoalbumFolderSchemaCreateRequest.setDescription("description");
            photoalbumFolderSchemaCreateRequest.setDisplayField(NAME);
            photoalbumFolderSchemaCreateRequest.setSegmentField(NAME);
            List<FieldSchema> photoalbumFolderFields = new ArrayList<>();
            StringFieldSchema nameField = new StringFieldSchemaImpl();
            nameField.setName(NAME);
            nameField.setLabel("Name of the page");
            photoalbumFolderFields.add(nameField);
            NodeFieldSchema defaultPageField = new NodeFieldSchemaImpl();
            defaultPageField.setName("defaultPage");
            defaultPageField.setLabel("Default page of Folder");
            photoalbumFolderFields.add(defaultPageField);
            NodeFieldSchema portfolioTemplateField = new NodeFieldSchemaImpl();
            portfolioTemplateField.setName("portfolioTemplate");
            portfolioTemplateField.setLabel("Template of Portfolio Page");
            photoalbumFolderFields.add(portfolioTemplateField);

            photoalbumFolderSchemaCreateRequest.setFields(photoalbumFolderFields);
            photoalbumFolderResponse = client.createSchema(photoalbumFolderSchemaCreateRequest).blockingGet();
        }
        SchemaResponse portfolioResponse = schemasResponse.getData().stream().filter(schema -> schema.getName().equals(PORTFOLIO)).findAny().orElse(null);
        if (portfolioResponse == null) {
            SchemaCreateRequest portfolioSchemaCreateRequest = new SchemaCreateRequest();
            portfolioSchemaCreateRequest.setName(PORTFOLIO);
            portfolioSchemaCreateRequest.setDescription("Portfolio Page");
            portfolioSchemaCreateRequest.setDisplayField(NAME);
            portfolioSchemaCreateRequest.setSegmentField(NAME);
            List<FieldSchema> portfolioFields = new ArrayList<>();
            StringFieldSchema nameField = new StringFieldSchemaImpl();
            nameField.setName(NAME);
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
        SchemaResponse photoalbumResourceResponse = schemasResponse.getData().stream().filter(schema -> schema.getName().equals(PHOTOALBUM_RESOURCE)).findAny().orElse(null);
        if (photoalbumResourceResponse == null) {
            SchemaCreateRequest photoalbumResourceSchemaCreateRequest = new SchemaCreateRequest();
            photoalbumResourceSchemaCreateRequest.setName(PHOTOALBUM_RESOURCE);
            photoalbumResourceSchemaCreateRequest.setDescription("Photoalbum binary Resource");
            photoalbumResourceSchemaCreateRequest.setDisplayField(NAME);
            photoalbumResourceSchemaCreateRequest.setSegmentField(CONTENT);
            List<FieldSchema> photoalbumResourceFields = new ArrayList<>();
            StringFieldSchema nameField = new StringFieldSchemaImpl();
            nameField.setName(NAME);
            nameField.setLabel("Name of the page");
            photoalbumResourceFields.add(nameField);
            BinaryFieldSchema contentField = new BinaryFieldSchemaImpl();
            contentField.setName(CONTENT);
            contentField.setLabel("Content of the resource");
            photoalbumResourceFields.add(contentField);
            photoalbumResourceSchemaCreateRequest.setFields(photoalbumResourceFields);
            photoalbumResourceResponse = client.createSchema(photoalbumResourceSchemaCreateRequest).blockingGet();
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
                    request.setSchemaRef("folder");
                    project = client.createProject(request).blockingGet();
                    photoalbumFolderResponse = client.assignSchemaToProject(project.getName(),photoalbumFolderResponse.getUuid()).blockingGet();
                    photoalbumResourceResponse = client.assignSchemaToProject(project.getName(),photoalbumResourceResponse.getUuid()).blockingGet();
                    portfolioResponse = client.assignSchemaToProject(project.getName(),portfolioResponse.getUuid()).blockingGet();
                } else {
                    throw new RuntimeException("Error finding project name", e.getCause());
                }
            } else {
                throw new RuntimeException("Error requesting project name", e);
            }
        }
        NodeResponse photoalbumDataResponse = null;
        try {
            photoalbumDataResponse = client.webroot(project.getName(), "/" + PHOTOALBUM_DATA).blockingGet().getNodeResponse();
        } catch (Exception e) {
            if (e.getCause() instanceof MeshRestClientMessageException) {
                MeshRestClientMessageException me = (MeshRestClientMessageException) e.getCause();
                if (HttpURLConnection.HTTP_NOT_FOUND == me.getStatusCode()) {
                    NodeCreateRequest photoalbumDataCreateRequest = new NodeCreateRequest();
                    photoalbumDataCreateRequest.setSchema(photoalbumFolderResponse.toReference());
                    photoalbumDataCreateRequest.setParentNode(project.getRootNode());
                    photoalbumDataCreateRequest.setLanguage("en");
                    FieldMap fieldMap = new FieldMapImpl();
                    StringField nameField = StringField.of(PHOTOALBUM_DATA);
                    fieldMap.put(NAME, nameField);
                    photoalbumDataCreateRequest.setFields(fieldMap);
                    photoalbumDataResponse = client.createNode(project.getName(), photoalbumDataCreateRequest).blockingGet();
                } else {
                    throw new RuntimeException("Error finding photoalbum data", e.getCause());
                }
            } else {
                throw new RuntimeException("Error requesting photoalbum data", e);
            }
        }
    }

    @Override
    public String getTemplate(String projectName, String templateName, String language) {
        NodeField photoalbumDataField = client.webroot(projectName, "/" + PHOTOALBUM_DATA).blockingGet()
                .getNodeResponse().getFields().getNodeField(templateName);
        NodeResponse photoalbumData = client.findNodeByUuid(projectName, photoalbumDataField.getUuid()).blockingGet();
        InputStream inputStream = client.downloadBinaryField(projectName, photoalbumData.getUuid(), language, CONTENT).blockingGet().getStream();
        return new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining(""));

    }

    @Override
    public InputStream getStaticResource(String projectName, String resourceName, String language) {
        MeshWebrootResponse resourceNode = client.webroot(projectName, "/" + resourceName).blockingGet();
        return client.downloadBinaryField(projectName, resourceNode.getNodeUuid(), language, CONTENT).blockingGet().getStream();
    }
}
