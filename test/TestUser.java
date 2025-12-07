import java.io.File;

import conceptualapi.ComputeEngineAPIImpl;
import networkapi.UserComputeAPI;
import networkapi.UserComputeAPIImpl;
import networkapi.UserRequest;
import processapi.DataStorageAPIImpl;

public class TestUser {

    // Constructor signature matches what TestMultiUser uses.
    private final UserComputeAPI coordinator;

    public TestUser(UserComputeAPI coordinator) {
        this.coordinator = coordinator;
    }

    public void run(String outputPath) {

        char delimiter = ';';
        String inputPath = "test" + File.separatorChar + "testInputFile.test";

        // fresh DataStorageAPIImpl per run (per thread)
        DataStorageAPIImpl storage = new DataStorageAPIImpl();
        storage.setOutputFilePath(outputPath);
        storage.setOutputDelimiter(String.valueOf(delimiter));

        // fresh coordinator per run using that storage
        UserComputeAPIImpl localCoordinator =
                new UserComputeAPIImpl(new ComputeEngineAPIImpl(), storage);

        UserRequest request = new UserRequest(
                inputPath,
                outputPath,
                String.valueOf(delimiter)
        );

        // Use the per-run coordinator
        localCoordinator.processUserRequest(request);
    }
}