package ua.vasilisa113.photoalbum.config;

public class PhotoalbumConfig {
    public MeshStorageConfig getMeshStorage() {
        return meshStorage;
    }

    public void setMeshStorage(MeshStorageConfig meshStorage) {
        this.meshStorage = meshStorage;
    }

    private MeshStorageConfig meshStorage;
}
