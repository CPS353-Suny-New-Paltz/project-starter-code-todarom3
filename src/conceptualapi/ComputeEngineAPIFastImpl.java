package conceptualapi;

import java.util.ArrayList;
import java.util.List;

/*I researched ways to improve the performance of prime number 
computation and found an optimized algorithm called the Sieve of 
Eratosthenes that significantly reduces computation time for large inputs.*/

public class ComputeEngineAPIFastImpl implements ComputeEngineAPI {

    @Override
    public ComputeResult computePrimes(ComputeRequest request) {

        if (request == null) {
            throw new IllegalArgumentException("ComputeRequest cannot be null.");
        }

        int n = request.getUpperLimit();

        if (n < 2) {
            return new ComputeResult(List.of(), 0);
        }

        long start = System.nanoTime();

        boolean[] isPrime = new boolean[n + 1];
        for (int i = 2; i <= n; i++) {
            isPrime[i] = true;
        }

        for (int p = 2; p * p <= n; p++) {
            if (isPrime[p]) {
                for (int multiple = p * p; multiple <= n; multiple += p) {
                    isPrime[multiple] = false;
                }
            }
        }

        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (isPrime[i]) {
                primes.add(i);
            }
        }

        long end = System.nanoTime();
        long elapsedMs = (end - start) / 1_000_000;

        System.out.println(
            "[FAST] computePrimes(" + n + ") took " + elapsedMs + " ms"
        );

        return new ComputeResult(primes, primes.size());
    }
}