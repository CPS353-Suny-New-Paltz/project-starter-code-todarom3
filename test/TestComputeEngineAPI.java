import conceptualapi.ComputeEngineAPIImpl;
import conceptualapi.ComputeRequest;
import conceptualapi.ComputeResult;
import processapi.DataStorageAPI;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.junit.jupiter.api.Assertions;

public class TestComputeEngineAPI {

    @Test
    public void testComputePrimes_smoke() {
        // Mock the dependency
        DataStorageAPI mockDataStorage = Mockito.mock(DataStorageAPI.class);

        // Create the implementation under test
        ComputeEngineAPIImpl computeEngine = new ComputeEngineAPIImpl(mockDataStorage);

        // Create a dummy request
        ComputeRequest request = new ComputeRequest(10);

        // Call the method
        ComputeResult result = computeEngine.computePrimes(request);

        // Smoke check – result should not be null
        Assertions.assertNotNull(result, "ComputeResult should not be null");
    }
}