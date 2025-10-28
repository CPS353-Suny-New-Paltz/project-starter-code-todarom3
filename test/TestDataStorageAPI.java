import processapi.DataStorageAPIImpl;
import processapi.DataRequest;
import processapi.DataResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class TestDataStorageAPI {

    @Test
    public void testReadAndWrite_smoke() {
        DataStorageAPIImpl dataStorage = new DataStorageAPIImpl();

        // Create a dummy request
        DataRequest request = new DataRequest("dummy-source");

        // Call readInput()
        DataResponse response = dataStorage.readInput(request);
        Assertions.assertNotNull(response, "DataResponse should not be null");

        // Call writeOutput()
        DataResponse writeResponse = dataStorage.writeOutput(response);
        Assertions.assertNotNull(writeResponse, "writeOutput should return a DataResponse");
    }
}