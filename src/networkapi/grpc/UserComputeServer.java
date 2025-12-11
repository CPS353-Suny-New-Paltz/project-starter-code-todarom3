package networkapi.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import conceptualapi.ComputeEngineAPI;
import conceptualapi.ComputeEngineAPIImpl;

import networkapi.UserComputeAPI;
import networkapi.UserComputeAPIImpl;

import processapi.DataStorageAPI;
import processapi.DataStorageAPIImpl;

public class UserComputeServer {

    private static final int PORT = 50051;

    public static void main(String[] args) throws Exception {

        ComputeEngineAPI engine = new ComputeEngineAPIImpl();
        DataStorageAPI storage = new DataStorageAPIImpl();

        // existing network API implementation
        UserComputeAPI api = new UserComputeAPIImpl(engine, storage);

        // Wrap gRPC service
        UserComputeGrpcServiceImpl service = new UserComputeGrpcServiceImpl(api);

        Server server = ServerBuilder
                .forPort(PORT)
                .addService(service)
                .build();

        System.out.println("Starting UserCompute gRPC server on port " + PORT + "...");
        server.start();
        System.out.println("Server started. Awaiting termination.");
        server.awaitTermination();
    }
}