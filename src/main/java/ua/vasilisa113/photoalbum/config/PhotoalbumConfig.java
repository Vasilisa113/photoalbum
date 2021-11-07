package ua.vasilisa113.photoalbum.config;

public class PhotoalbumConfig {
    public MeshStorageConfig getMeshStorage() {
        return meshStorage;
    }

    public void setMeshStorage(MeshStorageConfig meshStorage) {
        this.meshStorage = meshStorage;
    }

    private MeshStorageConfig meshStorage = new MeshStorageConfig();

    public VertxConfig getVertx() {
        return vertx;
    }

    public void setVertx(VertxConfig vertx) {
        this.vertx = vertx;
    }

    private VertxConfig vertx = new VertxConfig();
}
