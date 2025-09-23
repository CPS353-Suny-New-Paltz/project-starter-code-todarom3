package processapi;

import java.util.Arrays;
import java.util.List;
import project.annotations.ProcessAPIPrototype;

public class DataStorageAPIImpl {

    @ProcessAPIPrototype
    public DataResponse readInput(DataRequest request) {

    	List<Integer> dummyData = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10);
        return new DataResponse(dummyData);
    }

    @ProcessAPIPrototype
    public void writeOutput(DataResponse response) {

    	System.out.println("Writing output: " + response.getData());
    }
}