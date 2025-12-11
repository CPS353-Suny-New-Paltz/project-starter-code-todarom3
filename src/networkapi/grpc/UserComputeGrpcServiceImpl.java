package networkapi.grpc;

import io.grpc.stub.StreamObserver;

import networkapi.UserComputeAPI;
import networkapi.UserRequest;
import networkapi.UserResponse;

import networkapi.UserRequestProto;
import networkapi.UserResponseProto;
import networkapi.UserComputeServiceGrpc;

public class UserComputeGrpcServiceImpl
        extends UserComputeServiceGrpc.UserComputeServiceImplBase {

    private final UserComputeAPI api;

    public UserComputeGrpcServiceImpl(UserComputeAPI api) {
        this.api = api;
    }

    @Override
    public void processUserRequest(
            UserRequestProto request,
            StreamObserver<UserResponseProto> responseObserver) {

        try {
            String inputSource = request.hasInputSource()
                    ? request.getInputSource()
                    : null;

            String outputDestination = request.hasOutputDestination()
                    ? request.getOutputDestination()
                    : null;

            String delimiter = request.hasDelimiter()
                    ? request.getDelimiter()
                    : null;

            UserRequest userRequest = new UserRequest(
                    inputSource,
                    outputDestination,
                    delimiter
            );

            // Call existing network API implementation
            UserResponse result = api.processUserRequest(userRequest);

            // Translate back into proto
            String message = (result != null && result.getMessage() != null)
                    ? result.getMessage()
                    : "";

            UserResponseProto reply = UserResponseProto.newBuilder()
                    .setMessage(message)
                    .build();

            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        } catch (Exception e) {
            // Network-safe error message
            UserResponseProto reply = UserResponseProto.newBuilder()
                    .setMessage("Error processing request: " + e.getMessage())
                    .build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}