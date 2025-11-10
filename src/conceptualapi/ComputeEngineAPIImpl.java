package conceptualapi;

import java.util.ArrayList;
import java.util.List;
import processapi.DataStorageAPI;
import processapi.DataResponse;
import java.util.Collections;

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
            return new ComputeResult(Collections.emptyList(), 0);
        }

        List<Integer> primes = computePrimeList(upperLimit);
        int totalCount = primes.size();

        // Write results to data storage to confirm the output layer is working
        DataResponse response = new DataResponse(primes);
        dataStorageAPI.writeOutput(response);

        return new ComputeResult(primes, totalCount);
    }

    /* Computes all prime numbers less than or equal to the given limit.
     */
    private List<Integer> computePrimeList(int limit) {
        List<Integer> primes = new ArrayList<>();

        for (int number = 2; number <= limit; number++) {
            if (isPrime(number)) {
                primes.add(number);
            }
        }
        return primes;
    }

    /* Determines whether a number is prime.
     */
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
