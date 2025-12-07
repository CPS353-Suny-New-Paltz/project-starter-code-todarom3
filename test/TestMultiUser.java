import conceptualapi.ComputeEngineAPI;
import conceptualapi.ComputeEngineAPIImpl;
import networkapi.UserComputeAPI;
import networkapi.UserComputeAPIImpl;
import processapi.DataStorageAPIImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestMultiUser {

    private UserComputeAPI coordinator;

    @BeforeEach
    public void initializeComputeEngine() {
        ComputeEngineAPI engine = new ComputeEngineAPIImpl();
        DataStorageAPIImpl storage = new DataStorageAPIImpl();
        coordinator = new UserComputeAPIImpl(engine, storage);
    }

    @Test
    public void compareMultiAndSingleThreaded() throws Exception {

        int nThreads = 4;
        List<TestUser> testUsers = new ArrayList<>();

        for (int i = 0; i < nThreads; i++) {
            // constructor TestUser(UserComputeAPI coordinator)
            testUsers.add(new TestUser(coordinator));
        }

        // SINGLE-THREADED RUN
        String singlePrefix = "testMultiUser.singleThreadOut.tmp";

        for (int i = 0; i < nThreads; i++) {
            File out = new File(singlePrefix + i);
            out.deleteOnExit();
            try {
                testUsers.get(i).run(out.getCanonicalPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // MULTI-THREADED RUN
        ExecutorService pool = Executors.newCachedThreadPool();
        List<Future<?>> futures = new ArrayList<>();

        String multiPrefix = "testMultiUser.multiThreadOut.tmp";

        for (int i = 0; i < nThreads; i++) {
            File out = new File(multiPrefix + i);
            out.deleteOnExit();

            TestUser user = testUsers.get(i);

            futures.add(pool.submit(() -> {
                try {
                    user.run(out.getCanonicalPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));
        }

        for (Future<?> f : futures) {
            f.get();
        }

        // LOAD & COMPARE
        List<String> singleOut = loadAllOutput(singlePrefix, nThreads);
        List<String> multiOut = loadAllOutput(multiPrefix, nThreads);

        Assertions.assertEquals(singleOut, multiOut);
    }

    private List<String> loadAllOutput(String prefix, int nThreads) throws IOException {
        List<String> output = new ArrayList<>();
        for (int i = 0; i < nThreads; i++) {
            File f = new File(prefix + i);
            output.addAll(Files.readAllLines(f.toPath()));
        }
        return output;
    }
}