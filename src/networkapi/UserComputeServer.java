package networkapi;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class UserComputeServer {

    private Server server;

    public void start(int port, UserComputeAPI coordinator) throws Exception {

        // Build gRPC server
        server = ServerBuilder.forPort(port)
                .addService(new UserComputeGrpcService(coordinator))
                .build()
                .start();

        System.out.println("gRPC server started on port " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server...");
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
        int port = 50051;

        UserComputeAPI coordinator =
                new UserComputeAPIMultiThreadedImpl(
                        new conceptualapi.ComputeEngineAPIImpl(),
                        new processapi.DataStorageAPIImpl()
                );

        UserComputeServer server = new UserComputeServer();
        server.start(port, coordinator);
        server.blockUntilShutdown();
    }
}