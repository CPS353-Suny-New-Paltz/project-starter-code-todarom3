package conceptualapi;

import java.util.Collections;

public class ComputeEngineAPIImpl implements ComputeEngineAPI {

    // Example: might depend on DataStorageAPI (from processapi)
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