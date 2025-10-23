package conceptualapi;

import conceptualapi.ComputeEngineAPI;
import conceptualapi.ComputeRequest;
import conceptualapi.ComputeResult;
import processapi.DataStorageAPI;
import java.util.Collections;

public class ComputeEngineAPIImpl implements ComputeEngineAPI {

    // Might depend on DataStorageAPI (from processapi)
    private processapi.DataStorageAPI dataStorageAPI;

    public ComputeEngineAPIImpl(processapi.DataStorageAPI dataStorageAPI) {
        this.dataStorageAPI = dataStorageAPI;
    }

    @Override
    public ComputeResult computePrimes(ComputeRequest request) {
        // Return an empty result for now
        return new ComputeResult(Collections.emptyList(), 0);
    }
}