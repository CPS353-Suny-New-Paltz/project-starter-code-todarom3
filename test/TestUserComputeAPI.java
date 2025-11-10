import networkapi.UserComputeAPIImpl;
import networkapi.UserRequest;
import networkapi.UserResponse;
import conceptualapi.ComputeEngineAPI;
import processapi.DataStorageAPI;
import processapi.DataStorageAPIImpl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.junit.jupiter.api.Assertions;

public class TestUserComputeAPI {

    @Test
    public void testProcessUserRequest_smoke() {
        // Mock dependencies
        ComputeEngineAPI mockComputeEngine = Mockito.mock(ComputeEngineAPI.class);
        DataStorageAPI mockDataStorage = Mockito.mock(DataStorageAPI.class);

        // Create the implementation under test with both dependencies
        UserComputeAPIImpl userCompute = new UserComputeAPIImpl(mockComputeEngine, mockDataStorage);

        // Create a dummy request
        UserRequest request = new UserRequest("input.txt", "output.txt", ",");

        // Call the method
        UserResponse response = userCompute.processUserRequest(request);

        // Smoke check – response should not be null
        Assertions.assertNotNull(response, "UserResponse should not be null");
    }
}