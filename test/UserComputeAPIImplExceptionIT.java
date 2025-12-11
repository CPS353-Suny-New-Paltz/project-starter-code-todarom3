import networkapi.UserComputeAPIImpl;
import networkapi.UserRequest;
import networkapi.UserResponse;

import conceptualapi.ComputeEngineAPIImpl;
import processapi.DataRequest;
import processapi.DataResponse;
import processapi.DataStorageAPI;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*Creates a fake failing DataStorageAPI that throws an 
exception during input reading. Then it checks that the 
coordinator returns a safe UserResponse instead of letting the 
exception escape. */

public class UserComputeAPIImplExceptionIT {

    // Fake DataStorageAPI that always throws when reading input
	 private static class FailingDataStorageAPI implements DataStorageAPI {

	        @Override
	        public DataResponse readInput(DataRequest request) {
	            throw new RuntimeException("Simulated read failure");
	        }

	        @Override
	        public DataResponse writeOutput(DataResponse response) {
	            throw new RuntimeException("Simulated write failure");
	        }

	        @Override
	        public void setOutputDelimiter(String delimiter) {
	        }

	        @Override
	        public void setOutputFilePath(String path) {
	        }
	    }

    @Test
    public void testExceptionHandling_translatedToUserResponse() {
        // coordinator with normal compute engine + failing storage
        UserComputeAPIImpl api = new UserComputeAPIImpl(
            new ComputeEngineAPIImpl(),
            new FailingDataStorageAPI()
        );

        // build valid request
        UserRequest request = new UserRequest(
                "input.txt",
                "output.txt",
                ","
        );

        UserResponse response = api.processUserRequest(request);

        assertNotNull(response);
        assertTrue(response.getMessage().startsWith("Error"),
                "Coordinator should translate exceptions into an error message");

        System.out.println("UserResponse: " + response.getMessage());
    }
}
