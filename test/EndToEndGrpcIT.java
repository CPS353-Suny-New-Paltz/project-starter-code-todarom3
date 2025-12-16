import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EndToEndGrpcIT {

    @Test
    public void endToEndGrpcTest() throws Exception {

        String classpath = System.getProperty("java.class.path");

        Process datastore = new ProcessBuilder(
                "java",
                "-cp", classpath,
                "processapi.grpc.DataStoreServer"
        ).inheritIO().start();

        Thread.sleep(1000);

        // Start Compute Server 
        Process compute = new ProcessBuilder(
                "java",
                "-cp", classpath,
                "networkapi.grpc.UserComputeServer"
        ).inheritIO().start();

        Thread.sleep(1000);

        Process client = new ProcessBuilder(
                "java",
                "-cp", classpath,
                "frontend.UserComputeClient"
        ).redirectErrorStream(true).start();

        try (BufferedWriter writer =
                     new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {

            writer.write("1\n");          // manual input
            writer.write("10,20,30\n");   // numbers
            writer.write("output.txt\n"); // output file
            writer.write("\n");           // default delimiter
            writer.flush();
        }

        boolean finished = client.waitFor(10, TimeUnit.SECONDS);
        assertTrue(finished, "Client did not finish");

        assertTrue(client.exitValue() == 0, "Client failed");

        compute.destroy();
        datastore.destroy();
    }
}