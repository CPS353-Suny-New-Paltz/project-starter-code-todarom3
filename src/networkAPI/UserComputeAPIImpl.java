package networkAPI;

import project.annotations.NetworkAPIPrototype; 

public class UserComputeAPIImpl implements UserComputeAPI {

    @Override
    @NetworkAPIPrototype
    public UserResponse processUserRequest(UserRequest request) {
        // Prototype - just return a dummy response
        return new UserResponse("Processing request from source: " + request.getInputSource());
    }
}