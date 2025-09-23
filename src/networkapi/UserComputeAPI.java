package networkapi;

import project.annotations.NetworkAPI;

@NetworkAPI
public interface UserComputeAPI {

    UserResponse processUserRequest(UserRequest request);
}