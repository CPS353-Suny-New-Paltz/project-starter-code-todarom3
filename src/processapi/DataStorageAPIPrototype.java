package processapi;

import project.annotations.ProcessAPIPrototype;

public class DataStorageAPIPrototype {

    @ProcessAPIPrototype
    public void runPrototype(DataStorageAPI api) {

    	DataRequest request = new DataRequest("dummy-source");
        DataResponse response = api.readInput(request);

        api.writeOutput(response);
    }
}