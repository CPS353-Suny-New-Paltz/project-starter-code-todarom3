package conceptualapi;

import java.util.List;

public class ComputeResult {
    private List<Integer> primes;
    private int totalCount;

    public ComputeResult(List<Integer> primes, int totalCount) {
        this.primes = primes;
        this.totalCount = totalCount;
    }

    public List<Integer> getPrimes() {
        return primes;
    }

    public int getTotalCount() {
        return totalCount;
    }
}