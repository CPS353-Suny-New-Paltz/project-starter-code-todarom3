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
    // Dependency on conceptual layer
    private ComputeEngineAPI computeEngineAPI;
    private DataStorageAPI dataStorageAPI;

    public UserComputeAPIImpl(ComputeEngineAPI computeEngineAPI, DataStorageAPI dataStorageAPI) {
        this.computeEngineAPI = computeEngineAPI;
        this.dataStorageAPI = dataStorageAPI;
    }

    @Override
    public UserResponse processUserRequest(UserRequest request) {
        try {
            // Read input
            DataRequest dataRequest = new DataRequest(request.getInputSource());
            DataResponse inputResponse = dataStorageAPI.readInput(dataRequest);
            List<Integer> inputNumbers = inputResponse.getData();

            // ✅ Collect ALL outputs before writing
            List<Integer> allResults = new ArrayList<>();

            for (Integer number : inputNumbers) {
                ComputeResult result = computeEngineAPI.computePrimes(new ComputeRequest(number));

                // accumulate instead of writing multiple times
                allResults.addAll(result.getPrimes());

                System.out.println("Computed primes up to " + number + ": " + result.getPrimes());
                System.out.println("Total primes found: " + result.getTotalCount());
            }

            // ✅ Write ONE time, producing one comma-separated line
            dataStorageAPI.writeOutput(new DataResponse(allResults));

            return new UserResponse("Computation completed successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            return new UserResponse("Error processing request: " + e.getMessage());
        }
    }

}