package networkapi;

import project.annotations.NetworkAPIPrototype;

public class UserComputeAPIImpl {

    @NetworkAPIPrototype
    public UserResponse processUserRequest(UserRequest request) {
        // Prototype logic - just return a dummy response
        return new UserResponse(
            "Processing request from source: " + request.getInputSource()
        );
    }
}