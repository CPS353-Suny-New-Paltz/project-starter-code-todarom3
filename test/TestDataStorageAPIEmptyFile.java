// This test verifies that DataStorageAPIImpl correctly handles reading from an empty file.
// It ensures that the method returns an empty list rather than throwing an error or producing incorrect data.

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import processapi.DataStorageAPIImpl;
import processapi.DataRequest;
import processapi.DataResponse;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class TestDataStorageAPIEmptyFile {

    @Test
    public void testReadInputEmptyFile() throws Exception {

        Files.createDirectories(Paths.get("test-input"));
        Files.write(Paths.get("test-input/empty.txt"), "".getBytes(StandardCharsets.UTF_8));

        DataStorageAPIImpl storage = new DataStorageAPIImpl();

        DataResponse response = storage.readInput(new DataRequest("test-input/empty.txt"));

        Assertions.assertNotNull(response);
        Assertions.assertEquals(0, response.getData().size(),
                "Reading an empty file should return an empty list");
    }
}