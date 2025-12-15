import conceptualapi.ComputeEngineAPI;
import conceptualapi.ComputeEngineAPIImpl;
import conceptualapi.ComputeEngineAPIFastImpl;
import conceptualapi.ComputeRequest;

import networkapi.UserComputeAPI;
import networkapi.UserComputeAPIImpl;

import processapi.DataStorageAPI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ComputeEngineBenchmarkIT {

    private static final int TEST_N = 100_000;

    @Test
    public void benchmarkFastIsAtLeastTenPercentFaster() {

        InMemoryInput input = new InMemoryInput(List.of(TEST_N));
        InMemoryOutput output = new InMemoryOutput();
        DataStorageAPI storage = new InMemoryDataStorageAPI(input, output);

        ComputeEngineAPI slowEngine = new ComputeEngineAPIImpl();
        UserComputeAPI slowCoordinator =
                new UserComputeAPIImpl(slowEngine, storage);

        long slowStart = System.nanoTime();
        slowEngine.computePrimes(new ComputeRequest(TEST_N));
        long slowEnd = System.nanoTime();

        long slowTime = slowEnd - slowStart;

        ComputeEngineAPI fastEngine = new ComputeEngineAPIFastImpl();
        UserComputeAPI fastCoordinator = new UserComputeAPIImpl(fastEngine, storage);

        long fastStart = System.nanoTime();
        fastEngine.computePrimes(new ComputeRequest(TEST_N));
        long fastEnd = System.nanoTime();

        long fastTime = fastEnd - fastStart;

        double improvement = (double) (slowTime - fastTime) / slowTime;

        System.out.println("Slow time (ns): " + slowTime);
        System.out.println("Fast time (ns): " + fastTime);
        System.out.println("Improvement: " + (improvement * 100) + "%");

        Assertions.assertTrue(
                improvement >= 0.10,
                "Fast implementation must be at least 10% faster"
        );
    }
}