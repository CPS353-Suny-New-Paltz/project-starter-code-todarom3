package conceptualapi;

import project.annotations.ConceptualAPI;

@ConceptualAPI
public interface ComputeEngineAPI {

    ComputeResult computePrimes(ComputeRequest request);
}