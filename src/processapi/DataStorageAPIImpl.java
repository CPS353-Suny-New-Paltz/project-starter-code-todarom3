package processapi;

import java.util.Arrays;
import java.util.List;
import project.annotations.ProcessAPIPrototype;

public class DataStorageAPIImpl implements DataStorageAPI {

    @Override
    @ProcessAPIPrototype
    public DataResponse readInput(DataRequest request) {
        // Prototype 
        List<Integer> dummyData = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10);
        return new DataResponse(dummyData);
    }

    @Override
    @ProcessAPIPrototype
    public void writeOutput(DataResponse response) {
        // Prototype
        System.out.println("Writing output: " + response.getData());
    }
}