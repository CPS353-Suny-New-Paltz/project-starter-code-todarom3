import java.util.List;
import processapi.DataRequest;
import processapi.DataResponse;
import processapi.DataStorageAPI;

public class InMemoryDataStorageAPI implements DataStorageAPI {

    private InMemoryInput input;
    private InMemoryOutput output;

    public InMemoryDataStorageAPI(InMemoryInput input, InMemoryOutput output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public DataResponse readInput(DataRequest request) {
        // Wrapping the in memory input list as a DataResponse
        List<Integer> inputList = input.getInputData();
        return new DataResponse(inputList);
    }

    @Override
    public DataResponse writeOutput(DataResponse response) {
        // Write each integer as a string to the output 
        for (Integer num : response.getData()) {
            output.addOutput(String.valueOf(num));
        }

        // confirm success
        return response;
    }

    // Verifying output in tests
    public List<String> getWrittenOutput() {
        return output.getOutputData();
    }
}