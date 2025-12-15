import conceptualapi.ComputeEngineAPI;
import conceptualapi.ComputeEngineAPIFastImpl;
import networkapi.UserComputeAPIImpl;
import processapi.DataStorageAPI;

import org.junit.jupiter.api.Test;

public class ComputeEngineFastIntegrationTest {

    @Test
    public void integrationTestWithFastEngine() {

        ComputeEngineAPI engine = new ComputeEngineAPIFastImpl();

        InMemoryInput input = new InMemoryInput(java.util.List.of(10));
        InMemoryOutput output = new InMemoryOutput();
        DataStorageAPI storage = new InMemoryDataStorageAPI(input, output);

        new UserComputeAPIImpl(engine, storage);
    }
}