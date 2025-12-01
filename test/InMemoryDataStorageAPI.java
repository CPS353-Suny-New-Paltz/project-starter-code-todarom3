import java.util.List;
import processapi.DataRequest;
import processapi.DataResponse;
import processapi.DataStorageAPI;

public class InMemoryDataStorageAPI implements DataStorageAPI {

    private InMemoryInput input;
    private InMemoryOutput output;

    // store delimiter even if unused
    private String outputDelimiter = ",";

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
        // Write each integer as a string to the output, joined using the delimiter
        // add each element separately to maintain test clearly

        List<Integer> nums = response.getData();

        if (nums == null || nums.isEmpty()) {
            return response;
        }

        // Join numbers into ONE delimited string
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums.size(); i++) {
            sb.append(nums.get(i));
            if (i < nums.size() - 1) {
                sb.append(outputDelimiter);
            }
        }

        // Store that one string in output
        output.addOutput(sb.toString());

        return response;
    }

    @Override
    public void setOutputDelimiter(String delimiter) {
        // If caller passes null or empty, fall back to default comma
        if (delimiter == null || delimiter.isEmpty()) {
            this.outputDelimiter = ",";
        } else {
            this.outputDelimiter = delimiter;
        }
    }

    // Used in tests to verify what was written
    public List<String> getWrittenOutput() {
        return output.getOutputData();
    }
}