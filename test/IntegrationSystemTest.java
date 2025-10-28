import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import networkapi.UserComputeAPIImpl;
import conceptualapi.ComputeEngineAPIImpl;
import processapi.InMemoryDataStorageAPI;
import processapi.InMemoryInput;
import processapi.InMemoryOutput;

public class IntegrationSystemTest {

    @Test
    public void testFullSystemIntegration() {
        InMemoryInput input = new InMemoryInput(java.util.Arrays.asList(5, 10, 15));
        InMemoryOutput output = new InMemoryOutput();
        InMemoryDataStorageAPI dataStorage = new InMemoryDataStorageAPI(input, output);

        ComputeEngineAPIImpl computeEngine = new ComputeEngineAPIImpl(dataStorage);

        UserComputeAPIImpl userAPI = new UserComputeAPIImpl(computeEngine);

        Assertions.assertNotNull(response, "Integration should produce a response");
    }
}