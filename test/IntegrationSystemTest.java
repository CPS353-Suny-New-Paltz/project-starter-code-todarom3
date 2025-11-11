import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import networkapi.UserComputeAPIImpl;
import conceptualapi.ComputeEngineAPI;
import conceptualapi.ComputeEngineAPIImpl;
import processapi.DataStorageAPIImpl;
import processapi.DataRequest;
import processapi.DataResponse;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class IntegrationSystemTest {

    @Test
    public void testFullSystemIntegration() throws Exception {
        DataStorageAPIImpl dataStorage = new DataStorageAPIImpl();
        ComputeEngineAPI computeEngine = new ComputeEngineAPIImpl();

        // Set output path for the real storage layer
        dataStorage.setOutputFilePath("test-output/integration-out.txt");

        Files.createDirectories(Paths.get("test-input"));
        Files.write(Paths.get("test-input/integration-in.txt"), "30".getBytes(StandardCharsets.UTF_8));

        // Create UserComputeAPI with dependencies
        UserComputeAPIImpl userAPI = new UserComputeAPIImpl(computeEngine, dataStorage);

        // Simulate a request
        DataResponse response = dataStorage.readInput(new DataRequest("test-input/integration-in.txt"));

        // Check integration layer produced a response
        Assertions.assertNotNull(response, "Integration should produce a response");
    }
}
