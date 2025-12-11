package networkapi;

import processapi.DataRequest;
import processapi.DataResponse;
import processapi.DataStorageAPI;

import java.util.List;

public class UserComputeHelper {

    // Performs shared validation and reads input data.
    // Returns either: List<Integer> on success, or UserResponse on error
    public static Object readInputOrError(UserRequest request, DataStorageAPI dataStorageAPI) {

        if (request == null) {
            return new UserResponse("Error: request cannot be null.");
        }
        if (request.getInputSource() == null || request.getInputSource().isEmpty()) {
            return new UserResponse("Error: input file path is missing.");
        }
        if (request.getOutputDestination() == null || request.getOutputDestination().isEmpty()) {
            return new UserResponse("Error: output file path is missing.");
        }
        if (request.getDelimiter() == null) {
            return new UserResponse("Error: delimiter cannot be null.");
        }

        // Configure delimiter and output file 
        try {
            dataStorageAPI.setOutputDelimiter(request.getDelimiter());
            dataStorageAPI.setOutputFilePath(request.getOutputDestination());
        } catch (Exception e) {
            return new UserResponse("Error: " + e.getMessage());
        }

        // Read input file
        DataResponse input =
                dataStorageAPI.readInput(new DataRequest(request.getInputSource()));

        if (input == null || input.getData() == null) {
            return new UserResponse("Error: failed to read input.");
        }

        return input.getData();
    }

    // Writes results
    public static UserResponse writeOutput(List<Integer> results, DataStorageAPI storage) {
        DataResponse response = storage.writeOutput(new DataResponse(results));
        if (response == null) {
            return new UserResponse("Error: failed to write output.");
        }
        return new UserResponse("Computation completed successfully.");
    }
}