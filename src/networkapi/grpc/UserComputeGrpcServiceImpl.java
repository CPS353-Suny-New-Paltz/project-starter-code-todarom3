package networkapi.grpc;

import io.grpc.stub.StreamObserver;
import networkapi.UserComputeServiceGrpc;
import networkapi.UserRequestProto;
import networkapi.UserResponseProto;

import networkapi.UserComputeAPI;
import networkapi.UserRequest;
import networkapi.UserResponse;

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
            UserRequest userRequest = new UserRequest(
                    request.getInputSource(),
                    request.getOutputDestination(),
                    request.getDelimiter()
            );

            UserResponse result = api.processUserRequest(userRequest);

            UserResponseProto reply = UserResponseProto.newBuilder()
                    .setMessage(result != null ? result.getMessage() : "")
                    .build();

            responseObserver.onNext(reply);
            responseObserver.onCompleted();

        } catch (Exception ex) {
            UserResponseProto reply = UserResponseProto.newBuilder()
                    .setMessage("Error processing request: " + ex.getMessage())
                    .build();

            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}