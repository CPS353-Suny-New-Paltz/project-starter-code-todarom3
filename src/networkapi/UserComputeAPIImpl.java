package networkapi;

import conceptualapi.ComputeEngineAPI;
import conceptualapi.ComputeRequest;
import conceptualapi.ComputeResult;
import processapi.DataRequest;
import processapi.DataResponse;
import processapi.DataStorageAPI;

import java.util.ArrayList;
import java.util.List;

public class UserComputeAPIImpl implements UserComputeAPI {

    private final ComputeEngineAPI computeEngineAPI;
    private final DataStorageAPI dataStorageAPI;

    public UserComputeAPIImpl(ComputeEngineAPI computeEngineAPI, DataStorageAPI dataStorageAPI) {
        if (computeEngineAPI == null) {
            throw new IllegalArgumentException("ComputeEngineAPI dependency cannot be null.");
        }
        if (dataStorageAPI == null) {
            throw new IllegalArgumentException("DataStorageAPI dependency cannot be null.");
        }

        this.computeEngineAPI = computeEngineAPI;
        this.dataStorageAPI = dataStorageAPI;
    }

    @Override
    public UserResponse processUserRequest(UserRequest request) {
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

        try {
            // Read input file
            DataRequest dataRequest = new DataRequest(request.getInputSource());
            DataResponse inputResponse = dataStorageAPI.readInput(dataRequest);
            List<Integer> inputNumbers = inputResponse.getData();

            if (inputNumbers == null) {
                return new UserResponse("Error: input file contained no valid data.");
            }

            // Collect ALL prime results before writing
            List<Integer> allResults = new ArrayList<>();

            for (Integer number : inputNumbers) {
                if (number == null || number < 0) {
                    return new UserResponse("Error: invalid number in input: " + number);
                }

                ComputeResult result = computeEngineAPI.computePrimes(new ComputeRequest(number));
                allResults.addAll(result.getPrimes());
            }

            // Set delimiter before writing
            dataStorageAPI.setOutputDelimiter(request.getDelimiter());

            // Single write to output file
            dataStorageAPI.writeOutput(new DataResponse(allResults));

            return new UserResponse("Computation completed successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            return new UserResponse("Error processing request: " + e.getMessage());
        }
    }
}
