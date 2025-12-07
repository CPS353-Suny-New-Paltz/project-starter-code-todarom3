package networkapi;

import conceptualapi.ComputeEngineAPI;
import conceptualapi.ComputeRequest;
import conceptualapi.ComputeResult;
import processapi.DataRequest;
import processapi.DataResponse;
import processapi.DataStorageAPI;
import processapi.DataStorageAPIImpl;

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
            ((DataStorageAPIImpl) dataStorageAPI)
                    .setOutputFilePath(request.getOutputDestination());

            dataStorageAPI.setOutputDelimiter(request.getDelimiter());

            DataResponse inputResponse =
                    dataStorageAPI.readInput(new DataRequest(request.getInputSource()));

            if (inputResponse == null || inputResponse.getData() == null) {
                return new UserResponse("Error: failed to read input.");
            }

            List<Integer> inputNumbers = inputResponse.getData();
            List<Integer> allResults = new ArrayList<>();

            for (Integer number : inputNumbers) {
                if (number == null || number < 0) {
                    return new UserResponse("Error: invalid number in input: " + number);
                }
                ComputeResult result =
                        computeEngineAPI.computePrimes(new ComputeRequest(number));
                allResults.addAll(result.getPrimes());
            }

            DataResponse writeResponse =
                    dataStorageAPI.writeOutput(new DataResponse(allResults));

            if (writeResponse == null) {
                return new UserResponse("Error: failed to write output.");
            }

            return new UserResponse("Computation completed successfully.");

        } catch (Exception e) {
            return new UserResponse("Error processing request: " + e.getMessage());
        }
    }
}