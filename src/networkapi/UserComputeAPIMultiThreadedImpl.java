package networkapi;

import conceptualapi.ComputeEngineAPI;
import conceptualapi.ComputeRequest;
import conceptualapi.ComputeResult;
import processapi.DataStorageAPI;
import processapi.DataStorageAPIImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserComputeAPIMultiThreadedImpl implements UserComputeAPI {

    private final ComputeEngineAPI computeEngineAPI;
    private final DataStorageAPI dataStorageAPI;

    // Upper bound for number of threads 
    private static final int MAX_THREADS = 4;

    public UserComputeAPIMultiThreadedImpl(
            ComputeEngineAPI computeEngineAPI,
            DataStorageAPI dataStorageAPI) {

        this.computeEngineAPI = computeEngineAPI;
        this.dataStorageAPI = dataStorageAPI;
    }

    @Override
    public UserResponse processUserRequest(UserRequest request) {

        try {
            // Shared input validation & reading
            Object input = UserComputeHelper.readInputOrError(request, dataStorageAPI);
            if (input instanceof UserResponse) {
                return (UserResponse) input;
            }

            @SuppressWarnings("unchecked")
            List<Integer> inputNumbers = (List<Integer>) input;

            // Set output file path via implementation
            ((DataStorageAPIImpl) dataStorageAPI).setOutputFilePath(request.getOutputDestination());

            // Multi-threaded processing of conceptual compute calls
            ExecutorService pool = Executors.newFixedThreadPool(MAX_THREADS);
            List<Future<List<Integer>>> futures = new ArrayList<>();

            for (Integer n : inputNumbers) {
                futures.add(pool.submit(() -> {
                    ComputeResult result =
                            computeEngineAPI.computePrimes(new ComputeRequest(n));
                    return result.getPrimes();
                }));
            }

            // Collect results
            List<Integer> allResults = new ArrayList<>();
            for (Future<List<Integer>> f : futures) {
                allResults.addAll(f.get());
            }

            pool.shutdown();

            // Write output
            return UserComputeHelper.writeOutput(allResults, dataStorageAPI);

        } catch (Exception e) {
            return new UserResponse("Error processing request: " + e.getMessage());
        }
    }
}