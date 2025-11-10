package conceptualapi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import processapi.DataStorageAPI;
import processapi.DataResponse;

public class ComputeEngineAPIImpl implements ComputeEngineAPI {
    // Dependency on DataStorageAPI
    private DataStorageAPI dataStorageAPI;

    public ComputeEngineAPIImpl(DataStorageAPI dataStorageAPI) {
        this.dataStorageAPI = dataStorageAPI;
    }

    @Override
    public ComputeResult computePrimes(ComputeRequest request) {
        int upperLimit = request.getUpperLimit();
        if (upperLimit < 2) {
            // If input is below 2, there are no primes
            return new ComputeResult(Collections.emptyList(), 0);
        }

        // Compute list of primes
        List<Integer> primes = computePrimeList(upperLimit);
        int totalCount = primes.size();

        // Convert the list of primes to a comma-separated string for storage output
        String primeString = primes.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));

        // Write formatted output to DataStorage
        DataResponse response = new DataResponse(primes);
        dataStorageAPI.writeOutput(response);

        // Return the result for further processing
        return new ComputeResult(primes, totalCount);
    }

    /* Computes all prime numbers less than or equal to the given limit. */
    private List<Integer> computePrimeList(int limit) {
        List<Integer> primes = new ArrayList<>();

        for (int number = 2; number <= limit; number++) {
            if (isPrime(number)) {
                primes.add(number);
            }
        }
        return primes;
    }

    /* Determines whether a number is prime. */
    private boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }

        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}