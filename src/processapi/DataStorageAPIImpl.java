package processapi;

import processapi.DataRequest;
import processapi.DataResponse;
import processapi.DataStorageAPI;
import java.util.Collections;

public class DataStorageAPIImpl implements DataStorageAPI {

    @Override
    public DataResponse readInput(DataRequest request) {
        // Return an empty response for now
        return new DataResponse(Collections.emptyList());
    }

    @Override
    public void writeOutput(DataResponse response) {
        // Do nothing yet
        System.out.println("Output not yet written.");
    }
}