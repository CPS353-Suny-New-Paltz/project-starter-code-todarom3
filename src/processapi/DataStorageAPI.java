package processapi;

import project.annotations.ProcessAPI;

@ProcessAPI
public interface DataStorageAPI {
    DataResponse readInput(DataRequest request);
    DataResponse writeOutput(DataResponse response);

void setOutputDelimiter(String delimiter);
}