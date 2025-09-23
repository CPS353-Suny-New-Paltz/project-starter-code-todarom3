package processapi;

import project.annotations.ProcessAPI;

@ProcessAPI
public interface DataStorageAPI {

    public static DataResponse readInput(DataRequest request) {
    	return null;
    }

    public static void writeOutput(DataResponse response) {
    }
}