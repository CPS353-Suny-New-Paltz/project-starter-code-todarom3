package networkapi.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import conceptualapi.ComputeEngineAPI;
import conceptualapi.ComputeEngineAPIImpl;

import networkapi.UserComputeAPI;
import networkapi.UserComputeAPIImpl;

import processapi.DataStorageAPI;
import processapi.GrpcDataStorageAPI;

public class UserComputeServer {

    private static final int PORT = 5050;

    private static final String DATASTORE_HOST = "localhost";
    private static final int DATASTORE_PORT = 6000;

    public static void main(String[] args) throws Exception {

        // Conceptual engine
        ComputeEngineAPI engine = new ComputeEngineAPIImpl();

        // ProcessAPI
        DataStorageAPI storage = new GrpcDataStorageAPI(DATASTORE_HOST, DATASTORE_PORT);

        // controller / coordinator
        UserComputeAPI api = new UserComputeAPIImpl(engine, storage);

        UserComputeGrpcServiceImpl service = new UserComputeGrpcServiceImpl(api);

        Server server = ServerBuilder
                .forPort(PORT)
                .addService(service)
                .build();

        System.out.println("UserCompute gRPC server starting on port " + PORT + "...");
        server.start();
        System.out.println("UserCompute gRPC server started.");
        server.awaitTermination();
    }
}