package processapi;

import java.util.Collections;

public class DataStorageAPIImpl implements DataStorageAPI {

    @Override
    public DataResponse readInput(DataRequest request) {
        // Return empty response
        return new DataResponse(Collections.emptyList());
    }

    @Override
    public DataResponse writeOutput(DataResponse response) {
        // Return the same response to confirm it is working
        System.out.println("Output written successfully (placeholder).");
        return response;
    }
}