import conceptualapi.ComputeEngineAPI;
import conceptualapi.ComputeEngineAPIFastImpl;
import org.junit.jupiter.api.Test;

public class ComputeEngineAPIFastImplSmokeTest {
	
	/*I added a smoke test to instantiate the optimized compute engine 
	so that all implementations of the conceptual API are exercised, 
	preserving compatibility with earlier checkpoint tests.*/
	
    @Test
    public void smokeTestFastComputeEngine() {
        ComputeEngineAPI engine = new ComputeEngineAPIFastImpl();
    }
}