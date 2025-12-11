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
        // No validation needed, this API always returns in-memory data
        List<Integer> inputList = input.getInputData();
        return new DataResponse(inputList);
    }

    @Override
    public DataResponse writeOutput(DataResponse response) {
        // Test expects each integer written as a separate string
        for (Integer num : response.getData()) {
            output.addOutput(String.valueOf(num));
        }
        return response;
    }

    // For tests to inspect what was written
    public List<String> getWrittenOutput() {
        return output.getOutputData();
    }

    @Override
    public void setOutputDelimiter(String delimiter) {
        // Not used because test environment does not require delimiter logic
    }

    @Override
    public void setOutputFilePath(String path) {
        // Not used in the in-memory implementation
    }
}