package networkapi;

import io.grpc.stub.StreamObserver;

import networkapi.UserComputeServiceGrpc;
import networkapi.UserRequestMessage;
import networkapi.UserResponseMessage;

public class UserComputeGrpcService extends UserComputeServiceGrpc.UserComputeServiceImplBase {

    private final UserComputeAPI coordinator;

    public UserComputeGrpcService(UserComputeAPI coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public void processUserRequest(
            UserRequestMessage request,
            StreamObserver<UserResponseMessage> responseObserver) {

        UserRequest domainRequest = new UserRequest(
                request.getInputSource(),
                request.getOutputDestination(),
                request.getDelimiter()
        );

        UserResponse domainResponse;
        try {
            // Call multithreaded coordinator
            domainResponse = coordinator.processUserRequest(domainRequest);
        } catch (Exception e) {
            domainResponse = new UserResponse("Error processing request: " + e.getMessage());
        }

        UserResponseMessage grpcResponse = UserResponseMessage.newBuilder()
                .setMessage(domainResponse.getMessage())
                .build();

        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();
    }
}