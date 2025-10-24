import org.junit.jupiter.api.Assertions;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import conceptualapi.ComputeEngineAPI;
import conceptualapi.ComputeResult;
import conceptualapi.ComputeRequest;
import processapi.DataRequest;
import processapi.DataResponse;

public class ComputeEngineIntegrationTest {

    private InMemoryInputConfig inputConfig;
    private InMemoryOutputConfig outputConfig;
    private InMemoryDataStorageAPI dataStorage;
    private ComputeEngineAPI computeEngine;

    @BeforeEach
    public void setup() {
        //Create test input and configs
        inputConfig = new InMemoryInputConfig(Arrays.asList(1, 10, 25));
        outputConfig = new InMemoryOutputConfig();
        dataStorage = new InMemoryDataStorageAPI(inputConfig, outputConfig);

        // Use empty ComputeEngine implementation
        computeEngine = new conceptualapi.impl.ComputeEngineImpl(); 
    }

    @Test
    public void testIntegrationFlow() {
        //Read input from DataStore
        DataResponse response = dataStorage.readInput(new DataRequest("dummy"));
        Assertions.assertNotNull(response, "DataResponse should not be null");
        Assertions.assertEquals(Arrays.asList(1, 10, 25), response.getData(), "Input list should match expected values");

        // Simulate operation
        ComputeResult result = computeEngine.computePrimes(new ComputeRequest(25));

        // Simulate output
        dataStorage.writeOutput(new DataResponse(Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23)));

        // Verify output is consistent
        List<String> output = outputConfig.getOutputData();
        Assertions.assertFalse(output.isEmpty(), "Output list should not be empty");
        Assertions.assertTrue(output.contains("2"), "Expected primes in output");
    }
}