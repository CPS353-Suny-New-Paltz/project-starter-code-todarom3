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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserComputeAPIMultiThreadedImpl implements UserComputeAPI {

    private final ComputeEngineAPI computeEngineAPI;

    // Upper bound on the number of worker threads 
    private static final int MAX_THREADS = 4;

    public UserComputeAPIMultiThreadedImpl(ComputeEngineAPI computeEngineAPI,
                                           DataStorageAPI dataStorageAPI) {
        if (computeEngineAPI == null) {
            throw new IllegalArgumentException("ComputeEngineAPI dependency cannot be null.");
        }
        // Implementation creates its own storage per request to stay thread-safe.
        this.computeEngineAPI = computeEngineAPI;
    }

    @Override
    public UserResponse processUserRequest(UserRequest request) {

    	//start request
    	long startTime = System.nanoTime();

        // Basic validation 
        if (request == null) {
            return new UserResponse("Error: request cannot be null.");
        }
        if (request.getInputSource() == null || request.getInputSource().isEmpty()) {
            return new UserResponse("Error: input file path is missing.");
        }
        if (request.getOutputDestination() == null
                || request.getOutputDestination().isEmpty()) {
            return new UserResponse("Error: output file path is missing.");
        }
        if (request.getDelimiter() == null) {
            return new UserResponse("Error: delimiter cannot be null.");
        }

        try {
            // Each request gets its own storage instance 
            DataStorageAPIImpl storage = new DataStorageAPIImpl();
            storage.setOutputDelimiter(request.getDelimiter());
            storage.setOutputFilePath(request.getOutputDestination());

            // READ INPUT
            DataResponse inputResponse =
                    storage.readInput(new DataRequest(request.getInputSource()));
            if (inputResponse == null || inputResponse.getData() == null) {
                return new UserResponse("Error: failed to read input.");
            }

            List<Integer> inputNumbers = inputResponse.getData();

            // MULTI-THREADED COMPUTATION
            ExecutorService pool = Executors.newFixedThreadPool(MAX_THREADS);
            List<Future<List<Integer>>> futures = new ArrayList<>();

            for (Integer number : inputNumbers) {
                if (number == null || number < 0) {
                    pool.shutdownNow();
                    return new UserResponse("Error: invalid number in input: " + number);
                }

                futures.add(pool.submit(() -> {
                    ComputeResult result =
                            computeEngineAPI.computePrimes(new ComputeRequest(number));
                    return result.getPrimes();
                }));
            }

            List<Integer> allResults = new ArrayList<>();
            for (Future<List<Integer>> future : futures) {
                allResults.addAll(future.get());
            }

            pool.shutdown();

            // WRITE OUTPUT
            DataResponse writeResponse = storage.writeOutput(new DataResponse(allResults));
            if (writeResponse == null) {
                return new UserResponse("Error: failed to write output.");
            }

            //end request (working)
            long endTime = System.nanoTime();
            long durationMs = (endTime - startTime) / 1_000_000;
            System.out.println("User request completed in " + durationMs + " ms");

            return new UserResponse("Computation completed successfully.");

        } catch (Exception e) {

        	//end request (fail)
        	long endTime = System.nanoTime();
            long durationMs = (endTime - startTime) / 1_000_000;
            System.out.println("User request failed after " + durationMs + " ms");

            // translate exceptions to a safe user response
            return new UserResponse("Error processing request: " + e.getMessage());
        }
    }
}