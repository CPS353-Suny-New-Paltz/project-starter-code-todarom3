package networkapi;

import project.annotations.NetworkAPIPrototype;

public class UserComputeAPIImpl implements UserComputeAPI {

    @Override
    @NetworkAPIPrototype
    public UserResponse processUserRequest(UserRequest request) {
        // Prototype - just return a dummy message
        return new UserResponse("Processing request from source: " + request.getInputSource());
    }
}