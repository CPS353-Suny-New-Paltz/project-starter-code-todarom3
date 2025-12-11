package networkapi;

import io.grpc.stub.StreamObserver;

public class UserComputeGrpcService extends UserComputeServiceGrpc.UserComputeServiceImplBase {

    private final UserComputeAPI coordinator;

    public UserComputeGrpcService(UserComputeAPI coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public void processUserRequest(
            UserRequestMessage request,
            StreamObserver<UserResponseMessage> responseObserver) {

        // Map gRPC message → your existing domain request
        UserRequest domainRequest = new UserRequest(
                request.getInputSource(),
                request.getOutputDestination(),
                request.getDelimiter()
        );

        UserResponse domainResponse;
        try {
            // Call your (multithreaded) NetworkAPI implementation
            domainResponse = coordinator.processUserRequest(domainRequest);
        } catch (Exception e) {
            // If you want, move exception handling boundary up to here
            domainResponse = new UserResponse("Error processing request: " + e.getMessage());
        }

        // Map domain response → gRPC message
        UserResponseMessage grpcResponse = UserResponseMessage.newBuilder()
                .setMessage(domainResponse.getMessage())
                .build();

        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();
    }
}