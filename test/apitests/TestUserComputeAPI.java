package networkapi;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import conceptualapi.ComputeEngineAPI;

public class TestUserComputeAPI {

    @Test
    public void testProcessUserRequest_smoke() {
        // Mock the dependency
        ComputeEngineAPI mockComputeEngine = Mockito.mock(ComputeEngineAPI.class);

        // Create the implementation under test
        UserComputeAPIImpl userCompute = new UserComputeAPIImpl(mockComputeEngine);

        // Create a dummy request
        UserRequest request = new UserRequest("input.txt", "output.txt", ",");

        // Call the method
        UserResponse response = userCompute.processUserRequest(request);

        // Smoke check – response should not be null
        assertNotNull(response, "UserResponse should not be null");
    }
}