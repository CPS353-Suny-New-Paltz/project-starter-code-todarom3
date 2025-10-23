package networkapi;

import conceptualapi.ComputeEngineAPI;

public class UserComputeAPIImpl implements UserComputeAPI {

    // Dependency on conceptual layer
    private ComputeEngineAPI computeEngineAPI;

    public UserComputeAPIImpl(ComputeEngineAPI computeEngineAPI) {
        this.computeEngineAPI = computeEngineAPI;
    }

    @Override
    public UserResponse processUserRequest(UserRequest request) {
        // Just return a default response message for now
        return new UserResponse("Request not yet processed.");
    }
}