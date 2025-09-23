package conceptualapi;

import java.util.ArrayList;
import java.util.List;
import project.annotations.ConceptualAPIPrototype;

public class ComputeEngineAPIImpl {

    @ConceptualAPIPrototype
    public ComputeResult computePrimes(ComputeRequest request) {
        // Prototype version: return an empty list of primes
        List<Integer> emptyPrimes = new ArrayList<>();
        return new ComputeResult(emptyPrimes, 0);
    }
}