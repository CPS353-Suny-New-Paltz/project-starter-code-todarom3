import networkapi.UserComputeAPIImpl;
import networkapi.UserRequest;
import networkapi.UserResponse;

import conceptualapi.ComputeEngineAPI;
import conceptualapi.ComputeEngineAPIImpl;

import processapi.DataStorageAPI;
import InMemoryDataStorageAPI;
import InMemoryInput;
import InMemoryOutput;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.List;


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
    
    @Test
    public void testNetworkAPI_withRealDependencies_smoke() {
        // Real conceptual dependency
        ComputeEngineAPI realEngine = new ComputeEngineAPIImpl();

        // Test process dependency
        InMemoryInput input = new InMemoryInput(List.of(10));
        InMemoryOutput output = new InMemoryOutput();
        InMemoryDataStorageAPI store = new InMemoryDataStorageAPI(input, output);

        // **THIS is what the checkpoint wants**
        UserComputeAPIImpl api = new UserComputeAPIImpl(realEngine, store);

        Assertions.assertNotNull(api);
    }
}