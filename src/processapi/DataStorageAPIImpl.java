package processapi;

import processapi.DataRequest;
import processapi.DataResponse;
import processapi.DataStorageAPI;
import java.util.Collections;

public class DataStorageAPIImpl {

    @ProcessAPIPrototype
    public void runPrototype(DataStorageAPI api) {

    	DataRequest request = new DataRequest("dummy-source");
        DataResponse response = api.readInput(request);

        api.writeOutput(response);
    }
}