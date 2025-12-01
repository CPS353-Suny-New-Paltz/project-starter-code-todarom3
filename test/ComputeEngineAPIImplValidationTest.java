import conceptualapi.ComputeEngineAPIImpl;
import conceptualapi.ComputeRequest;
import conceptualapi.ComputeResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//Checkpoint 5 - Question 3
//confirms that: Invalid inputs will throw appropriate exceptions and valid inputs behave normally

public class ComputeEngineAPIImplValidationTest {

    @Test
    public void testComputePrimes_nullRequest_throwsException() {
        ComputeEngineAPIImpl engine = new ComputeEngineAPIImpl();

        Exception ex = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> engine.computePrimes(null)
        );

        Assertions.assertTrue(ex.getMessage().contains("ComputeRequest cannot be null"));
    }

    @Test
    public void testComputePrimes_negativeLimit_throwsException() {
        ComputeEngineAPIImpl engine = new ComputeEngineAPIImpl();

        Exception ex = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> engine.computePrimes(new ComputeRequest(-5))
        );

        Assertions.assertTrue(ex.getMessage().contains("Upper limit must be non-negative"));
    }

    @Test
    public void testComputePrimes_validRequest_returnsCorrectResult() {
        ComputeEngineAPIImpl engine = new ComputeEngineAPIImpl();

        ComputeResult result = engine.computePrimes(new ComputeRequest(10));

        Assertions.assertEquals(
                java.util.Arrays.asList(2, 3, 5, 7),
                result.getPrimes(),
                "Prime list should be correct"
        );
        Assertions.assertEquals(4, result.getTotalCount());
    }
}