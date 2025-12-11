package processapi;

import project.annotations.ProcessAPI;

@ProcessAPI
public interface DataStorageAPI {
    DataResponse readInput(DataRequest request);

    DataResponse writeOutput(DataResponse response);

    // Configures how to separate values when writing output
    void setOutputDelimiter(String delimiter);

    // Configures where to write the output file
    void setOutputFilePath(String path);
}