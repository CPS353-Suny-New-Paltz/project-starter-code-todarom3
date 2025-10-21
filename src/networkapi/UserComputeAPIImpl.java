package networkapi;

import processapi.DataStorageAPI; // dependency on process layer, optional

public class UserComputeAPIImpl implements UserComputeAPI {

    private DataStorageAPI dataStorageAPI; // Dependency field (if network interacts with process API)

    public UserComputeAPIImpl(DataStorageAPI dataStorageAPI) {
        this.dataStorageAPI = dataStorageAPI;
    }

    @Override
    public UserResponse processUserRequest(UserRequest request) {
        // Empty implementation: return default response
        return new UserResponse("Unprocessed request");
    }
}