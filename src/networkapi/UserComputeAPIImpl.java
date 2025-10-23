package networkapi;

import conceptualapi.ComputeEngineAPI;

public class UserComputeAPIImpl {

    @NetworkAPIPrototype
    public void prototype(UserComputeAPI api) {

    	UserRequest dummyRequest = new UserRequest("input.txt", "output.txt", ",");

        UserResponse response = api.processUserRequest(dummyRequest);

        System.out.println("Prototype response: " + (response != null ? response.getMessage() : "null"));
    }
}