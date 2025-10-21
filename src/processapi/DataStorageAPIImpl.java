package processapi;

import conceptualapi.ComputeEngineAPI;
import java.util.Collections;

public class DataStorageAPIImpl implements DataStorageAPI {

    private ComputeEngineAPI computeEngineAPI; // Dependency (optional, for conceptual layer communication)

    public DataStorageAPIImpl(ComputeEngineAPI computeEngineAPI) {
        this.computeEngineAPI = computeEngineAPI;
    }

    @Override
    public DataResponse readInput(DataRequest request) {
        // Empty implementation: return a DataResponse with empty data
        return new DataResponse(Collections.emptyList());
    }

    @Override
    public void writeOutput(DataResponse response) {
        // Empty implementation: do nothing for now
    }
}