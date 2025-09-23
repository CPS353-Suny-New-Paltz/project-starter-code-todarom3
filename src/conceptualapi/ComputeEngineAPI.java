package conceptualapi;

import project.annotations.ConceptualAPI;

@ConceptualAPI
public interface ComputeEngineAPI {

    public static ComputeResult computePrimes(ComputeRequest request) {
        // Empty base method - does nothing
        return null;
    }
}