import java.io.File;
import java.io.IOException;

import networkapi.UserComputeAPI;
import networkapi.UserRequest;

public class TestUser {

    private final UserComputeAPI coordinator;

    public TestUser(UserComputeAPI coordinator) {
        this.coordinator = coordinator;
    }

    public void run(String outputPath) throws IOException {

        char delimiter = ';';
        String inputPath = "test" + File.separatorChar + "testInputFile.test";

        UserRequest request = new UserRequest(
                inputPath,
                outputPath,
                String.valueOf(delimiter)
        );

        // Use the shared coordinator
        coordinator.processUserRequest(request);
    }
}