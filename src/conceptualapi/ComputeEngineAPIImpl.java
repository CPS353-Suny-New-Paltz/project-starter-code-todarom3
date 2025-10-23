package conceptualapi;

import java.util.Collections;

public class ComputeEngineAPIImpl {

    @ConceptualAPIPrototype
    public void prototype(ComputeEngineAPI api) {

    	ComputeRequest dummyRequest = new ComputeRequest(10); // compute primes up to 10

        ComputeResult result = api.computePrimes(dummyRequest);

        if (result != null) {
            List<Integer> primes = result.getPrimes();
            System.out.println("Prototype computed " + result.getTotalCount() + " primes: " + primes);
        } else {
            System.out.println("Prototype received a null result.");
        }
    }
}