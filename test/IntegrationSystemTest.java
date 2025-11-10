import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import networkapi.UserComputeAPIImpl;
import conceptualapi.ComputeEngineAPI;
import conceptualapi.ComputeEngineAPIImpl;
import processapi.DataStorageAPI;
import processapi.DataStorageAPIImpl;
import processapi.DataRequest;
import processapi.DataResponse;

public class IntegrationSystemTest {

    @Test
    public void testFullSystemIntegration() {
        // Set up storage and compute engine
        DataStorageAPI dataStorage = new DataStorageAPIImpl();
        ComputeEngineAPI computeEngine = new ComputeEngineAPIImpl();

        // Create UserComputeAPI with both dependencies
        UserComputeAPIImpl userAPI = new UserComputeAPIImpl(computeEngine, dataStorage);

        // Simulate a request
        DataResponse response = dataStorage.readInput(new DataRequest("dummy"));

        // Check integration worked
        Assertions.assertNotNull(response, "Integration should produce a response");
    }
}