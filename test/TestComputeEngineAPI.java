import conceptualapi.ComputeEngineAPIImpl;
import conceptualapi.ComputeRequest;
import conceptualapi.ComputeResult;
import processapi.DataStorageAPI;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.List;

public class TestComputeEngineAPI {

    @Test
    public void testComputePrimes_correctness() {
        // Mock DataStorageAPI dependency
        DataStorageAPI mockDataStorage = Mockito.mock(DataStorageAPI.class);

        // Create instance under test
        ComputeEngineAPIImpl computeEngine = new ComputeEngineAPIImpl(mockDataStorage);

        // Input: upper limit = 30
        ComputeRequest request = new ComputeRequest(30);
        ComputeResult result = computeEngine.computePrimes(request);

        // Expected primes <= 30
        List<Integer> expectedPrimes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);

        // These assertions will fail if the implementation just returns an empty list
        Assertions.assertNotNull(result, "Result should not be null");
        Assertions.assertEquals(expectedPrimes, result.getPrimes(),
                "Primes should match the expected list");
        Assertions.assertEquals(expectedPrimes.size(), result.getTotalCount(),
                "Total count should match the number of primes found");
    }
}