package conceptualapi;

public class ComputeEngineAPIImpl implements ComputeEngineAPI {

    @Override
    public ComputeResult computePrimes(ComputeRequest request) {
        // Empty implementation for now
        return new ComputeResult(null, 0); // default or "empty" result
    }
}