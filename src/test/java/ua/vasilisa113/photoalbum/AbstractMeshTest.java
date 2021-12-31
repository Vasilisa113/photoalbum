package ua.vasilisa113.photoalbum;

import org.junit.ClassRule;
import org.testcontainers.containers.GenericContainer;

public abstract class AbstractMeshTest {
    @ClassRule
    public static GenericContainer<?> mesh =
            new GenericContainer<>("gentics/mesh:1.7.9")
                    .withExposedPorts(8080)
                    .withEnv("MESH_GRAPH_DB_DIRECTORY", "null")
                    .withEnv("MESH_INITIAL_ADMIN_PASSWORD", "admin")
                    .withEnv("MESH_INITIAL_ADMIN_PASSWORD_FORCE_RESET", "false");
}
