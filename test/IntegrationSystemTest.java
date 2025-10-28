import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import networkapi.UserComputeAPIImpl;
import conceptualapi.ComputeEngineAPIImpl;
import processapi.DataRequest;
import processapi.DataResponse;
import networkapi.UserRequest;
import networkapi.UserResponse;

public class IntegrationSystemTest {

    @Test
    public void testFullSystemIntegration() {
        InMemoryInput input = new InMemoryInput(java.util.Arrays.asList(5, 10, 15));
        InMemoryOutput output = new InMemoryOutput();
        InMemoryDataStorageAPI dataStorage = new InMemoryDataStorageAPI(input, output);

        ComputeEngineAPIImpl computeEngine = new ComputeEngineAPIImpl(dataStorage);

        UserComputeAPIImpl userAPI = new UserComputeAPIImpl(computeEngine);

        DataResponse response = dataStorage.readInput(new DataRequest("dummy"));
        Assertions.assertNotNull(response, "Integration should produce a response");
    }
}