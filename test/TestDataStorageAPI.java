import processapi.DataStorageAPIImpl;
import processapi.DataRequest;
import processapi.DataResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class TestDataStorageAPI {

    @Test
    public void testReadAndWrite_smoke() throws Exception {
        DataStorageAPIImpl dataStorage = new DataStorageAPIImpl();

        // Set output path so writeOutput() knows where to write
        dataStorage.setOutputFilePath("test-output/output.txt");

     // Make sure the folder exists:
        Files.createDirectories(Paths.get("test-input"));
        // Write the file content:
        Files.write(Paths.get("test-input/input.txt"), "5\n10\n15\n".getBytes(StandardCharsets.UTF_8));

        // Call readInput()
        DataResponse response = dataStorage.readInput(new DataRequest("test-input/input.txt"));
        Assertions.assertNotNull(response, "DataResponse should not be null");

        // Call writeOutput()
        DataResponse writeResponse = dataStorage.writeOutput(response);
        Assertions.assertNotNull(writeResponse, "writeOutput should return a DataResponse");
    }
}