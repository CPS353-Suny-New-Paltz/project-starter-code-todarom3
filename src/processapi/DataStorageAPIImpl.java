package processapi;

import java.util.Collections;

public class DataStorageAPIImpl {

    @ProcessAPIPrototype
    public void runPrototype(DataStorageAPI api) {

    	DataRequest request = new DataRequest("dummy-source");
        DataResponse response = api.readInput(request);

        api.writeOutput(response);
    }
}