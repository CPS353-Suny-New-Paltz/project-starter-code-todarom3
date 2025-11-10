import org.junit.jupiter.api.Assertions;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import conceptualapi.ComputeEngineAPIImpl;
import conceptualapi.ComputeEngineAPI;
import conceptualapi.ComputeResult;
import conceptualapi.ComputeRequest;
import processapi.DataRequest;
import processapi.DataResponse;

public class ComputeEngineIntegrationTest {

    private InMemoryInput input;
    private InMemoryOutput output;
    private InMemoryDataStorageAPI dataStorage;
    private ComputeEngineAPI computeEngine;

    @BeforeEach
    public void setup() {
        // Create test input and configs
        input = new InMemoryInput(Arrays.asList(30));
        output = new InMemoryOutput();
        dataStorage = new InMemoryDataStorageAPI(input, output);

        computeEngine = new ComputeEngineAPIImpl(dataStorage);
    }

    @Test
    public void testIntegrationFlow_correctOutput() {
        // Read input
        DataResponse response = dataStorage.readInput(new DataRequest("dummy"));
        Assertions.assertEquals(Arrays.asList(30), response.getData());

        // Compute primes
        ComputeResult result = computeEngine.computePrimes(new ComputeRequest(30));

        // Expected primes ≤ 30
        List<Integer> expectedPrimes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);

        // Write output
        dataStorage.writeOutput(new DataResponse(result.getPrimes()));

        // Verify the computed result
        Assertions.assertEquals(expectedPrimes, result.getPrimes(), "Computed primes should be correct");
        Assertions.assertEquals(expectedPrimes.size(), result.getTotalCount(), "Prime count should be correct");

        // Verify output stored correctly
        List<String> outputData = output.getOutputData();
        Assertions.assertTrue(outputData.contains("2"), "Output should contain 2");
        Assertions.assertEquals(expectedPrimes.size(), outputData.size(),
                "Output should have the same number of primes as expected");
    }
}