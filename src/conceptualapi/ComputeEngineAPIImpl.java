package conceptualapi;

import java.util.ArrayList;
import java.util.List;
import project.annotations.ConceptualAPIPrototype;

public class ComputeEngineAPIImpl implements ComputeEngineAPI {

    @Override
    @ConceptualAPIPrototype
    public ComputeResult computePrimes(ComputeRequest request) {
        List<Integer> emptyPrimes = new ArrayList<>();
        return new ComputeResult(emptyPrimes, 0);
    }
}