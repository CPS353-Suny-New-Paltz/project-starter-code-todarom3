package processapi.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import processapi.DataStorageAPI;
import processapi.DataStorageAPIImpl;

public class DataStoreServer {

    private static final int PORT = 6000;

    public static void main(String[] args) throws Exception {

        DataStorageAPI storage = new DataStorageAPIImpl();
        DataStoreServiceImpl service = new DataStoreServiceImpl(storage);

        Server server = ServerBuilder
                .forPort(PORT)
                .addService(service)
                .build();

        System.out.println("DataStore gRPC server starting on port " + PORT + "...");
        server.start();
        System.out.println("DataStore gRPC server started.");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down DataStore gRPC server...");
            server.shutdown();
        }));

        server.awaitTermination();
    }
}