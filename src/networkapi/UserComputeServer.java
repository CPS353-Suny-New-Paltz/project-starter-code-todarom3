package networkapi;

import conceptualapi.ComputeEngineAPI;
import conceptualapi.ComputeEngineAPIImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import processapi.DataStorageAPI;
import processapi.DataStorageAPIImpl;

public class UserComputeServer {

    private final Server server;

    public UserComputeServer(int port) {
        ComputeEngineAPI engine = new ComputeEngineAPIImpl();
        DataStorageAPI storage = new DataStorageAPIImpl();

        // IMPORTANT: use your multithreaded implementation here
        UserComputeAPI coordinator =
                new UserComputeAPIMultiThreadedImpl(engine, storage);

        this.server = ServerBuilder.forPort(port)
                .addService(new UserComputeGrpcService(coordinator))
                .build();
    }

    public void start() throws Exception {
        server.start();
        System.out.println("gRPC server started on port " + server.getPort());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server");
            UserComputeServer.this.stop();
        }));
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 50051; // or whatever port your prof suggests
        UserComputeServer server = new UserComputeServer(port);
        server.start();
        server.blockUntilShutdown();
    }
}