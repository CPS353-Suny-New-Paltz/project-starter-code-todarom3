package processapi;

import project.annotations.ProcessAPI;

@ProcessAPI
public interface DataStorageAPI {
    DataResponse readInput(DataRequest request);
    void writeOutput(DataResponse response);
}