package processapi;

import org.junit.jupiter.api.Test;

public class TestDataStorageAPI {

    @Test
    public void testReadAndWrite_smoke() {
        // No mocks needed yet – this API has no external dependencies
        DataStorageAPIImpl dataStorage = new DataStorageAPIImpl();

        // Create a dummy request
        DataRequest request = new DataRequest("dummy-source");

        // Call the readInput method
        DataResponse response = dataStorage.readInput(request);

        // Smoke check – response should not be null
        assertNotNull(response, "DataResponse should not be null");
    }
}